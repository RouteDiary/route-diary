package com.routediary.control;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Admin;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.service.AdminServiceImpl;
import com.routediary.service.NoticeServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("admin/*")
public class AdminController {
  @Autowired
  private AdminServiceImpl adminService;
  @Autowired
  private NoticeServiceImpl noticeService;


  @GetMapping(value = "write")
  public String write() {
    return "write";
  }

  // -------로그인 관련 START
  @GetMapping(value = {"login"})
  public ResultBean<?> login(@RequestParam(name = "adminId", required = true) String adminId,
      @RequestParam(name = "adminPwd", required = true) String adminPwd, HttpSession session)
      throws FindException {

    ResultBean<?> resultBean = new ResultBean();
    Admin admin = adminService.login(adminId, adminPwd);
    resultBean.setStatus(1);
    resultBean.setMessage("로그인 성공입니다^_^");
    session.setAttribute("loginInfo", adminId);

    return resultBean;

  }

  @GetMapping(value = {"loginstatus"})
  public ResultBean<?> loginStatus(HttpSession session) {
    ResultBean<?> resultBean = new ResultBean();
    String adminId = (String) session.getAttribute("loginInfo");

    if (adminId == null) {
      resultBean.setStatus(0);
    } else {
      resultBean.setStatus(1);
      resultBean.setMessage("로그인 되어있습니다");
    }
    return resultBean;
  }

  @GetMapping(value = {"logout"})
  public ResponseEntity<?> logout(HttpSession session) {
    session.removeAttribute("loginInfo"); 
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  // --------로그인 관련 END
  // --------다이어리 관련 START
  @GetMapping(value = {"diary/list", "diary/list/{currentPageOpt}"})
  public ResultBean<PageBean<Diary>> showDiaryBoard(@PathVariable Optional<Integer> currentPageOpt,
      @RequestParam(name = "order", required = false, defaultValue = "2") int order)
      throws FindException {
    ResultBean<PageBean<Diary>> resultBean = new ResultBean<PageBean<Diary>>();
    int currentPage;
    if (currentPageOpt.isPresent()) {
      currentPage = currentPageOpt.get();
    } else {
      currentPage = 1;
    }

    PageBean<Diary> diaries = adminService.showDiaryBoard(order, currentPage, null);
    resultBean.setStatus(1);
    resultBean.setMessage("다이어리 가져오는데 성공했습니다");
    resultBean.setT(diaries);
    return resultBean;

  }

  @GetMapping(value = {"diary/{diaryNoOpt}"})
  public ResultBean<Diary> showDiary(@PathVariable Optional<Integer> diaryNoOpt)
      throws FindException {
    ResultBean<Diary> resultBean = new ResultBean<Diary>();
    int diaryNo;
    if (diaryNoOpt.isPresent()) {
      diaryNo = diaryNoOpt.get();
    } else {
      throw new FindException("다이어리가 없습니다");
    }
    Diary diary = adminService.showDiary(diaryNo);
    resultBean.setStatus(1);
    resultBean.setMessage("다이어리 가져오는데 성공했습니다");
    resultBean.setT(diary);
    return resultBean;

  }

  @DeleteMapping(value = {"diary/{diaryNo}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> removeDiary(@PathVariable int diaryNo, HttpSession session)
      throws RemoveException {
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      return new ResponseEntity<String>("관리자가 아닙니다", HttpStatus.BAD_REQUEST);
    }
    adminService.removeDiary(diaryNo);
    return new ResponseEntity<String>("삭제되었습니다", HttpStatus.OK);
  }

  @DeleteMapping(value = {"diary/{diaryNo}/comment"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeComment(@PathVariable int diaryNo,
      @RequestParam(name = "commentNo", required = true) int commentNo, HttpSession session)
      throws RemoveException {
    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      return new ResponseEntity<String>("관리자가 아닙니다", HttpStatus.BAD_REQUEST);
    }
    adminService.removeComment(diaryNo, commentNo);
    return new ResponseEntity<String>("댓글이 삭제되었습니다", HttpStatus.OK);

  }

  // --------다이어리 관련 END
  // --------공지사항 관련 START
  @PutMapping(value = {"notice/{noticeNo}"}, consumes = {"multipart/form-data"})
  public ResponseEntity<?> modifyNotice(@PathVariable int noticeNo,
      @RequestPart(required = false) List<MultipartFile> imgFiles, @RequestPart Notice notice,
      HttpSession session) throws ModifyException {

    String adminId = (String) session.getAttribute("loginInfo");
    if (notice.getNoticeTitle() == null || notice.getNoticeTitle().equals("")
        || notice.getNoticeContent() == null || notice.getNoticeContent().equals("")) {
      return new ResponseEntity<>("공지사항이 없습니다", HttpStatus.BAD_REQUEST);
    } else if (adminId == null) {
      return new ResponseEntity<>("관리자가 아닙니다", HttpStatus.BAD_REQUEST);
    }
    adminService.modifyNotice(notice, imgFiles);
    return new ResponseEntity<>("공지사항이 수정되었습니다", HttpStatus.OK);

  }

  @DeleteMapping(value = {"notice/{noticeNo}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeNotice(
      @PathVariable @RequestParam(name = "noticeNo", required = true) int noticeNo,
      HttpSession session) throws RemoveException {

    String adminId = (String) session.getAttribute("loginInfo");
    if (adminId == null) {
      return new ResponseEntity<>("관리자가 아닙니다", HttpStatus.BAD_REQUEST);
    }
    adminService.removeNotice(noticeNo);
    return new ResponseEntity<>("공지사항이 삭제되었습니다", HttpStatus.OK);
  }

  @PostMapping(value = "notice/write", consumes = {"multipart/form-data"})
  public ResultBean<?> writeNotice(@RequestPart(required = false) List<MultipartFile> imgFiles,
      @RequestPart Notice notice, HttpSession session) throws AddException {
    ResultBean<?> resultBean = new ResultBean<>();
    String adminId = (String) session.getAttribute("loginInfo");
    notice.setAdminId(adminId);

    adminService.writeNotice(notice, imgFiles);
    resultBean.setStatus(1);
    resultBean.setMessage("공지사항 작성 성공했습니다");
    return resultBean;


  }
  // --------공지사항 관련 END
}
