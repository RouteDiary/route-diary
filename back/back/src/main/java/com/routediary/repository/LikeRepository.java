package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Like;

@Repository
@Mapper
public interface LikeRepository {
  /**
   * DB에 있는 like 행을 찾아 반환함
   *
   * @param diaryNo
   * @param clientId
   * @return Like
   */
  public Like selectLike(@Param("diaryNo") int diaryNo, @Param("clientId") String clientId);

  /**
   * diaryNo에 해당하는 다이어리의 좋아요 수를 반환함
   *
   * @param diaryNo
   * @return int
   */
  public int selectCount(int diaryNo);

  /**
   * 좋아요 행을 추가한다.
   *
   * @param like
   */
  public void insert(Like like);

  /**
   * 좋아요 행을 삭제한다.
   *
   * @param like
   */
  public void delete(Like like);

  /**
   * 다이어리 번호를 찾아 해당하는 모든 좋아요 행을 삭제한다.
   *
   * @param diaryNo
   */
  public void deleteAll(int diaryNo);
}
