package com.routediary.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Notice;

@Repository
@Mapper
public interface NoticeRepository {
  /**
   * 공지사항 전체 갯수를 반환
   *
   * @return int
   */
  public int selectCount();

  /**
   * 공지사항을 추가한다.
   *
   * @param notice
   */
  public void insert(Notice notice);

  /**
   * 공지사항을 수정한다.
   *
   * @param notice
   */
  public void update(Notice notice);

  /**
   * 공지사항을 삭제한다.
   *
   * @param noticeNo
   */
  public void delete(int noticeNo);

  /**
   * Notice(공지사항) 객체 1개를 반환
   *
   * @param noticeNo
   * @return Notice
   */
  public Notice selectNotice(int noticeNo);

  /**
   * keyword parameter가 null이 아닌 경우, 검색어에 해당되는 Notice(공지사항) 객체들이 반환. null인 경우, 검색어와 상관없이
   * Notice(공지사항) 객체들이 반환
   *
   * @param startRow
   * @param endRow
   * @param keyword
   * @return List<Notice>
   */
  public List<Notice> selectNotices(@Param("startRow") int startRow, @Param("endRow") int endRow,
      @Param("keyword") @Nullable String keyword);

  /**
   * 공지사항의 조회수(notice_view_cnt)를 증가함
   *
   * @param noticeNo
   */
  public void updateViewCnt(int NoticeNo);
}
