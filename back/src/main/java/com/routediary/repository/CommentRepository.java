package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Comment;

@Repository
@Mapper
public interface CommentRepository {
  /**
   * 댓글을 작성한다
   *
   * @param comment
   * @throws InsertException
   */
  public void insert(Comment comment);

  /**
   * 댓글을 수정한다
   *
   * @param comment
   * @throws UpdateException
   */
  public void update(Comment comment);

  /**
   * 댓글을 삭제한다
   *
   * @param diaryNo
   * @param commentNo
   * @throws DeleteException
   */
  public void delete(int diaryNo, int commentNo);

  /**
   * 다이어리 diaryNo의 모든 행을 삭제한다
   *
   * @param diaryNo
   * @throws DeleteException
   */
  public void deleteAll(int diaryNo);
}
