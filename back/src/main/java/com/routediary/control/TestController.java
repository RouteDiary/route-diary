package com.routediary.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.service.TestService;

@RestController
@RequestMapping("notice/*")
public class TestController {

  @Autowired
  private TestService testService;

  @GetMapping(value = {"list/{pageNo}"})
  public ResultBean<PageBean<Notice>> showNoticeBoard(@PathVariable int pageNo) {
    return null;
  }

  @GetMapping(value = {"list/{keyword}/{pageNo}"})
  public ResultBean<PageBean<Notice>> showNoticeBoardByKeyword(@PathVariable String keyword,
      @PathVariable int pageNo) {
    return null;
  }

  @GetMapping(value = {"{noticeNo}"})
  public ResultBean<Notice> showNotice(@PathVariable Long noticeNo) {
    return null;
  }
}
