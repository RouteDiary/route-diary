package com.routediary.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Diary;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;

@Repository
@Mapper
public interface DiaryRepository {
  public int selectCount() throws SelectException;

  public List<Diary> selectDiariesByClientId(@Param("clientId") String clientId,
      @Param("startRow") int startRow, @Param("endRow") int endRow) throws SelectException;

  public List<Diary> selectDiaries(@Param("hsahtags") List<String> hashtags, int order,
      @Param("startRow") int startRow, @Param("endRow") int endRow) throws SelectException;

  /**
   * order parameter -> 1 = diary_view_cnt의 DESC 순 2 = diary_writing_time의 DESC 순 3 =
   * diary_like_cnt의 DESC 순
   * 
   * @param order
   * @param startRow
   * @param endRow
   * @return
   * @throws SelectException
   */
  public List<Diary> selectDiaries(@Param("order") int order, @Param("startRow") int startRow,
      @Param("endRow") int endRow) throws SelectException;

  public Diary selectDiary(int diaryNo) throws SelectException;

  public void insert(Diary diary) throws InsertException;

  public void update(Diary diary) throws UpdateException;

  public void delete(int diaryNo) throws DeleteException;


}
