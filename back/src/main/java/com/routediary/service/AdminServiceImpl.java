package com.routediary.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Admin;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.NoticeImage;
import com.routediary.dto.PageBean;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.repository.AdminRepository;
import com.routediary.repository.CommentRepository;
import com.routediary.repository.DiaryImageRepository;
import com.routediary.repository.DiaryRepository;
import com.routediary.repository.HashtagRepository;
import com.routediary.repository.LikeRepository;
import com.routediary.repository.NoticeImageRepository;
import com.routediary.repository.NoticeRepository;
import com.routediary.repository.RouteRepository;

@Service(value = "AdminService")
public class AdminServiceImpl implements AdminService {
  private static final int CNT_PER_PAGE = 10;
  private static final int CNT_PER_PAGE_GROUP = 10;

  @Autowired
  private AdminRepository adminRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private DiaryRepository diaryRepository;
  @Autowired
  private RouteRepository routeRepository;
  @Autowired
  private NoticeRepository noticeRepository;
  @Autowired
  private LikeRepository likeRepository;
  @Autowired
  private HashtagRepository hashtagRepository;
  @Autowired
  private NoticeImageRepository noticeImageRepository;
  @Autowired
  private DiaryImageRepository diaryImageRepository;

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public Admin login(String adminId, String adminPwd) throws FindException {

    Admin admin = new Admin();
    try {
      Optional<Admin> adminOptional = Optional.ofNullable(adminRepository.selectAdminById(adminId));
      if (adminOptional.isPresent()) {
        admin = adminOptional.get();
        String adminPwdRepo = admin.getAdminPwd();
        if (adminPwd.equals(adminPwdRepo)) {
          return admin;
        }
      }
      throw new FindException("아이디 혹은 비밀번호가 일치하지 않습니다.");
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException(e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeDiary(int diaryNo) throws RemoveException {
    try {
      if (diaryRepository.selectDiary(diaryNo) == null) {
        throw new Exception("존재하지않는 다이어리 입니다");
      }
      commentRepository.deleteAll(diaryNo);
      hashtagRepository.deleteAll(diaryNo);
      likeRepository.deleteAll(diaryNo);
      routeRepository.deleteAll(diaryNo);
      diaryImageRepository.deleteAll(diaryNo);
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
      throw new RemoveException(e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeComment(int diaryNo, int commentNo) throws RemoveException {
    try {
//      Diary diary = diaryRepository.selectDiary(diaryNo);
//      List<Comment> comment = diary.getComments();
//      if (comment.get(commentNo) == null) {
//        throw new RemoveException("댓글이존재하지 않습니다");
//      }
//      Comment comment = comment.get
//      if(commentRep)
      commentRepository.delete(diaryNo, commentNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoveException("댓글삭제 되지않았습니다" + e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void writeNotice(Notice notice, List<MultipartFile> imgFiles) throws AddException {
    int noticeNo = 0;// currentValue 를 찾아주는 메서드.
    try {
      noticeRepository.insert(notice);
      noticeNo = noticeRepository.selectLatestNoticeNo(); // currentValue 를 찾아주는 메서드.
      // ---이미지 업로드 START
      String absolutePath = new File("").getAbsolutePath() + File.separator; // 이미지파일을 저장할 경로중 절대경로
      logger.info(absolutePath); /// Users/minseong/Desktop/semi-project/back/ -->운영체제마다 다른걸
                                 /// file.sperator 로 맞춰줌.
      // 이미지 파일 저장 로직
      int imageNo = 0; // 생성자.
      String path =
          "images" + File.separator + "notice_images" + File.separator + "notice" + noticeNo;
      File file = new File(path);
      if (!file.exists()) {
        file.mkdirs(); // 디렉토리가 없을 경우, 디렉토리 생성
      }
      // DB 저장작업 시작
      if (imgFiles != null) {
        for (MultipartFile imgFile : imgFiles) {
          long imgFileSize = imgFiles.size();
          if (imgFileSize > 0) {
            imageNo++;
            NoticeImage noticeImage = new NoticeImage();
            noticeImage.setImageNo(imageNo); // 이미지 번호
            noticeImage.setNoticeNo(noticeNo); // 이미지의 공지사항 번호
            noticeImage.setFileName(imageNo + ".jpg"); // 이미지이름
            String storedFilePath = path + File.separator + imageNo + ".jpg";
            noticeImage.setStoredFilePath(storedFilePath);// 저장 경로.

            logger.info("DB에 추가할 noticeImage의 noticeNo : " + noticeNo + ", imageNo : " + imageNo
                + ", originalFileNmae : " + imageNo + ".jpg" + ", storedFilePath : "
                + storedFilePath + ", fileSize : " + imgFile.getSize());

            noticeImageRepository.insert(noticeImage); // DB에 추가

            // 파일이름변경 적용
            file = new File(absolutePath + storedFilePath);
            logger.info("파일경로+이름 : " + absolutePath + storedFilePath);
            imgFile.transferTo(file); //
          }

        }
      }

      // ---이미지 업로드 END
    } catch (Exception e) {
      e.printStackTrace();
      File folder = new File(new File("").getAbsolutePath() + File.separator + "images"
          + File.separator + "notice_images" + File.separator + "notice" + noticeNo);
      try {
        if (folder.exists()) {
          FileUtils.cleanDirectory(folder);// 하위 폴더와 파일 모두 삭제

          if (folder.isDirectory()) {
            folder.delete(); // 대상폴더 삭제
            logger.debug(folder + "폴더가 삭제되었습니다.");
          }
        }
      } catch (Exception e1) {
        logger.error("폴더삭제 오류");
      }
      throw new AddException("notice를 DB에 추가하는데 실패하였습니다. \n" + e.getMessage());
    }



  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void modifyNotice(Notice notice, List<MultipartFile> imgFiles) throws ModifyException {
    int noticeNo = notice.getNoticeNo();
    try {
      noticeRepository.update(notice);
      noticeImageRepository.deleteAll(noticeNo);
      File folder = new File(new File("").getAbsolutePath() + File.separator + "images"
          + File.separator + "notice_images" + File.separator + "notice" + noticeNo);
      if (folder.exists()) {
        FileUtils.cleanDirectory(folder);// 하위 폴더와 파일 모두 삭제
      }
      String absolutePath = new File("").getAbsolutePath() + File.separator; // 이미지파일을 저장할 경로중 절대경로
      logger.info(absolutePath); /// Users/minseong/Desktop/semi-project/back/ -->운영체제마다 다른걸
                                 /// file.sperator 로 맞춰줌.
      // 이미지 파일 저장 로직
      int imageNo = 0; // 생성자.
      String path =
          "images" + File.separator + "notice_images" + File.separator + "notice" + noticeNo;
      File file = new File(path);
      if (!file.exists()) {
        file.mkdirs(); // 디렉토리가 없을 경우, 디렉토리 생성
      }
      // DB 저장작업 시작
      if (imgFiles != null) {
        for (MultipartFile imgFile : imgFiles) {
          long imgFileSize = imgFiles.size();
          if (imgFileSize > 0) {
            imageNo++;
            NoticeImage noticeImage = new NoticeImage();
            noticeImage.setImageNo(imageNo); // 이미지 번호
            noticeImage.setNoticeNo(noticeNo); // 이미지의 공지사항 번호
            noticeImage.setFileName(imageNo + ".jpg"); // 이미지이름
            String storedFilePath = path + File.separator + imageNo + ".jpg";
            noticeImage.setStoredFilePath(storedFilePath);// 저장 경로.

            logger.info("DB에 추가할 noticeImage의 noticeNo : " + noticeNo + ", imageNo : " + imageNo
                + ", originalFileNmae : " + imageNo + ".jpg" + ", storedFilePath : "
                + storedFilePath + ", fileSize : " + imgFile.getSize());

            noticeImageRepository.insert(noticeImage); // DB에 추가

            // 파일이름변경 적용
            file = new File(absolutePath + storedFilePath);
            logger.info("파일경로+이름 : " + absolutePath + storedFilePath);
            imgFile.transferTo(file); //
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new ModifyException("저장되지 않았습니다" + e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeNotice(int noticeNo) throws RemoveException {
    try {
      if (noticeRepository.selectNotice(noticeNo) == null) {
        throw new Exception("존재하지 않는 다이어리 입니다.");
      }
      noticeRepository.delete(noticeNo);
      noticeImageRepository.deleteAll(noticeNo);
      File folder = new File(new File("").getAbsolutePath() + File.separator + "images"
          + File.separator + "notice_images" + File.separator + "notice" + noticeNo);
      if (folder.exists()) {
        FileUtils.cleanDirectory(folder);// 하위 폴더와 파일 모두 삭제
      }
        if (folder.isDirectory()) {
          folder.delete(); // 대상폴더 삭제
          logger.debug(folder + "폴더가 삭제되었습니다.");
        }
      
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoveException("존재하지 않는 다이어리 입니다" + e.getMessage());
    }

  }

  @Override
  public PageBean<Diary> showDiaryBoard(int order, int currentPage, List<String> hashtags)
      throws FindException {
    PageBean<Diary> pageBean = null;
    int endRow = currentPage * CNT_PER_PAGE; // 10 20
    int startRow = endRow - CNT_PER_PAGE + 1; // 1 11
    try {
      long totalRows = diaryRepository.selectCountAll();
      if (totalRows == 0) {
        throw new FindException("다이어리가 없습니다");
      }
      List<Diary> diaries = diaryRepository.selectAllDiaries(order, startRow, endRow, null);
      pageBean =
          new PageBean<Diary>(diaries, totalRows, currentPage, CNT_PER_PAGE, CNT_PER_PAGE_GROUP);
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("다이어리가 없습니다" + e.getMessage());
    }
    return pageBean;
  }

  @Override
  public Diary showDiary(int diaryNo) throws FindException {
    Optional<Diary> diaryOpt = Optional.empty();
    try {
      diaryOpt = Optional.ofNullable(diaryRepository.selectDiary(diaryNo));
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("게시글이 없습니다" + e.getMessage());
    }
    if (diaryOpt.isPresent()) {
      return diaryOpt.get();

    } else {
      throw new FindException("게시글이 없습니다");
    }
  }

}
