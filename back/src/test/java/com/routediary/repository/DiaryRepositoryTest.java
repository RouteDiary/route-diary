package com.routediary.repository;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.routediary.dto.Client;
import com.routediary.dto.Diary;

@SpringBootTest
class DiaryRepositoryTest {
  @Autowired
  DiaryRepository diaryRepository;

  @Test
  void selectCountByDisclosureFlagTest() throws Exception {
    int expectedCount = 198;
    int count = diaryRepository.selectCountByDisclosureFlag(1);
    assertEquals(expectedCount, count);
  }

  @Test
  void selectCountByClientIdTest() throws Exception {
    int expectedCount = 65;
    int count = diaryRepository.selectCountByClientId("koreaman@gmail.com");
    assertEquals(expectedCount, count);
  }

  @Test
  void selectDiariesByClientIdTest() throws Exception {
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
  void selectDiariesTest() throws Exception {
    int order = 2; // diary_view_cnt

    // hashtag를 이용한 검색
    List<String> hashtags = new ArrayList<String>();
    hashtags.add("여행");
    hashtags.add("양양");
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("hashtags", hashtags);
    System.out.println(mapper.writeValueAsString(map));

    String expectedTitle = "즐거운 부산으로 떠나요";
    String expectedClientNickname = "한국남자6";
    int expectedDiariesCount = 6;
    List<Diary> diaries = diaryRepository.selectDiaries(order, 1, 30, null);
    System.out.println(diaries.toString());
    assertEquals(expectedTitle, diaries.get(0).getDiaryTitle());
    assertEquals(expectedClientNickname, diaries.get(0).getClient().getClientNickname());
    assertEquals(expectedDiariesCount, diaries.size());
    System.out.println(mapper.writeValueAsString(map));
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
  void selectDiaryTest() throws Exception {
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
    System.out.println(diary.toString());
    System.out.println(diary.getRoutes().get(1).toString());
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("diary", diary);
    System.out.println(mapper.writeValueAsString(map));
    assertEquals(expectedRoutesCount, diary.getRoutes().size());
    assertEquals(expectedCommentsCount, diary.getComments().size());
    assertEquals(expectedHashtagsCount, diary.getHashtags().size());
    assertEquals(expectedTitle, diary.getDiaryTitle());
    assertEquals(expectedClientNickname, diary.getClient().getClientNickname());
    assertEquals(expectedFirstRouteContent, diary.getRoutes().get(0).getRouteContent());
    assertEquals(expectedFirstCommentContent, diary.getComments().get(0).getCommentContent());
    assertEquals(expectedFirstHashtag, diary.getHashtags().get(0).getHashtag());

  }

  @Test
  void insertTest() throws Exception {
    String clientId = "chinaman@gmail.com";
    String expectedClientNickname = "한국남자3";
    String diaryTitle = "new diary";
    int diaryDisclosureFlag = 1;
    Diary diary = new Diary();
    Client client = new Client();
    client.setClientId(clientId);
    diary.setDiaryTitle(diaryTitle);
    diary.setDiaryStartDate(new Date(2022, 1, 1));
    diary.setDiaryEndDate(new Date(2022, 1, 3));
    diary.setDiaryDisclosureFlag(diaryDisclosureFlag);
    diary.setClient(client);

    diaryRepository.insert(diary);
    Diary diaryInDb = diaryRepository.selectDiary(14);
    assertEquals(diaryTitle, diaryInDb.getDiaryTitle());
    assertEquals(expectedClientNickname, diaryInDb.getClient().getClientNickname());
  }

  @Test
  void selectLatestDiaryNoTest() throws Exception {
    int diaryNo = diaryRepository.selectLatestDiaryNo();
    System.out.println("latest diaryNo : " + diaryNo);
    assertNotEquals(0, diaryNo);
  }

  @Test
  void updateTest() throws Exception {
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
  void updateViewCntTest() throws Exception {

    diaryRepository.updateViewCnt(1);
    Diary diaryInDb = diaryRepository.selectDiary(1);
    assertEquals(14, diaryInDb.getDiaryViewCnt());
  }

  @Test
  void updateLikeCntTest() throws Exception {

    diaryRepository.updateIncreaseLikeCnt(1);
    Diary diaryInDb = diaryRepository.selectDiary(1);
    assertEquals(3, diaryInDb.getDiaryLikeCnt());

    diaryRepository.updateDecreaseLikeCnt(1);
    Diary diaryInDb2 = diaryRepository.selectDiary(1);
    assertEquals(2, diaryInDb2.getDiaryLikeCnt());
  }

  @Test
  void deleteTest() throws Exception {
    int diaryNo = 10;
    diaryRepository.delete(diaryNo);

    Diary diaryInDb = diaryRepository.selectDiary(10);
    assertNull(diaryInDb);
  }
}
