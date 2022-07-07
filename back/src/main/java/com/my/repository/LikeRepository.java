package com.my.repository;

import com.my.exception.DeleteException;
import com.my.exception.InsertException;

public interface LikeRepository {
  void insertLike(int diaryNo, String clientId) throws InsertException;

  void deleteLike(int diaryNo, String clientId) throws DeleteException;
}
