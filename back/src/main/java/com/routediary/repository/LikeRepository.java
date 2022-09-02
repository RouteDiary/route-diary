package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Like;

@Repository
@Mapper
public interface LikeRepository {
  /**
   * diaryNo에 해당하는 다이어리의 좋아요 수를 반환함
   * 
   * @param diaryNo
   * @return int
   * @throws SelectException
   */
  public int selectCount(int diaryNo);

  /**
   * 좋아요 행을 추가한다.
   * 
   * @param like
   * @throws InsertException
   */
  public void insert(Like like);

  /**
   * 좋아요 행을 삭제한다.
   * 
   * @param like
   * @throws DeleteException
   */
  public void delete(Like like);

  /**
   * 다이어리 번호를 찾아 해당하는 모든 좋아요 행을 삭제한다.
   * 
   * @param diaryNo
   * @throws DeleteException
   */
  public void deleteAll(int diaryNo);
}
