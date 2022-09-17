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
   * 공지사항 키워드에 해당하는 갯수를 반환.
   * 
   * @return int
   */
  public int selectCountByKeyword(@Param("keyword") String keyword);

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

  /**
   * 가장 최근의 noticeNo(공지사항번호) 를 반환 (SQL에서 공지사항번호용 시퀀스의 CURRVAL를 찾아 반환함).
   *
   * insert() method를 사용한 후, DB에 추가된 공지사항의 번호를 알아내는데 사용 가능
   *
   * @return int
   */
  public int selectLatestNoticeNo();
}
