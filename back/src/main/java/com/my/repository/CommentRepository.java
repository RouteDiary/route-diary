package com.my.repository;

import com.my.dto.Comment;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.exception.UpdateException;

public interface CommentRepository {
  void insert(Comment comment) throws InsertException;

  void update(Comment comment) throws UpdateException;

  void delete(Comment comment) throws DeleteException;
}
