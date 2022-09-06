package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Hashtag;


@Repository
@Mapper
public interface HashtagRepository {
  /**
   * Hashtag를 넣는다
   *
   * @param hashtag
   */
  public void insert(Hashtag hashtag);

  /**
   * Hashtag를 업데이트한다
   *
   * @param hashtag
   */
  public void update(Hashtag hashtag);

  /**
   * 특정 Hashtag를 지운다
   *
   * @param hashtag
   */
  public void delete(Hashtag hashtag);

  /**
   * 해당 게시물의 Hashtag를 전부 지운다
   *
   * @param diaryNo
   */
  public void deleteAll(int diaryNo);

}
