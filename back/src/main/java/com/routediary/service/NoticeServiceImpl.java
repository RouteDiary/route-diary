package com.routediary.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.enums.ErrorCode;
import com.routediary.exception.FindException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.functions.ServiceFunctions;
import com.routediary.repository.NoticeRepository;

@Service(value = "NoticeService")
public class NoticeServiceImpl implements NoticeService {
  @Autowired
  private ServiceFunctions serviceFunctions;
  @Autowired
  NoticeRepository noticeRepository;

  @Override
  public PageBean<Notice> showNoticeBoard(int currentPage) throws FindException {
    List<Notice> notices = null;
    int totalRows = noticeRepository.selectCount();
    int[] rowArr = serviceFunctions.calculateStartAndEndRow(currentPage, totalRows);
    int startRow = rowArr[0];
    int endRow = rowArr[1];

    notices = noticeRepository.selectNotices(startRow, endRow, null);
    PageBean<Notice> pageBean = (PageBean<Notice>) serviceFunctions.calculatePageBean(currentPage,
        totalRows, (List<?>) notices);
    return pageBean;
  }

  @Override
  public PageBean<Notice> showNoticeBoardByKeyword(int currentPage, String keyword)
      throws FindException {
    List<Notice> notices = null;
    int totalRows = noticeRepository.selectCountByKeyword(keyword);
    System.out.println("totalRows:" + totalRows);
    int[] rowArr = serviceFunctions.calculateStartAndEndRow(currentPage, totalRows);
    int startRow = rowArr[0];
    int endRow = rowArr[1];
    notices = noticeRepository.selectNotices(startRow, endRow, keyword);
    PageBean<Notice> pageBean = (PageBean<Notice>) serviceFunctions.calculatePageBean(currentPage,
        totalRows, (List<?>) notices);
    return pageBean;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Notice showNotice(int noticeNo) throws FindException, NumberNotFoundException {
    // 조회수 증가하기
    noticeRepository.updateViewCnt(noticeNo);
    Notice notice = noticeRepository.selectNotice(noticeNo);
    if (notice == null) {
      throw new NumberNotFoundException(ErrorCode.POST_NOT_FOUND);
    }
    return notice;
  }
}
