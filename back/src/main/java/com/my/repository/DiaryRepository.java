package com.my.repository;

import java.util.List;
import com.my.dto.Diary;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;

public interface DiaryRepository {
  List<Diary> selectDirariesByWritingDate() throws SelectException;

  List<Diary> selectDirariesByViewCnt() throws SelectException;

  List<Diary> selectDirariesByLikeCnt() throws SelectException;

  List<Diary> selectDirariesById(String clientId) throws SelectException;

  List<Diary> selectDirariesByKeyword(String keyword) throws SelectException;

  Diary selectDiraryByDiaryNo(int diaryNo) throws SelectException;

  int updateViewCnt(int diaryNo) throws UpdateException;

  void insert(Diary diary) throws InsertException;

  void update(Diary diary) throws UpdateException;
}
