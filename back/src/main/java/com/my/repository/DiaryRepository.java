package com.my.repository;

import java.util.List;
import com.my.dto.Diary;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;

public interface DiaryRepository {

  int selectDiariesRowSize(int diaryDisclosureFlag) throws SelectException;

  List<Diary> selectDiariesByWritingDate(int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException;

  List<Diary> selectDiariesByViewCnt(int diaryStartRowNo, int diaryEndRowNo) throws SelectException;

  List<Diary> selectDiariesByLikeCnt(int diaryStartRowNo, int diaryEndRowNo) throws SelectException;

  List<Diary> selectDiariesById(String clientId, int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException;

  List<Diary> selectDiariesByKeyword(String keyword, int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException;

  Diary selectDiaryByDiaryNo(int diaryNo) throws SelectException;

  void updateViewCnt(Diary diary) throws UpdateException;

  void insert(Diary diary) throws InsertException;

  void update(Diary diary) throws UpdateException;
}
