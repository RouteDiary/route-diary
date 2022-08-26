<<<<<<< Updated upstream
package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Comment;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.UpdateException;

@Repository
@Mapper
public interface CommentRepository {
  /**
   * 댓글을 작성한다
   * 
   * @param comment
   * @throws InsertException
   */
  public void insert(Comment comment) throws InsertException;

  /**
   * 댓글을 수정한다
   * 
   * @param comment
   * @throws UpdateException
   */
  public void update(Comment comment) throws UpdateException;

  /**
   * 댓글을 삭제한다, 다이어리를 지웠을 때 댓글까지 전체 삭제한다
   * 
   * @param comment
   * @throws DeleteException
   */
  public void delete(Comment comment) throws DeleteException;
}
=======
package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.routediary.dto.Client;
import com.routediary.dto.Comment;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.UpdateException;

@Repository
@Mapper
public interface CommentRepository {
  /**
   * 댓글을 작성한다
   * 
   * @param comment
   * @throws InsertException
   */
  void insert(Comment comment) throws InsertException;

  /**
   * 댓글을 수정한다
   * 
   * @param comment
   * @throws UpdateException
   */
  public void update(Comment comment) throws UpdateException;

  /**
   * 댓글을 삭제한다, 다이어리를 지웠을 때 댓글까지 전체 삭제한다
   * 
   * @param comment
   * @throws DeleteException
   */
  public void delete(Comment comment) throws DeleteException;
}
>>>>>>> Stashed changes
