package com.routediary.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.routediary.dto.Admin;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.repository.AdminRepository;
import com.routediary.repository.CommentRepository;
import com.routediary.repository.DiaryRepository;
import com.routediary.repository.HashtagRepository;
import com.routediary.repository.LikeRepository;
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
  @Transactional(rollbackFor = RemoveException.class)
  public void removeDiary(int diaryNo) throws RemoveException {
    try {
      if(diaryRepository.selectDiary(diaryNo)==null) {
        throw new Exception("존재하지않는 다이어리 입니다");
      }
      commentRepository.deleteAll(diaryNo);
      hashtagRepository.deleteAll(diaryNo);
      likeRepository.deleteAll(diaryNo);
      routeRepository.deleteAll(diaryNo);
      diaryRepository.delete(diaryNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoveException(e.getMessage());
    } 
  }

  @Override
  @Transactional(rollbackFor = RemoveException.class)
  public void removeComment(int diaryNo, int commentNo) throws RemoveException {
    try {
      Diary diary = diaryRepository.selectDiary(diaryNo);
      if(diary.getComments()==null) {
        throw new RemoveException("댓글이 없습니다");
      }
      commentRepository.delete(diaryNo, commentNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoveException("댓글삭제 되지않았습니다"+e.getMessage());
    }
  }

  @Override
  public void writeNotice(Notice notice) throws AddException {
    try {
      noticeRepository.insert(notice);
    } catch (Exception e) {
      e.printStackTrace();
      throw new AddException("저장되지않았습니다"+e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = ModifyException.class)
  public void modifyNotice(Notice notice) throws ModifyException {
    try {
      noticeRepository.update(notice);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ModifyException("저장되지 않았습니다"+e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = RemoveException.class)
  public void removeNotice(int noticeNo) throws RemoveException {
    try {
      if (noticeRepository.selectNotice(noticeNo) == null) {
        throw new Exception("존재하지 않는 다이어리 입니다.");
      }
      noticeRepository.delete(noticeNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoveException("존재하지 않는 다이어리 입니다"+e.getMessage());
    } 
    
  }

  @Override
  public PageBean<Diary> showDiaryBoard(int order, int currentPage, List<String> hashtags)
      throws FindException {
    PageBean<Diary> pageBean = null;
    int endRow = currentPage * CNT_PER_PAGE; // 10 20
    int startRow = endRow - CNT_PER_PAGE + 1; // 1 11
    try {
      long totalRows = diaryRepository.selectCountByAllDiaries();
      if(totalRows == 0) {
        throw new FindException("다이어리가 없습니다");
      }
      List<Diary>diaries = diaryRepository.selectAllDiaries(order, startRow, endRow, null);
      pageBean = new PageBean<Diary>(diaries,totalRows,currentPage,CNT_PER_PAGE,CNT_PER_PAGE_GROUP);
    }catch (Exception e) {
      e.printStackTrace();
      throw new FindException("다이어리가 없습니다"+e.getMessage());
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
      throw new FindException("게시글이 없습니다"+e.getMessage());
    }
    if(diaryOpt.isPresent()) {
      return diaryOpt.get();
      
    }else {
      throw new FindException("게시글이 없습니다");
    }
  }

}
