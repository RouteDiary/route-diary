package com.my.repository;

import java.util.List;
import com.my.dto.Diary;

public interface DiaryRepository {
  List<Diary> selectDirariesByWritingDate();

  List<Diary> selectDirariesByViewCnt();

  List<Diary> selectDirariesByLikeCnt();

  List<Diary> selectDirariesById(String clientId);

  List<Diary> selectDirariesByKeyword(String keyword);

  Diary selectDiraryByDiaryNo(int diaryNo);

  int updateViewCnt(int diaryNo);

  void insert(Diary diary);

  void update(Diary diary);
}
