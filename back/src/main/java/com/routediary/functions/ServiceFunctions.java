package com.routediary.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.PageBean;
import com.routediary.enums.Dto;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@Component
public class ServiceFunctions {

  @Value("${file.upload.directory}")
  private String UPLOAD_PATH;
  @Value("${file.upload.temp-directory}")
  private String TEMP_UPLOAD_PATH;
  @Value("${paging.cnt-per-page}")
  private int CNT_PER_PAGE;
  @Value("${paging.cnt-per-page-group}")
  private int CNT_PER_PAGE_GROUP;

  /**
   * 이미지 파일을 서버에 저장하는 메서드
   * 
   * @param postNo
   * @param dto
   * @param imageFiles
   * @param hasThumbnail
   * @throws Exception
   */
  public void saveImages(int postNo, Dto dto, List<MultipartFile> imageFiles, boolean hasThumbnail)
      throws Exception {
    String path = findOriginalPath(postNo, dto);
    makeDirectories(path); // 디렉토리가 없을 경우, 디렉토리를 생성
    int imageNo = 0;
    String fileExtension = ".jpg"; // 이미지 파일을 저장할 확장자명
    for (MultipartFile imageFile : imageFiles) {
      if (!imageFile.isEmpty()) { // 파일이 null이 아닐 경우에 저장작업을 시작함
        String contentType = imageFile.getContentType();
        if (isImageFile(contentType)) {
          imageNo++;
          File file = new File(path + imageNo + fileExtension);
          imageFile.transferTo(file);
        } else {
          continue;
        }
      }
    }
    createThumbnail(path, hasThumbnail);
  }

  public void saveImages(int postNo, Dto dto, List<MultipartFile> imageFiles) throws Exception {
    saveImages(postNo, dto, imageFiles, false);
  }

  /**
   * 해당 다이어리 or 공지사항의 경로의 파일들을 원래폴더에서 임시폴더로 옮김
   * 
   * @param postNo
   * @param dto
   */
  public void transferFilesToTempFolder(int postNo, Dto dto) {
    String tempPath = findTempPath(postNo, dto);
    makeDirectories(tempPath);
    String path = findOriginalPath(postNo, dto);

    copyFiles(path, tempPath);
  }

  /**
   * 임시폴더에 있던 해당 다이어리 or 공지사항의 파일들을 원래폴더로 옮김
   * 
   * @param postNo
   * @param dto
   */
  public void transferFilesToOriginalFolder(int postNo, Dto dto) {
    String path = findOriginalPath(postNo, dto);
    makeDirectories(path);
    String tempPath = findOriginalPath(postNo, dto);

    copyFiles(tempPath, path);
  }

  /**
   * 해당 다이어리 or 공지사항의 경로의 파일들을 모두 삭제
   * 
   * @param postNo
   * @param dto
   */
  public void removeOriginalFiles(int postNo, Dto dto) {
    String path = findOriginalPath(postNo, dto);
    removeFolderAndFiles(path);
  }

  /**
   * 임시폴더에 있는 해당 다이어리 or 공지사항의 파일들을 모두 삭제
   * 
   * @param postNo
   * @param dto
   */
  public void removeTempFiles(int postNo, Dto dto) {
    String tempPath = findTempPath(postNo, dto);
    removeFolderAndFiles(tempPath);
  }

  /**
   * 각 다이어리나 공지사항 폴더에 저장된 이미지의 갯수를 반환함 *주의 : 다이어리의 경우에는 thumbnail image 파일까지 포함한 갯수임
   * 
   * @param postNo
   * @param dto
   * @return int
   */
  public int getImageFilesCount(int postNo, Dto dto) {
    String path = findOriginalPath(postNo, dto);
    File folder = new File(path);
    int count = 0;
    if (folder.exists()) {
      count = folder.listFiles().length;
    }
    return count;
  }

  /**
   * repository에서 Diary, Notice 등을 불러오기 위해 필요한 startRow(index = 0), endRow(index = 1)를 배열로 반환한다.
   * 
   * @param currentPage
   * @param totalRows
   * @return int[]
   */
  public int[] calculateStartAndEndRow(int currentPage, int totalRows) {
    if (currentPage <= 0) {
      currentPage = 1;
    }
    int endRow = currentPage * CNT_PER_PAGE;
    int startRow = endRow - (CNT_PER_PAGE - 1);

    if (endRow > totalRows) {// 전체 페이지 수보다 더 높은 수의 페이지를 요청할 경우, 마지막 페이지의 다이어리들을 반환하도록 설정
      endRow = (int) Math.ceil((double) totalRows / CNT_PER_PAGE) * CNT_PER_PAGE;
      startRow = endRow - (CNT_PER_PAGE_GROUP - 1);
    }
    int[] rowArr = {startRow, endRow};
    return rowArr;
  }

