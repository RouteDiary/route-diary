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
   * @throws InsertException
   */
  public void insert(Hashtag hashtag);

  /**
   * Hashtag를 업데이트한다
   * 
   * @param hashtag
   * @throws UpdateException
   */
  public void update(Hashtag hashtag);

  /**
   * 특정 Hashtag를 지운다
   * 
   * @param hashtag
   * @throws DeleteException
   */
  public void delete(Hashtag hashtag);

  /**
   * 해당 게시물의 Hashtag를 전부 지운다
   * 
   * @param diaryNo
   * @throws DeleteException
   */
  public void deleteAll(int diaryNo);

}
