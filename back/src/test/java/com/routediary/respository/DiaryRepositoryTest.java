package com.routediary.respository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Client;
import com.routediary.dto.Diary;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;
import com.routediary.repository.DiaryRepository;

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
    String expectedTitle = "즐거운 서울로 떠나요";
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
    int order = 2; // diary_view_cnt
    List<String> hashtags = new ArrayList<String>();
    hashtags.add("여행");
    hashtags.add("양양");
    String expectedTitle = "즐거운 광주으로 떠나요";
    String expectedClientNickname = "한국남자4";
    int expectedDiariesCount = 6;
    List<Diary> diaries = diaryRepository.selectDiaries(order, 1, 10, null);
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

  @Test
  void insertTest() throws InsertException {
    Diary diary = new Diary();
    Client client = new Client();
    client.setClientId("chinaman@gmail.com");
    diary.setDiaryNo(1);
    diary.setDiaryTitle("new diary");
    diary.setDiaryStartDate(new Date(2022, 1, 1));
    diary.setDiaryEndDate(new Date(2022, 1, 3));
    diary.setClient(client);

    diaryRepository.insert(diary);
  }

  @Test
  void updateTest() throws UpdateException { // 다이어리의 내용을 수정하기 위해 update()를 사용하는 경우
    Diary diary = new Diary();
    diary.setDiaryNo(1);
    diary.setDiaryTitle("modified title3");
    diary.setDiaryStartDate(new Date(2022, 1, 12));
    diary.setDiaryEndDate(new Date(2022, 1, 15));
    diary.setDiaryDisclosureFlag(1);
    diaryRepository.update(diary);
  }

  @Test
  void updateViewCntTest() throws UpdateException { // 다이어리의 조회수를 증가시키기 위해 update()를 사용하는 경우
    Diary diaryForViewCnt = new Diary();
    diaryForViewCnt.setDiaryNo(24);
    diaryForViewCnt.setDiaryViewCnt(-1);
    diaryRepository.update(diaryForViewCnt);
  }

  @Test
  void deleteTest() throws DeleteException {
    int diaryNo = 25;
    diaryRepository.delete(diaryNo);
  }
}
