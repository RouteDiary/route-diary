package com.routediary.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.DiaryImage;
import com.routediary.dto.Hashtag;
import com.routediary.dto.Like;
import com.routediary.dto.PageBean;
import com.routediary.dto.Route;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.repository.CommentRepository;
import com.routediary.repository.DiaryImageRepository;
import com.routediary.repository.DiaryRepository;
import com.routediary.repository.HashtagRepository;
import com.routediary.repository.LikeRepository;
import com.routediary.repository.RouteRepository;
import net.coobird.thumbnailator.Thumbnailator;

@Service(value = "DiaryService")
public class DiaryServiceImpl implements DiaryService {
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private DiaryRepository diaryRepository;
  @Autowired
  private RouteRepository routeRepository;
  @Autowired
  private DiaryImageRepository diaryImageRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private LikeRepository likeRepository;
  @Autowired
  private HashtagRepository hashtagRepository;
  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void writeDiary(Diary diary, List<MultipartFile> imageFiles) throws AddException {
    // Routes, Hashtags를 batch를 이용하여 insert함
    List<Route> routes = diary.getRoutes();
    List<Hashtag> hashtags = diary.getHashtags();
    int diaryNo = diaryRepository.selectLatestDiaryNo(); // 저장된 다이어리의 diaryNo 구하기
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    try {
      diaryRepository.insert(diary);
      routeRepository = sqlSession.getMapper(RouteRepository.class);
      for (Route route : routes) {
        route.setDiaryNo(diaryNo);
        routeRepository.insert(route);
        sqlSession.commit();
      }
      hashtagRepository = sqlSession.getMapper(HashtagRepository.class);
      for (Hashtag hashtag : hashtags) {
        hashtag.setDiaryNo(diaryNo);
        hashtagRepository.insert(hashtag);
        sqlSession.commit();
      }

      // File.separator = window, linux, mac 등 서로 다른 운영체제에서 폴더경로를 인식하게 하는 역할
      String absolutePath = new File("").getAbsolutePath() + File.separator; // 이미지파일을 저장할 경로중 절대경로
                                                                             // 부분을 가져옴
      // 이미지 파일 저장 로직
      int imageNo = 0;
      String path = "images" + File.separator + "diary_images" + File.separator + "diary" + diaryNo;
      File file = new File(path);
      if (!file.exists()) {
        file.mkdirs(); // 디렉토리가 없을 경우, 디렉토리 생성
      }
      String fileExtension = ".jpg";
      for (MultipartFile imageFile : imageFiles) {
        if (!imageFile.isEmpty()) { // 파일이 null이 아닐 경우에 저장작업을 시작함
          String contentType = imageFile.getContentType();
          if (ObjectUtils.isEmpty(contentType) || !contentType.contains("image/")) {
            continue;
          } else {
            // DB에 추가
            imageNo++;
            DiaryImage diaryImage = new DiaryImage();
            diaryImage.setDiaryNo(diaryNo);
            diaryImage.setImageNo(imageNo);
            diaryImage.setFileName(imageNo + ".jpg");
            String storedFilePath = path + File.separator + imageNo + fileExtension;
            diaryImage.setStoredFilePath(storedFilePath);
            logger.info("DB에 추가할 diaryImage의 diaryNo : " + diaryNo + ", imageNo : " + imageNo
                + ", originalFileNmae : " + imageNo + ".jpg" + ", storedFilePath : "
                + storedFilePath + ", fileSize : " + imageFile.getSize());
            diaryImageRepository.insert(diaryImage);
            logger.info("절대경로 : " + absolutePath);
            // 변경된 파일이름으로 적용
            file = new File(absolutePath + storedFilePath);
            logger.info("파일경로+이름 : " + absolutePath + storedFilePath);
            imageFile.transferTo(file);
          }
        }
      }
      // 섬네일 생성 (섬네일 파일 이름은 중간에 "s_"로 시작하도록)
      String thumbnailSaveName =
          absolutePath + File.separator + path + File.separator + "thumbnail.png";
      File originalFile = new File(absolutePath + File.separator + path + File.separator + "1.jpg");
      File thumbnailFile = new File(thumbnailSaveName);
      DiaryImage diaryImage = new DiaryImage();
      diaryImage.setDiaryNo(diaryNo);
      diaryImage.setImageNo(imageNo + 1);
      String thumnailName = "thumbnail.png";
      diaryImage.setFileName(thumnailName);
      String storedFilePath = path + File.separator + thumnailName;
      diaryImage.setStoredFilePath(storedFilePath);
      logger.info("DB에 추가할 diaryImage(thumbnail)의 diaryNo : " + diaryNo + ", imageNo : " + imageNo
          + ", storedFilePath : " + storedFilePath);
      diaryImageRepository.insert(diaryImage);
      Thumbnailator.createThumbnail(originalFile, thumbnailFile, 200, 100);

    } catch (Exception e) {
      e.printStackTrace();
      File folder = new File(new File("").getAbsolutePath() + File.separator + "images"
          + File.separator + "diary_images" + File.separator + "diary" + diaryNo);
      try {
        if (folder.exists()) {
          FileUtils.cleanDirectory(folder);// 하위 폴더와 파일 모두 삭제

          if (folder.isDirectory()) {
            folder.delete(); // 대상폴더 삭제
            logger.debug(folder + "폴더가 삭제되었습니다.");
          }
        }
      } catch (IOException e1) {
        logger.error("폴더삭제 오류");
      }
      sqlSession.rollback();
      throw new AddException("Diary와 Diary의 Route들를 DB에 추가하는데 실패하였습니다. \n" + e.getMessage());
    } finally {
      sqlSession.close();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void modifyDiary(Diary diary, List<MultipartFile> imageFiles) throws ModifyException {
    // Routes, Hashtags를 batch를 이용하여 insert함
    List<Route> routes = diary.getRoutes();
    List<Hashtag> hashtags = diary.getHashtags();
    int diaryNo = diary.getDiaryNo();

    try {
      routeRepository.deleteAll(diaryNo);
      hashtagRepository.deleteAll(diaryNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ModifyException(
          diaryNo + "번 다이어리의 route, hashtag들을 삭제하는데 실패하였습니다. \n" + e.getMessage());
    }
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    try {
      routeRepository = sqlSession.getMapper(RouteRepository.class);
      for (Route route : routes) {
        routeRepository.insert(route);
        sqlSession.commit();
      }
      hashtagRepository = sqlSession.getMapper(HashtagRepository.class);
      for (Hashtag hashtag : hashtags) {
        hashtagRepository.insert(hashtag);
        sqlSession.commit();
      }
      diaryRepository.update(diary);
      // 폴더안의 이미지 삭제
      File folder = new File(new File("").getAbsolutePath() + File.separator + "images"
          + File.separator + "diary_images" + File.separator + "diary" + diaryNo);
      if (folder.exists()) {
        FileUtils.cleanDirectory(folder);// 하위 폴더와 파일 모두 삭제
      }
      // File.separator = window, linux, mac 등 서로 다른 운영체제에서 폴더경로를 인식하게 하는 역할
      String absolutePath = new File("").getAbsolutePath() + File.separator; // 이미지파일을 저장할 경로중 절대경로
                                                                             // 부분을 가져옴
      // 이미지 파일 저장 로직
      int imageNo = 0;
      String path = "images" + File.separator + "diary_images" + File.separator + "diary" + diaryNo;
      File file = new File(path);
      if (!file.exists()) {
        file.mkdirs(); // 디렉토리가 없을 경우, 디렉토리 생성
      }
      String fileExtension = ".jpg";
      for (MultipartFile imageFile : imageFiles) {
        if (!imageFile.isEmpty()) { // 파일이 null이 아닐 경우에 저장작업을 시작함
          String contentType = imageFile.getContentType();
          if (ObjectUtils.isEmpty(contentType) || !contentType.contains("image/")) {
            continue;
          } else {
            // DB에 추가
            imageNo++;
            DiaryImage diaryImage = new DiaryImage();
            diaryImage.setDiaryNo(diaryNo);
            diaryImage.setImageNo(imageNo);
            diaryImage.setFileName(imageNo + ".jpg");
            String storedFilePath = path + File.separator + imageNo + fileExtension;
            diaryImage.setStoredFilePath(storedFilePath);
            logger.info("DB에 추가할 diaryImage의 diaryNo : " + diaryNo + ", imageNo : " + imageNo
                + ", originalFileNmae : " + imageNo + ".jpg" + ", storedFilePath : "
                + storedFilePath + ", fileSize : " + imageFile.getSize());
            diaryImageRepository.insert(diaryImage);
            logger.info("절대경로 : " + absolutePath);
            // 변경된 파일이름으로 적용
            file = new File(absolutePath + storedFilePath);
            logger.info("파일경로+이름 : " + absolutePath + storedFilePath);
            imageFile.transferTo(file);
          }
        }
      }
      // 섬네일 생성 (섬네일 파일 이름은 중간에 "s_"로 시작하도록)
      String thumbnailSaveName =
          absolutePath + File.separator + path + File.separator + "thumbnail.png";
      File originalFile = new File(absolutePath + File.separator + path + File.separator + "1.jpg");
      File thumbnailFile = new File(thumbnailSaveName);
      DiaryImage diaryImage = new DiaryImage();
      diaryImage.setDiaryNo(diaryNo);
      diaryImage.setImageNo(imageNo + 1);
      String thumnailName = "thumbnail.png";
      diaryImage.setFileName(thumnailName);
      String storedFilePath = path + File.separator + thumnailName;
      diaryImage.setStoredFilePath(storedFilePath);
      logger.info("DB에 추가할 diaryImage(thumbnail)의 diaryNo : " + diaryNo + ", imageNo : " + imageNo
          + ", storedFilePath : " + storedFilePath);
      diaryImageRepository.insert(diaryImage);
      Thumbnailator.createThumbnail(originalFile, thumbnailFile, 200, 100);
    } catch (Exception e) {
      e.printStackTrace();
      File folder = new File(new File("").getAbsolutePath() + File.separator + "images"
          + File.separator + "diary_images" + File.separator + "diary" + diaryNo);
      try {
        if (folder.exists()) {
          FileUtils.cleanDirectory(folder);// 하위 폴더와 파일 모두 삭제

          if (folder.isDirectory()) {
            folder.delete(); // 대상폴더 삭제
            logger.debug(folder + "폴더가 삭제되었습니다.");
          }
        }
      } catch (IOException e1) {
        logger.error("폴더삭제 오류");
      }
      sqlSession.rollback();
      throw new ModifyException(diaryNo + "번 Diary를 DB에서 수정하는데 실패하였습니다. \n" + e.getMessage());
    } finally {
      sqlSession.close();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeDiary(int diaryNo) throws RemoveException {
    try {
      hashtagRepository.deleteAll(diaryNo);
      routeRepository.deleteAll(diaryNo);
      diaryImageRepository.deleteAll(diaryNo);
      likeRepository.deleteAll(diaryNo);
      commentRepository.deleteAll(diaryNo);
      diaryRepository.delete(diaryNo);
      File folder = new File(new File("").getAbsolutePath() + File.separator + "images"
          + File.separator + "diary_images" + File.separator + "diary" + diaryNo);
      // 이미지 삭제
      if (folder.exists()) {
        FileUtils.cleanDirectory(folder);// 하위 폴더와 파일 모두 삭제

        if (folder.isDirectory()) {
          folder.delete(); // 대상폴더 삭제
          logger.debug(folder + "폴더가 삭제되었습니다.");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoveException(diaryNo + "번 다이어리 삭제에 실패했습니다. \n" + e.getMessage());
    }
  }

  @Override
  public void writeComment(Comment comment) throws AddException {
    try {
      commentRepository.insert(comment);
    } catch (Exception e) {
      e.printStackTrace();
      throw new AddException("댓글 작성에 실패했습니다 \n" + e.getMessage());
    }
  }

  @Override
  public void modifyComment(Comment comment) throws ModifyException {
    try {
      commentRepository.update(comment);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ModifyException("댓글 수정에 실패했습니다. \n" + e.getMessage());
    }
  }

  @Override
  public void removeComment(int diaryNo, int commentNo) throws RemoveException {
    try {
      commentRepository.delete(diaryNo, commentNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoveException("댓글 삭제에 실패했습니다. \n" + e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void clickLikeToggle(boolean isLike, Like like) throws AddException, RemoveException {
    int diaryNo = like.getDiaryNo();
    String clientId = like.getClientId();
    if (isLike == true) {
      try {
        likeRepository.insert(like);
        diaryRepository.updateIncreaseLikeCnt(diaryNo);
      } catch (Exception e) {
        e.printStackTrace();
        throw new AddException(diaryNo + "번 다이어리의 좋아요를 1 증가시키는데 실패하였습니다. \n" + e.getMessage());
      }
    } else {
      try {
        Like likeInDb = likeRepository.selectLike(diaryNo, clientId);
        if (likeInDb == null) {
          throw new RemoveException("이미 좋아요를 한 다이어리에서만 좋아요 취소가 가능합니다.");
        }
        likeRepository.delete(like);
        diaryRepository.updateDecreaseLikeCnt(diaryNo);
      } catch (Exception e) {
        e.printStackTrace();
        throw new RemoveException(diaryNo + "번 다이어리의 좋아요를 1 감소시키는데 실패하였습니다. \n" + e.getMessage());
      }
    }
  }

  @Override
  public PageBean<Diary> showDiaryBoard(int order, int currentPage, List<String> hashtags)
      throws FindException {
    PageBean<Diary> pageBean = null;
    int cntPerPageGroup = 10;
    int cntPerPage = 10;
    int endRow = currentPage * 10;
    int startRow = endRow - (cntPerPage - 1);
    int totalRows;
    int currentPageNo = 13;
    try {
      totalRows = diaryRepository.selectCountByDisclosureFlag(1);
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("다이어리 갯수 정보를 찾을 수 없습니다 \n" + e.getMessage());
    }
    if (endRow > totalRows) { // 전체 페이지 수보다 더 높은 수의 페이지를 요청할 경우, 마지막 페이지의 다이어리들을 반환하도록 설정
      endRow = (int) Math.ceil((double) totalRows / cntPerPage) * cntPerPage;
      startRow = endRow - (cntPerPage - 1);
    }
    if (hashtags == null) {
      try {
        List<Diary> diaries = diaryRepository.selectDiaries(order, startRow, endRow, null);
        pageBean =
            new PageBean<Diary>(diaries, totalRows, currentPage, cntPerPageGroup, cntPerPage);
      } catch (Exception e) {
        e.printStackTrace();
        throw new FindException(currentPage + "번 페이지를 찾을 수 없습니다 \n" + e.getMessage());
      }
    } else {
      try {
        List<Diary> diaries = diaryRepository.selectDiaries(order, startRow, endRow, null);
        pageBean =
            new PageBean<Diary>(diaries, totalRows, currentPage, cntPerPageGroup, cntPerPage);
      } catch (Exception e) {
        e.printStackTrace();
        throw new FindException(currentPage + "번 페이지를 찾을 수 없습니다 \n" + e.getMessage());
      }
    }
    return pageBean;
  }

  @Override
  public PageBean<Diary> showMyDiaryBoard(int currentPage, String clientId) throws FindException {
    PageBean<Diary> pageBean = null;
    int cntPerPageGroup = 10;
    int cntPerPage = 10;
    int endRow = currentPage * 10;
    int startRow = endRow - (cntPerPage - 1);
    int totalRows;
    int currentPageNo = 13;

    try {
      totalRows = diaryRepository.selectCountByClientId(clientId);
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("다이어리 갯수 정보를 찾을 수 없습니다 \n" + e.getMessage());
    }
    if (endRow > totalRows) {// 전체 페이지 수보다 더 높은 수의 페이지를 요청할 경우, 마지막 페이지의 다이어리들을 반환하도록 설정
      endRow = (int) Math.ceil((double) totalRows / cntPerPage) * cntPerPage;
      startRow = endRow - (cntPerPage - 1);
    }
    try {
      List<Diary> diaries = diaryRepository.selectDiariesByClientId(clientId, startRow, endRow);
      pageBean = new PageBean<Diary>(diaries, totalRows, currentPage, cntPerPageGroup, cntPerPage);
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException(currentPage + "번 페이지를 찾을 수 없습니다 \n" + e.getMessage());
    }
    return pageBean;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Diary showDiary(int diaryNo) throws FindException {
    try {
      // 다이어리의 조회수 1 증가
      diaryRepository.updateViewCnt(diaryNo);

      // 다이어리번호의 다이어리를 조회한다
      Diary diary = diaryRepository.selectDiary(diaryNo);
      if (diary == null) {
        throw new FindException(diaryNo + "번 다이어리는 없는 다이어리 번호입니다.");
      }
      return diary;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException(diaryNo + "번 다이어리를 불러오는데 실패하였습니다. \n" + e.getMessage());
    }
  }

  @Override
  public Map<String, List<Diary>> showIndexPage() throws FindException {
    Map<String, List<Diary>> map = new HashMap<String, List<Diary>>();
    try {
      List<Diary> top5DiariesByLikeCnt = diaryRepository.selectDiaries(3, 1, 5, null);
      map.put("top5DiariesByLikeCnt", top5DiariesByLikeCnt);

      List<Diary> top5DiariesByWritingTime = diaryRepository.selectDiaries(2, 1, 5, null);
      map.put("top5DiariesByWritingTime", top5DiariesByWritingTime);
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("다이어리를 가져오는데 실패했습니다 \n" + e.getMessage());
    }
    return map;
  }
}
