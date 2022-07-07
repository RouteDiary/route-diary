package com.my.repository;

import java.util.List;
import com.my.dto.Diary;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;

public interface DiaryRepository {
  List<Diary> selectDirariesFromFirstRowByWritingDate(int diaryEndNo) throws SelectException;

  List<Diary> selectDirariesFromMiddleRowByWritingDate(int diaryStartNo, int diaryEndNo)
      throws SelectException;

  List<Diary> selectDirariesFromFirstRowByViewCnt(int diaryEndNo) throws SelectException;

  List<Diary> selectDirariesFromMiddleRowByViewCnt(int diaryStartNo, int diaryEndNo)
      throws SelectException;

  List<Diary> selectDirariesFromFirstRowByLikeCnt(int diaryEndNo) throws SelectException;

  List<Diary> selectDirariesFromMiddleRowByLikeCnt(int diaryStartNo, int diaryEndNo)
      throws SelectException;

  List<Diary> selectDirariesFromFirstRowById(String clientId, int diaryEndNo)
      throws SelectException;

  List<Diary> selectDirariesFromMiddleRowById(String clientId, int diaryStartNo, int diaryEndNo)
      throws SelectException;

  List<Diary> selectDirariesFromFirstRowByKeyword(String keyword, int diaryEndNo)
      throws SelectException;

  List<Diary> selectDirariesFromMiddleRowByKeyword(String keyword, int diaryStartNo, int diaryEndNo)
      throws SelectException;

  Diary selectDiraryByDiaryNo(int diaryNo) throws SelectException;

  int updateViewCnt(int diaryNo) throws UpdateException;

  void insert(Diary diary) throws InsertException;

  void update(Diary diary) throws UpdateException;
}
