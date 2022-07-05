package com.my.repository;

import com.my.dto.Comment;

public interface CommentRepository {
  void insert(Comment comment);

  void update(Comment comment);

  void delete(Comment comment);
}
