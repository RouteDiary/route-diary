package com.routediary.control;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.exception.FindException;
import com.routediary.service.NoticeServiceImpl;

@RestControllerAdvice
@RequestMapping("notice/*")
public class NoticeController {
  @Autowired
  NoticeServiceImpl noticeService;

  private Logger logger = LoggerFactory.getLogger(getClass());

  // 공지사항 관련 START
  @GetMapping(value = {"list", "list/{currentPageOpt}"})
  public ResultBean<PageBean<Notice>> showNoticeBoard(
      @PathVariable Optional<Integer> currentPageOpt) throws FindException {
    ResultBean<PageBean<Notice>> resultBean = new ResultBean<PageBean<Notice>>();
    int currentPage;
    if (currentPageOpt.isPresent()) {
      currentPage = currentPageOpt.get();
    } else {
      currentPage = 1;
    }

    PageBean<Notice> notice = noticeService.showNoticeBoard(currentPage);
    logger.error("currentPage =" + currentPage); // 오 짱이다...
    if (currentPage > notice.getTotalPage()) {
      currentPage = notice.getTotalPage();
      resultBean.setStatus(0);
      resultBean.setMessage("공지사항 가져오는데 실패했습니다.");
      return resultBean;
    }
    resultBean.setStatus(1);
    resultBean.setMessage("공지사항 가져오는 성공했습니다.");
    resultBean.setT(notice);
    return resultBean;

  }
  
  @GetMapping(value = {"list/{keyword}/{currentPageOpt}"})
  public ResultBean<PageBean<Notice>> showNoticeBoardByKeyword(
      @PathVariable Optional<Integer> currentPageOpt, 
      @PathVariable String keyword) throws FindException {
    
    ResultBean<PageBean<Notice>> resultBean = new ResultBean<PageBean<Notice>>();
    int currentPage;
    if (currentPageOpt.isPresent()) {
      currentPage = currentPageOpt.get();
    } else {
      currentPage = 1;
    }

    PageBean<Notice> notice = noticeService.showNoticeBoardByKeyword(currentPage,keyword);
    logger.error("currentPage =" + currentPage); // 오 짱이다...
    if (currentPage > notice.getTotalPage()) {
      currentPage = notice.getTotalPage();
      resultBean.setStatus(0);
      resultBean.setMessage("공지사항 가져오는데 실패했습니다.");
      return resultBean;
    }
    resultBean.setStatus(1);
    resultBean.setMessage("공지사항 가져오는 성공했습니다.");
    resultBean.setT(notice);
    return resultBean;

  }
  
  @GetMapping(value = {"/{noticeNoOpt}"})
  public ResultBean<Notice> showNotice(@PathVariable Optional<Integer> noticeNoOpt)
      throws FindException {
    ResultBean<Notice> resultBean = new ResultBean<Notice>();
    int noticeNo;
    if (noticeNoOpt.isPresent()) {
      noticeNo = noticeNoOpt.get();
    } else {
      throw new FindException("공지사항이 없습니다");
    }
    Notice notice = noticeService.showNotice(noticeNo);
    resultBean.setStatus(1);
    resultBean.setMessage("공지사항 가져오는데 성공했습니다");
    resultBean.setT(notice);
    return resultBean;
  }
  // 공지사항 관련 END


}
