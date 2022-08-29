package com.routediary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.repository.NoticeRepository;

@Service
public class TestServiceImpl implements TestService {

  @Autowired
  NoticeRepository noticeRepository;

  /**
   * 설마 여기다가도 주석다는건 아니겠죠? 안달아도 되요
   */
  @Override
  public PageBean<Notice> showNoticeBoard() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PageBean<Notice> showNoticeBoardByKeyword() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Notice showNotice() {
    // TODO Auto-generated method stub
    return null;
  }


}
