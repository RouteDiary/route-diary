package com.routediary.service;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Admin;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.enums.Dto;
import com.routediary.enums.ErrorCode;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.MismatchException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.exception.RemoveException;
import com.routediary.functions.ServiceFunctions;
import com.routediary.repository.AdminRepository;
import com.routediary.repository.CommentRepository;
import com.routediary.repository.DiaryRepository;
import com.routediary.repository.HashtagRepository;
import com.routediary.repository.LikeRepository;
import com.routediary.repository.NoticeRepository;
import com.routediary.repository.RouteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "AdminService")
public class AdminServiceImpl implements AdminService {

  @Autowired
  private ServiceFunctions serviceFunctions;
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
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public Admin login(String adminId, String adminPwd) throws MismatchException, FindException {
    Admin admin = new Admin();
    Optional<Admin> adminOptional = Optional.ofNullable(adminRepository.selectAdminById(adminId));
    if (adminOptional.isPresent()) {
      admin = adminOptional.get();
      String adminPwdInDb = admin.getAdminPwd();
      if (adminPwd.equals(adminPwdInDb)) {
        return admin;
      }
    }
    throw new MismatchException(ErrorCode.ID_PWD_MISMATCH);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeDiary(int diaryNo) throws RemoveException {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    try {
      serviceFunctions.transferFilesToTempFolder(diaryNo, Dto.DIARY);
      hashtagRepository.deleteAll(diaryNo);
      routeRepository.deleteAll(diaryNo);
      likeRepository.deleteAll(diaryNo);
      commentRepository.deleteAll(diaryNo);
      sqlSession.commit();
      diaryRepository.delete(diaryNo);
      serviceFunctions.removeTempFiles(diaryNo, Dto.DIARY);
    } catch (Exception e) {
      serviceFunctions.transferFilesToOriginalFolder(diaryNo, Dto.DIARY);
      sqlSession.rollback();
      throw new RemoveException();
    }
  }

  @Override
  public void removeComment(int diaryNo, int commentNo) throws RemoveException {
    commentRepository.delete(diaryNo, commentNo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void writeNotice(Notice notice, List<MultipartFile> imgFiles) throws AddException {
    int noticeNo = 0;// currentValue 를 찾아주는 메서드.
    try {
      noticeRepository.insert(notice);
      noticeNo = noticeRepository.selectLatestNoticeNo();
      if (imgFiles != null) {
        serviceFunctions.saveImages(noticeNo, Dto.NOTICE, imgFiles);
      }
    } catch (Exception e) {
      serviceFunctions.removeOriginalFiles(noticeNo, Dto.NOTICE);
      throw new AddException();
    }
  }

  @Override
  public void modifyNotice(Notice notice, List<MultipartFile> imgFiles) throws ModifyException {
    int noticeNo = notice.getNoticeNo();
    try {
      serviceFunctions.transferFilesToTempFolder(noticeNo, Dto.NOTICE);
      noticeRepository.update(notice);
      serviceFunctions.saveImages(noticeNo, Dto.NOTICE, imgFiles);
      serviceFunctions.removeTempFiles(noticeNo, Dto.NOTICE);
    } catch (Exception e) {
      serviceFunctions.transferFilesToOriginalFolder(noticeNo, Dto.NOTICE);
      throw new ModifyException();
    }
  }

  @Override
  public void removeNotice(int noticeNo) throws RemoveException {
    try {
      serviceFunctions.transferFilesToTempFolder(noticeNo, Dto.NOTICE);
      noticeRepository.delete(noticeNo);
      serviceFunctions.removeTempFiles(noticeNo, Dto.NOTICE);
    } catch (Exception e) {
      serviceFunctions.transferFilesToOriginalFolder(noticeNo, Dto.NOTICE);
      throw new RemoveException();
    }
  }

  @Override
  public PageBean<Diary> showDiaryBoard(int order, int currentPage, List<String> hashtags)
      throws FindException, NumberNotFoundException {
    List<Diary> diaries = null;
    if (order >= 4 || order <= 0) {
      throw new NumberNotFoundException(ErrorCode.INVALID_ORDER);
    }
    int totalRows = diaryRepository.selectCountAll(hashtags);

    int[] rowArr = serviceFunctions.calculateStartAndEndRow(currentPage, totalRows);
    int startRow = rowArr[0];
    int endRow = rowArr[1];
    if (hashtags == null) {
      diaries = diaryRepository.selectAllDiaries(order, startRow, endRow, null);
    } else {
      diaries = diaryRepository.selectAllDiaries(order, startRow, endRow, hashtags);
    }

    PageBean<Diary> pageBean = (PageBean<Diary>) serviceFunctions.calculatePageBean(currentPage,
        totalRows, (List<?>) diaries);
    return pageBean;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Diary showDiary(int diaryNo) throws FindException, NumberNotFoundException {
    // 다이어리의 조회수 1 증가
    diaryRepository.updateViewCnt(diaryNo);

    // 다이어리번호의 다이어리를 조회한다
    Diary diary = diaryRepository.selectDiary(diaryNo);
    if (diary == null) {
      throw new NumberNotFoundException(ErrorCode.POST_NOT_FOUND);
    }
    return diary;
  }
}
