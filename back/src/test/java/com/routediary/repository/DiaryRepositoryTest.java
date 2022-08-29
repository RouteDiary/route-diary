package com.routediary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Diary;
import com.routediary.exception.SelectException;

@SpringBootTest
class DiaryRepositoryTest {
  @Autowired
  DiaryRepository diaryRepository;

  @Test
  void selectCountTest() throws SelectException {
    int expectedCount = 9;
    int count = diaryRepository.selectCount();
    assertEquals(expectedCount, count);
  }

  @Test
  void selectDiariesByClientIdTest() throws SelectException {
    String clientId = "koreaman@gmail.com";
    String expectedTitle = "즐거운 서울로 떠나요2";
    String expectedClientNickname = "한쿡";
    int expectedDiariesCount = 2;
    List<Diary> diaries = diaryRepository.selectDiariesByClientId(clientId, 1, 10);
    assertEquals(expectedTitle, diaries.get(0).getDiaryTitle());
    assertEquals(expectedClientNickname, diaries.get(0).getClient().getClientNickname());
    assertEquals(expectedDiariesCount, diaries.size());
    System.out.println(diaries.toString());
  }

  @Test
  void selectDiariesTest() throws SelectException {
    int order = 1; // diary_view_cnt
    String expectedTitle = "즐거운 광주으로 떠나요";
    String expectedClientNickname = "한국남자4";
    int expectedDiariesCount = 6;
    List<Diary> diaries = diaryRepository.selectDiaries(order, 1, 10);
    assertEquals(expectedTitle, diaries.get(0).getDiaryTitle());
    assertEquals(expectedClientNickname, diaries.get(0).getClient().getClientNickname());
    assertEquals(expectedDiariesCount, diaries.size());
    System.out.println(diaries.toString());
  }

  @Test
  void selectDiaryTest() throws SelectException {
    int diaryNo = 1;
    int expectedRoutesCount = 5;
    String expectedTitle = "즐거운 서울로 떠나요";
    String expectedClientNickname = "한쿡";
    Diary diary = diaryRepository.selectDiary(diaryNo);
    assertEquals(expectedTitle, diary.getDiaryTitle());
    assertEquals(expectedClientNickname, diary.getClient().getClientNickname());
    assertEquals(expectedRoutesCount, diary.getRoutes().size());
    System.out.println(diary.toString());
  }
}
