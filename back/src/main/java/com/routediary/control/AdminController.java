package com.routediary.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.routediary.dto.Admin;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.enums.Dto;
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
import com.routediary.functions.ServiceFunctions;
import com.routediary.service.AdminService;

@CrossOrigin("*")
@RestControllerAdvice
@RequestMapping("admin/*")
public class AdminController {
  @Autowired
  ServiceFunctions serviceFunctions;
  @Autowired
  private AdminService adminService;

  @GetMapping(value = "login")
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
      @PathVariable int order, @RequestParam(required=false) List<String> hashtags)
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
  public ResponseEntity<?> showDiary(@PathVariable int diaryNo,HttpSession session)
      throws FindException, NumberNotFoundException {
    String adminId = (String) session.getAttribute("adminLoginInfo");

    Diary diary = adminService.showDiary(diaryNo);
    int imageFilesCount = serviceFunctions.getImageFilesCount(diaryNo, Dto.DIARY) - 1; // 섬네일 파일 제외한
    boolean likeFlag = false; // 좋아요선택안함
//    for (Like like : diary.getLikes()) {
//      if (adminId.equals(like.getClientId())) {
//        likeFlag = true; // 좋아요선택한 경우
//      }
//    }
    ResultBean<Map<String, Object>> resultBean =
        new ResultBean<Map<String, Object>>(SuccessCode.DIARY_LOAD_SUCCESS);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("diary", diary);
    map.put("loginedId", adminId);
    map.put("imageFilesCount", imageFilesCount);
    map.put("likeFlag", likeFlag);
    resultBean.setT(map);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @DeleteMapping(value = "diary/{diaryNo}")
  public ResponseEntity<?> removeDiary(@PathVariable int diaryNo,HttpSession session)
      throws RemoveException, NotLoginedException {
    String adminId = (String) session.getAttribute("adminLoginInfo");
      adminService.removeDiary(diaryNo);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_REMOVE);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @DeleteMapping(value = "diary/{diaryNo}/comment")
  public ResponseEntity<?> removeComment(@PathVariable int diaryNo, @RequestBody Comment comment,
      HttpSession session) throws RemoveException, NotLoginedException {
    String adminId = (String) session.getAttribute("adminLoginInfo");
    //--sample
    adminId = "msk@kosta.com";
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
      @RequestPart(required = false) List<MultipartFile> imageFiles, String notice,
      HttpSession session) throws AddException, NotLoginedException, EmptyContentException,
      JsonMappingException, JsonProcessingException {
    String adminId = (String) session.getAttribute("loginInfo");
    adminId = "msk@kosta.com";
    ObjectMapper mapper = new ObjectMapper();
    Notice n = null;
    n = mapper.readValue(notice, Notice.class);
    if (adminId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else if (n.getNoticeTitle().equals("") || n.getNoticeTitle() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_TITLE);
    } else if (n.getNoticeContent().equals("") || n.getNoticeContent() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_CONTENT);
    } else {
      n.setAdminId(adminId);
      adminService.writeNotice(n, imageFiles);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_WRITE);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }

  @PutMapping(value = "notice/{noticeNo}")
  public ResponseEntity<?> modifyNotice(@PathVariable int noticeNo,
      @RequestPart(required = false) List<MultipartFile> imageFiles, String notice,
      HttpSession session) throws ModifyException, NotLoginedException, EmptyContentException,
      JsonMappingException, JsonProcessingException {
    String adminId = (String) session.getAttribute("loginInfo");
//    -- sample입니다
    adminId = "msk@kosta.com";
    ObjectMapper mapper = new ObjectMapper();
    Notice n = null;
    n = mapper.readValue(notice, Notice.class);
    if (adminId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else if (n.getNoticeTitle().equals("") || n.getNoticeTitle() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_TITLE);
    } else if (n.getNoticeContent().equals("") || n.getNoticeContent() == null) {
      throw new EmptyContentException(ErrorCode.EMPTY_CONTENT);
    } else {
      adminService.modifyNotice(n, imageFiles);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_MODIFY);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }

  @DeleteMapping(value = "notice/{noticeNo}")
  public ResponseEntity<?> removeNotice(@PathVariable int noticeNo, HttpSession session)
      throws RemoveException, NotLoginedException, EmptyContentException {
    String adminId = (String) session.getAttribute("loginInfo");
    //---sampledata
    adminId = "msk@kosta.com";
    if (adminId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else {
      adminService.removeNotice(noticeNo);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_REMOVE);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }
}
