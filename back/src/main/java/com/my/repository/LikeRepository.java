package com.my.repository;

import com.my.dto.Like;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;

public interface LikeRepository {
  void insertLike(Like like) throws InsertException;

  void deleteLike(Like like) throws DeleteException;
}
