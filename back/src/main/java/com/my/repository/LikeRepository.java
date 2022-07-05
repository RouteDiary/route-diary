package com.my.repository;

public interface LikeRepository {
  void insertLike(int diaryNo, String clientId);

  void deleteLike(int diaryNo, String clientId);
}
