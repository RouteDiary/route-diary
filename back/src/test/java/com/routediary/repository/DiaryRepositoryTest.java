package com.routediary.repository;

import static org.junit.Assert.assertNull;
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

    // hashtag를 이용한 검색
    List<String> hashtags = new ArrayList<String>();
    hashtags.add("여행");
    hashtags.add("양양");
    String expectedTitle = "즐거운 부산으로 떠나요";
    String expectedClientNickname = "한국남자6";
    int expectedDiariesCount = 6;
    List<Diary> diaries = diaryRepository.selectDiaries(order, 1, 10, hashtags);
    assertEquals(expectedTitle, diaries.get(0).getDiaryTitle());
    assertEquals(expectedClientNickname, diaries.get(0).getClient().getClientNickname());
    assertEquals(expectedDiariesCount, diaries.size());
    System.out.println(diaries.toString());

    // hashtag 검색을 안하는 경우
    String expectedTitle2 = "즐거운 광주으로 떠나요";
    String expectedClientNickname2 = "한국남자4";
    int expectedDiariesCount2 = 6;
    List<Diary> diaries2 = diaryRepository.selectDiaries(order, 1, 10, null);
    assertEquals(expectedTitle2, diaries2.get(0).getDiaryTitle());
    assertEquals(expectedClientNickname2, diaries2.get(0).getClient().getClientNickname());
    assertEquals(expectedDiariesCount2, diaries2.size());
    System.out.println(diaries2.toString());
  }

  @Test
  void selectDiaryTest() throws SelectException {
    int diaryNo = 1;
    int expectedRoutesCount = 5;
    int expectedCommentsCount = 3;
    int expectedHashtagsCount = 2;
    String expectedTitle = "즐거운 서울로 떠나요";
    String expectedClientNickname = "한쿡";
    String expectedFirstRouteContent = "경복궁갔다옴";
    String expectedFirstCommentContent = "좋은 여행정보 감사합니다~";
    String expectedFirstHashtag = "여행";
    Diary diary = diaryRepository.selectDiary(diaryNo);
    assertEquals(expectedRoutesCount, diary.getRoutes().size());
    assertEquals(expectedCommentsCount, diary.getComments().size());
    assertEquals(expectedHashtagsCount, diary.getHashtags().size());
    assertEquals(expectedTitle, diary.getDiaryTitle());
    assertEquals(expectedClientNickname, diary.getClient().getClientNickname());
    assertEquals(expectedFirstRouteContent, diary.getRoutes().get(0).getRouteContent());
    assertEquals(expectedFirstCommentContent, diary.getComments().get(0).getCommentContent());
    assertEquals(expectedFirstHashtag, diary.getHashtags().get(0).getHashtag());
    System.out.println(diary.toString());
  }

  @Test
  void insertTest() throws InsertException, SelectException {
    String clientId = "chinaman@gmail.com";
    String expectedClientNickname = "한국남자3";
    String diaryTitle = "new diary";
    Diary diary = new Diary();
    Client client = new Client();
    client.setClientId(clientId);
    diary.setDiaryTitle(diaryTitle);
    diary.setDiaryStartDate(new Date(2022, 1, 1));
    diary.setDiaryEndDate(new Date(2022, 1, 3));
    diary.setClient(client);

    diaryRepository.insert(diary);
    Diary diaryInDb = diaryRepository.selectDiary(10);
    assertEquals(diaryTitle, diaryInDb.getDiaryTitle());
    assertEquals(expectedClientNickname, diaryInDb.getClient().getClientNickname());
  }

  @Test
  void updateTest() throws UpdateException, SelectException { // 다이어리의 내용을 수정하기 위해 update()를 사용하는 경우
    String expectedClientId = "chinaman@gmail.com";
    String diaryTitle = "modified diary";
    Diary diary = new Diary();
    diary.setDiaryNo(10);
    diary.setDiaryTitle(diaryTitle);
    diary.setDiaryStartDate(new Date(2022, 1, 12));
    diary.setDiaryEndDate(new Date(2022, 1, 15));
    diary.setDiaryDisclosureFlag(1);
    diaryRepository.update(diary);

    Diary diaryInDb = diaryRepository.selectDiary(10);
    assertEquals(diaryTitle, diaryInDb.getDiaryTitle());
    assertEquals(expectedClientId, diaryInDb.getClient().getClientId());
  }

  @Test
  void updateViewCntTest() throws UpdateException, SelectException { // 다이어리의 조회수를 증가시키기 위해
                                                                     // update()를 사용하는 경우
    Diary diaryForViewCnt = new Diary();
    diaryForViewCnt.setDiaryNo(10);
    diaryForViewCnt.setDiaryViewCnt(-1);
    diaryRepository.update(diaryForViewCnt);

    Diary diaryInDb = diaryRepository.selectDiary(10);
    assertEquals(1, diaryInDb.getDiaryViewCnt());
  }

  @Test
  void deleteTest() throws DeleteException, SelectException {
    int diaryNo = 10;
    diaryRepository.delete(diaryNo);

    Diary diaryInDb = diaryRepository.selectDiary(10);
    assertNull(diaryInDb);
  }
}
