package com.routediary.control;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Admin;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.enums.ErrorCode;
import com.routediary.enums.SuccessCode;
import com.routediary.exception.AddException;
import com.routediary.exception.EmptyContentException;
import com.routediary.exception.FindException;
import com.routediary.exception.LogoutFailureException;
import com.routediary.exception.MismatchException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.NotLoginedException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.exception.RemoveException;
import com.routediary.service.AdminService;
@CrossOrigin(origins = "*")
@RestControllerAdvice
@RequestMapping("admin/*")
public class AdminController {
  @Autowired
  private AdminService adminService;

  @PostMapping(value = "login")
  public ResponseEntity<?> login(@RequestBody Admin admin, HttpSession session)
      throws MismatchException, FindException {
    String adminId = admin.getAdminId();
    String adminPwd = admin.getAdminPwd();
    adminService.login(adminId, adminPwd);
    session.setAttribute("loginInfo", adminId);
    ResultBean<?> resultBean = new ResultBean(SuccessCode.LOGIN_SUCCESS);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @GetMapping(value = "logout")
  public ResponseEntity<?> logout(HttpSession session) throws LogoutFailureException {
    session.removeAttribute("loginInfo");
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      ResultBean<?> resultBean = new ResultBean(SuccessCode.LOGOUT_SUCCESS);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    } else {
      throw new LogoutFailureException(ErrorCode.FAILED_TO_LOGOUT);
    }
  }

  @GetMapping(value = {"diary/list/{order}", "diary/list/{order}/{pageNo}"})
  public ResponseEntity<?> showDiaryBoard(@PathVariable Optional<Integer> pageNo,
      @PathVariable int order, @RequestBody List<String> hashtags)
      throws FindException, NumberNotFoundException {
    int currentPageNo;
    if (pageNo.isPresent()) {
      currentPageNo = pageNo.get();
    } else {
      currentPageNo = 1;
    }
    PageBean<Diary> pageBean = adminService.showDiaryBoard(order, currentPageNo, hashtags);
    ResultBean<PageBean<Diary>> resultBean = new ResultBean(SuccessCode.PAGE_LOAD_SUCCESS);
    resultBean.setT(pageBean);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @GetMapping(value = "diary/{diaryNo}")
  public ResponseEntity<?> showDiary(@PathVariable int diaryNo)
      throws FindException, NumberNotFoundException {
    Diary diary = adminService.showDiary(diaryNo);
    ResultBean<Diary> resultBean = new ResultBean(SuccessCode.PAGE_LOAD_SUCCESS);
    resultBean.setT(diary);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @DeleteMapping(value = "diary/{diaryNo}")
  public ResponseEntity<?> removeDiary(@PathVariable int diaryNo, HttpSession session)
      throws RemoveException, NotLoginedException {
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else {
      adminService.removeDiary(diaryNo);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_REMOVE);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }

  @DeleteMapping(value = "diary/{diaryNo}/comment")
  public ResponseEntity<?> removeComment(@PathVariable int diaryNo, @RequestBody Comment comment,
      HttpSession session) throws RemoveException, NotLoginedException {
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId != null) {
      adminService.removeComment(diaryNo, comment.getCommentNo());
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_REMOVE);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    } else {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    }
  }

  @PostMapping(value = "notice/write")
  public ResponseEntity<?> writeNotice(
      @RequestPart(required = false) List<MultipartFile> imageFiles, @RequestPart Notice notice,
      HttpSession session) throws AddException, NotLoginedException, EmptyContentException {
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else if (notice.getNoticeTitle().equals("") || notice.getNoticeTitle() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_TITLE);
    } else if (notice.getNoticeContent().equals("") || notice.getNoticeContent() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_CONTENT);
    } else {
      adminService.writeNotice(notice, imageFiles);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_WRITE);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }

  @PutMapping(value = "notice/{noticeNo}")
  public ResponseEntity<?> modifyNotice(@PathVariable int noticeNo,
      @RequestPart(required = false) List<MultipartFile> imageFiles, @RequestPart Notice notice,
      HttpSession session) throws ModifyException, NotLoginedException, EmptyContentException {
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else if (notice.getNoticeTitle().equals("") || notice.getNoticeTitle() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_TITLE);
    } else if (notice.getNoticeContent().equals("") || notice.getNoticeContent() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_CONTENT);
    } else {
      adminService.modifyNotice(notice, imageFiles);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_MODIFY);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }

  @DeleteMapping(value = "notice/{noticeNo}")
  public ResponseEntity<?> removeNotice(@PathVariable int noticeNo, HttpSession session)
      throws RemoveException, NotLoginedException, EmptyContentException {
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else {
      adminService.removeNotice(noticeNo);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_REMOVE);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }
}
