package com.my.repository;

import java.util.List;
import com.my.dto.Diary;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;

public interface DiaryRepository {

  List<Diary> selectDirariesByWritingDate(int diaryStartNo, int diaryEndNo) throws SelectException;

  List<Diary> selectDirariesByViewCnt(int diaryStartNo, int diaryEndNo) throws SelectException;

  List<Diary> selectDirariesByLikeCnt(int diaryStartNo, int diaryEndNo) throws SelectException;

  List<Diary> selectDirariesById(String clientId, int diaryStartNo, int diaryEndNo)
      throws SelectException;

  List<Diary> selectDirariesByKeyword(String keyword, int diaryStartNo, int diaryEndNo)
      throws SelectException;

  Diary selectDiraryByDiaryNo(int diaryNo) throws SelectException;

  void updateViewCnt(Diary diary) throws UpdateException;

  void insert(Diary diary) throws InsertException;

  void update(Diary diary) throws UpdateException;
}