  /**
   * 공지사항이나 다이어리의 번호에 따른 이미지저장 파일 경로를 반환함
   * 
   * @param postNo
   * @param dto
   * @return String
   */
  public String findOriginalPath(int postNo, Dto dto) {
    // File.separator = window, linux, mac 등 서로 다른 운영체제에서 폴더경로를 인식하게 하는 역할
    String dtoName = dto.getName();
    String specificPath = "images" + File.separator + dtoName + "_images" + File.separator + dtoName
        + postNo + File.separator;
    String path = UPLOAD_PATH + specificPath;
    log.info(postNo + "번 " + dtoName + "의 이미지파일 저장 경로 : " + path);
    return path;
  }

  /**
   * 페이지 계산을 한 뒤, PageBean 객체를 반환
   * 
   * @param currentPage
   * @param totalRows
   * @param posts
   * @return PageBean<?>
   */
  public PageBean<?> calculatePageBean(int currentPage, int totalRows, List<?> posts) {
    if (currentPage <= 0) {
      currentPage = 1;
    }
    PageBean<?> pageBean =
        new PageBean(posts, totalRows, currentPage, CNT_PER_PAGE_GROUP, CNT_PER_PAGE);
    return pageBean;
  }

  private boolean isImageFile(String contentType) {
    if (ObjectUtils.isEmpty(contentType) || !contentType.contains("image/")) {
      return false;
    } else {
      return true;
    }
  }

  private void createThumbnail(String path, boolean hasThumbnail) throws IOException {
    // thumbnail image 만들기
    if (hasThumbnail == true) {
      String thumbnailName = path + File.separator + "thumbnail.png";
      File originalFile = new File(path + File.separator + "1.jpg");
      File thumbnailFile = new File(thumbnailName);
      Thumbnailator.createThumbnail(originalFile, thumbnailFile, 200, 100);
    }
  }

  private void removeFolderAndFiles(String path) {
    File folder = new File(path);
    if (folder.exists()) {
      File[] allFiles = folder.listFiles();
      if (allFiles != null) {
        for (File file : allFiles) {
          file.delete();
        }
      }
      if (folder.isDirectory()) {
        folder.delete(); // 대상폴더 삭제
      }
    }
  }

  private void copyFiles(String originalPath, String copiedPath) {
    File originalFolder = new File(originalPath);
    if (originalFolder.exists()) {
      File[] allOriginalFiles = originalFolder.listFiles();
      if (allOriginalFiles != null) {
        for (File originalFile : allOriginalFiles) {
          // 파일 복사
          String fileName = originalFile.getName();
          FileInputStream fis = null;
          FileOutputStream fos = null;
          try {
            fis = new FileInputStream(originalFile);
            File copiedFile = new File(copiedPath + fileName);
            fos = new FileOutputStream(copiedFile);
            int nRealByte = 0;
            while ((nRealByte = fis.read()) != -1) {
              fos.write(nRealByte);
            }
            fis.close();
            fos.close();
            originalFile.delete();
          } catch (Exception e) {
            e.printStackTrace();
            log.error("파일 복사 실패. 사유 : " + e.getLocalizedMessage());
          }
        }
      }
      if (originalFolder.isDirectory()) {
        originalFolder.delete(); // 대상폴더 삭제
      }
    }
  }

  private void makeDirectories(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  private String findTempPath(int postNo, Dto dto) {
    // File.separator = window, linux, mac 등 서로 다른 운영체제에서 폴더경로를 인식하게 하는 역할
    String dtoName = dto.getName();
    String specificPath = "images" + File.separator + dtoName + "_images" + File.separator + dtoName
        + postNo + File.separator;
    String tempPath = TEMP_UPLOAD_PATH + specificPath;
    log.info(postNo + "번 " + dtoName + "의 이미지파일 임시저장 경로 : " + tempPath);
    return tempPath;
  }
}
