package com.routediary.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.exception.FindException;
import com.routediary.repository.NoticeRepository;

@Service(value = "NoticeService")
public class NoticeServiceImpl implements NoticeService {
  private static final int CNT_PER_PAGE = 10;
  private static final int CNT_PER_PAGE_GROUP = 10;

  @Autowired
  NoticeRepository noticeRepository;


  @Override
  public PageBean<Notice> showNoticeBoard(int currentPage) throws FindException {
    PageBean<Notice> pageBean = null;
    int endRow = currentPage * CNT_PER_PAGE; // 10 20
    int startRow = endRow - CNT_PER_PAGE + 1; // 1 11
    String keyword = null;
    try {
      long totalRows = noticeRepository.selectCount(); // 총개수
      List<Notice> notices = noticeRepository.selectNotices(startRow, endRow, keyword);
      pageBean =
          new PageBean<Notice>(notices, totalRows, currentPage, CNT_PER_PAGE, CNT_PER_PAGE_GROUP);
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("가져올 공지사항이 없습니다"+e.getMessage());
    }
    return pageBean;

  }

  @Override
  public PageBean<Notice> showNoticeBoardByKeyword(int currentPage, String keyword)
      throws FindException {

    PageBean<Notice> pageBean = null;
    int endRow = currentPage * CNT_PER_PAGE; // 10 20
    int startRow = endRow - CNT_PER_PAGE + 1; // 1 11
    try {
      long totalRows = noticeRepository.selectCount(); // 총개수
      List<Notice> notices = noticeRepository.selectNotices(startRow, endRow, keyword);
      pageBean =
          new PageBean<Notice>(notices, totalRows, currentPage, CNT_PER_PAGE, CNT_PER_PAGE_GROUP);
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("해당"+keyword+"에 해당하는 공지사항이 없습니다"+e.getMessage());
    }
    return pageBean;

  }

  @Override
  public Notice showNotice(int noticeNo) throws FindException {
    // 조회수 증가하기
    try {
      Optional<Notice> noticeViewCntOpt =
          Optional.ofNullable(noticeRepository.selectNotice(noticeNo));
      if (noticeViewCntOpt.isPresent()) {
        Notice NoticeBefore = noticeViewCntOpt.get();

        // System.out.println(NoticeBefore);
        NoticeBefore.setNoticeViewCnt(NoticeBefore.getNoticeViewCnt() + 1);
        noticeRepository.updateViewCnt(noticeNo);
      } else {
        throw new FindException("게시글이 없습니다");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 게시글 한개 불러오기
    Optional<Notice> notice = Optional.empty();
    try {
      notice = Optional.ofNullable(noticeRepository.selectNotice(noticeNo));
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("게시글이 없습니다"+e.getMessage());
    }
    if (notice.isPresent()) {
      return notice.get();
    } else {
      throw new FindException("게시글이 없습니다");
    }
  }

}
