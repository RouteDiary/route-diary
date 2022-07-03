package com.routediary.service;

import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.exception.FindException;
import com.routediary.exception.NumberNotFoundException;


public interface NoticeService {

  /**
   * 공지사항 목록을 보여준다
   * 
   * @param currentPage
   * @return PageBean<Notice>
   * @throws FindException
   */
  public PageBean<Notice> showNoticeBoard(int currentPage) throws FindException;


  /**
   * 검색어를 입력하면 검색어와 일치하는 공지사항 목록이 보여진다 검색어가 입력되어 있지 않으면, 최신순대로 작성된 공지사항 목록이 차례대로 보여진다
   * 
   * @param currentPage, keyword
   * @return PageBean<Notice>
   * @throws FindException
   */
  public PageBean<Notice> showNoticeBoardByKeyword(int currentPage, String keyword)
      throws FindException;


  /**
   * 공지사항 상세보기(1개씩)를 보여준다
   * 
   * @return noticeNo
   * @throws FindException
   * @throws NumberNotFoundException
   */
  public Notice showNotice(int noticeNo) throws FindException, NumberNotFoundException;

}
