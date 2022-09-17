package com.routediary.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.enums.Dto;
import com.routediary.enums.SuccessCode;
import com.routediary.exception.FindException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.functions.ServiceFunctions;
import com.routediary.service.NoticeService;

@RestControllerAdvice
@RequestMapping("notice/*")
public class NoticeController {
  @Autowired
  ServiceFunctions serviceFunctions;
  @Autowired
  NoticeService noticeService;

  @GetMapping(value = {"list", "list/{pageNo}"})
  public ResponseEntity<?> showNoticeBoard(@PathVariable Optional<Integer> pageNo,
      HttpSession session) throws FindException {
    int currentPage;
    if (pageNo.isPresent()) {
      currentPage = pageNo.get();
    } else {
      currentPage = 1;
    }
    String loginedId = (String) session.getAttribute("loginInfo");
    PageBean<Notice> pageBean = noticeService.showNoticeBoard(currentPage);
    ResultBean<PageBean<Notice>> resultBean =
        new ResultBean<PageBean<Notice>>(SuccessCode.PAGE_LOAD_SUCCESS);
    resultBean.setT(pageBean);
    resultBean.setLoginInfo(loginedId);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @GetMapping(value = "list/{keyword}/{pageNo}")
  public ResponseEntity<?> showNoticeBoardByKeyword(@PathVariable Optional<Integer> pageNo,
      @PathVariable String keyword, HttpSession session) throws FindException {
    int currentPage;
    if (pageNo.isPresent()) {
      currentPage = pageNo.get();
    } else {
      currentPage = 1;
    }
    String loginedId = (String) session.getAttribute("loginInfo");
    PageBean<Notice> pageBean = noticeService.showNoticeBoardByKeyword(currentPage, keyword);
    ResultBean<PageBean<Notice>> resultBean =
        new ResultBean<PageBean<Notice>>(SuccessCode.PAGE_LOAD_SUCCESS);
    resultBean.setT(pageBean);
    resultBean.setLoginInfo(loginedId);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @GetMapping(value = {"/{noticeNo}"})
  public ResponseEntity<?> showNotice(@PathVariable int noticeNo, HttpSession session)
      throws FindException, NumberNotFoundException {
    String loginedId = (String) session.getAttribute("loginInfo");
    Notice notice = noticeService.showNotice(noticeNo);
    int imageFilesCount = serviceFunctions.getImageFilesCount(noticeNo, Dto.NOTICE);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("notice", notice);
    map.put("imageFilesCount", imageFilesCount);
    ResultBean<Map<String, Object>> resultBean =
        new ResultBean<Map<String, Object>>(SuccessCode.NOTICE_LOAD_SUCCESS);
    resultBean.setT(map);
    resultBean.setLoginInfo(loginedId);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }
}
