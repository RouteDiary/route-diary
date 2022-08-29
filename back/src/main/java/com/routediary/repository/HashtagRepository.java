package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Hashtag;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.UpdateException;


@Repository
@Mapper
public interface HashtagRepository {
  /**
   * Hashtag를 넣는다
   * 
   * @param hashtag
   * @throws InsertException
   */
  void insert(Hashtag hashtag) throws InsertException;

  /**
   * Hashtag를 업데이트한다
   * 
   * @param hashtag
   * @throws UpdateException
   */
  void update(Hashtag hashtag) throws UpdateException;

  /**
   * 특정 Hashtag를 지운다
   * 
   * @param hashtag
   * @throws DeleteException
   */
  void delete(Hashtag hashtag) throws DeleteException;

  /**
   * 해당 게시물의 Hashtag를 전부 지운다
   * 
   * @param diaryNo
   * @throws DeleteException
   */
  void deleteAll(int diaryNo) throws DeleteException;

}
