package com.routediary.service;

import org.springframework.stereotype.Service;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;

@Service
public interface TestService {

  /**
   * 주석 꼭 달아요
   * 
   * @return
   */
  public PageBean<Notice> showNoticeBoard();

  public PageBean<Notice> showNoticeBoardByKeyword();

  public Notice showNotice();

}
