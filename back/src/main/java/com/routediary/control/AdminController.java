package com.routediary.control;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.routediary.dto.Admin;
import com.routediary.dto.Diary;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.exception.FindException;
import com.routediary.service.AdminServiceImpl;
import com.routediary.service.NoticeServiceImpl;

@RestControllerAdvice
@RequestMapping("admin/*")
public class AdminController {
  @Autowired
  private AdminServiceImpl adminService;
  @Autowired
  private NoticeServiceImpl noticeService;

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
    session.removeAttribute("loginInfo"); // 바
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
  public ResultBean<PageBean<Diary>> showDiaryBoard(
      @PathVariable Optional<Integer> currentPageOpt,
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

  @GetMapping(value = {"{diary}"})
  public ResultBean<Diary> showDiary(@RequestParam(name="diaryNo" ,required=true)Optional<Integer> diaryNoOpt) throws FindException {
    ResultBean<Diary> resultBean = new ResultBean<Diary>();
    int diaryNo;
    if(diaryNoOpt.isPresent()) {
      diaryNo = diaryNoOpt.get();
    }else {
     throw new FindException("다이어리가 없습니다"); 
    }
    Diary diary = adminService.showDiary(diaryNo);
    resultBean.setStatus(1);
    resultBean.setMessage("다이어리 가져오는데 성공했습니다");
    resultBean.setT(diary);
    return resultBean;

  }
  @DeleteMapping(value = {"{diary}"})
  public ResponseEntity<?> removeDiary(int diaryNo){
    return null;
  }

  // --------다이어리 관련 END
}
