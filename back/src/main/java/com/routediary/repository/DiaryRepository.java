package com.routediary.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Diary;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;

@Repository
@Mapper
public interface DiaryRepository {
  /**
   * 현재 DB에 저장되있는 전체 diary의 개수를 반환
   * 
   * @return int
   * @throws SelectException
   */
  public int selectCount() throws SelectException;

  /**
   * 회원의 ID(clientId)값과 일치하는 다이어리들을 반환
   * 
   * @param clientId
   * @param startRow
   * @param endRow
   * @return List<Diary>
   * @throws SelectException
   */
  public List<Diary> selectDiariesByClientId(@Param("clientId") String clientId,
      @Param("startRow") int startRow, @Param("endRow") int endRow) throws SelectException;

  /**
   * 입력한 해시태그를 가진 다이어리들을 반환. order parameter : 1 = diary_view_cnt의 DESC 순 2 = diary_writing_time의
   * DESC 순 3 = diary_like_cnt의 DESC 순 으로 정렬. hashtags parameter의 값이 null인 경우 : 해시태그검색이 아닌 일반 검색,
   * null이 아닌 경우 : 해시태그를 통한 검색
   * 
   * @param order
   * @param startRow
   * @param endRow
   * @param hashtags
   * @return List<Diary>
   * @throws SelectException
   */
  public List<Diary> selectDiaries(@Param("order") int order, @Param("startRow") int startRow,
      @Param("endRow") int endRow, @Param("hashtags") @Nullable List<String> hashtags)
      throws SelectException;

  /**
   * diaryNo에 해당하는 다이어리를 반환
   * 
   * @param diaryNo
   * @return Diary
   * @throws SelectException
   */
  public Diary selectDiary(int diaryNo) throws SelectException;

  /**
   * 작성한 다이어리를 DB에 추가
   * 
   * @param diary
   * @throws InsertException
   */
  public void insert(Diary diary) throws InsertException;

  /**
   * 다이어리의 내용(diary_title, diary_start_date, diary_end_date, diary_disclosure_flag)을 수정하거나
   * 조회수(diary_view_cnt)를 증가시킬때 사용
   * 
   * @param diary
   * @throws UpdateException
   */
  public void update(Diary diary) throws UpdateException;

  /**
   * 다이어리를 삭제
   * 
   * @param diaryNo
   * @throws DeleteException
   */
  public void delete(int diaryNo) throws DeleteException;


}
