package com.routediary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Client;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Hashtag;
import com.routediary.dto.PageBean;
import com.routediary.dto.Route;
import com.routediary.exception.FindException;
import com.routediary.repository.CommentRepository;
import com.routediary.repository.DiaryRepository;
import com.routediary.repository.HashtagRepository;
import com.routediary.repository.LikeRepository;
import com.routediary.repository.RouteRepository;

@SpringBootTest
class DiaryServiceTest {
  @Autowired
  DiaryService diarySerivce;

  @Autowired
  DiaryRepository diaryRepository;

  @Autowired
  RouteRepository routeRepository;

  @Autowired
  HashtagRepository hashtagRepository;

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  LikeRepository likeRepository;

  @Test
  void writeDiaryTest() throws Exception {
    Diary diary = new Diary();
    Client client = new Client();
    client.setClientId("koreaman@gmail.com");
    diary.setDiaryNo(1);
    diary.setDiaryTitle("diary new");
    diary.setDiaryStartDate(new Date(22, 2, 2));
    diary.setDiaryEndDate(new Date(22, 2, 4));
    diary.setDiaryDisclosureFlag(1);
    diary.setClient(client);
    List<Route> routes = new ArrayList<Route>();
    for (int i = 0; i < 8; i++) {
      Route route = new Route();
      route.setDiaryNo(27);
      route.setRouteContent("content" + (i + 1));
      route.setKakaoMapId("1");
      routes.add(route);
    }
    diary.setRoutes(routes);

    diarySerivce.writeDiary(diary, null);
  }


  @Test
  void removeDiaryTest() throws Exception {

    // 성공하는 경우의 수
    // int diaryNo = 1;
    // diarySerivce.removeDiary(diaryNo);
    //
    // Diary diary = diarySerivce.showDiary(diaryNo);
    // List<Route> routes = diary.getRoutes();
    // List<Hashtag> hashtags = diary.getHashtags();
    // List<Comment> comments = diary.getComments();
    //
    // assertNull(diary);
    // assertNull(routes);
    // assertNull(hashtags);
    // assertNull(comments);

    // 중간에 삭제 실패하는 경우의 수 (diary mapper의 sql구문에 일부러 오타낸 후 실행해볼것)
    int diaryNo = 2;
    diarySerivce.removeDiary(diaryNo);

    Diary diary = diarySerivce.showDiary(diaryNo);
    List<Route> routes = diary.getRoutes();
    List<Hashtag> hashtags = diary.getHashtags();
    List<Comment> comments = diary.getComments();

    assertNull(diary);
    assertNull(routes);
    assertNull(hashtags);
    assertNull(comments);
  }

  @Test
  void showDiaryBoardTest() throws Exception {
    PageBean<Diary> pageBean = diarySerivce.showDiaryBoard(1, 20, null);
    int expectedDiariesCount = 6;
    int expectedTotalPage = 20;
    int expectedStartPage = 11;
    int expectedEndPage = 20;
    assertEquals(expectedDiariesCount, pageBean.getPosts().size());
    assertEquals(expectedTotalPage, pageBean.getTotalPage());
    assertEquals(expectedStartPage, pageBean.getStartPage());
    assertEquals(expectedEndPage, pageBean.getEndPage());
  }

  @Test
  void showDiaryTest() throws Exception {
    int diaryNo = 1;
    Diary d = diarySerivce.showDiary(diaryNo);
    d.setDiaryNo(diaryNo);
    String expectedTitle = "즐거운 서울로 떠나요";

    Date expectedWritingTime; // = new Date();
    Calendar c1 = Calendar.getInstance();
    c1.set(Calendar.YEAR, 2022);
    c1.set(Calendar.MONTH, Calendar.JULY);
    c1.set(Calendar.DATE, 22);
    c1.set(Calendar.HOUR, 10);
    c1.set(Calendar.MINUTE, 20);
    c1.set(Calendar.SECOND, 11);
    expectedWritingTime = c1.getTime();

    Date expectedModifyingTime;
    Calendar c2 = Calendar.getInstance();
    c2.set(Calendar.YEAR, 2022);
    c2.set(Calendar.MONTH, Calendar.JULY);
    c2.set(Calendar.DATE, 23);
    c2.set(Calendar.HOUR, 10);
    c2.set(Calendar.MINUTE, 20);
    c2.set(Calendar.SECOND, 11);
    expectedModifyingTime = c2.getTime();

    Date expectedStartDate;
    Calendar c3 = Calendar.getInstance();
    c3.set(Calendar.YEAR, 2020);
    c3.set(Calendar.MONTH, Calendar.JULY);
    c3.set(Calendar.DATE, 10);
    c3.set(Calendar.HOUR, 00);
    c3.set(Calendar.MINUTE, 00);
    c3.set(Calendar.SECOND, 00);
    expectedStartDate = c3.getTime();

    Date expectedEndDate;
    Calendar c4 = Calendar.getInstance();
    c4.set(Calendar.YEAR, 2020);
    c4.set(Calendar.MONTH, Calendar.JULY);
    c4.set(Calendar.DATE, 12);
    c4.set(Calendar.HOUR, 00);
    c4.set(Calendar.MINUTE, 00);
    c4.set(Calendar.SECOND, 00);
    expectedEndDate = c4.getTime();

    Integer expectedDisclosureFlag = 1;
    int expectedViewCnt = 1578;
    int expectedLikeCnt = 0;
    Client client = new Client();
    String expectedId = "koreaman@gmail.com";
    // client.setClientId("koreaman@gmail.com");
    List<Route> expectedRoutes = d.getRoutes();
    List<Comment> expectedComments = d.getComments();
    List<Hashtag> expectedHashtags = d.getHashtags();

    assertEquals(expectedTitle, d.getDiaryTitle());
    assertEquals(expectedWritingTime.toString(), d.getDiaryWritingTime().toString());
    assertEquals(expectedModifyingTime.toString(), d.getDiaryModifyingTime().toString());
    assertEquals(expectedStartDate.toString(), d.getDiaryStartDate().toString());
    assertEquals(expectedEndDate.toString(), d.getDiaryEndDate().toString());
    assertEquals(expectedDisclosureFlag, d.getDiaryDisclosureFlag());
    assertEquals(expectedViewCnt, d.getDiaryViewCnt());
    assertEquals(expectedLikeCnt, d.getDiaryLikeCnt());
    assertEquals(expectedId, d.getClient().getClientId());
    assertEquals(expectedRoutes.toString(), d.getRoutes().toString());
    assertEquals(expectedComments.toString(), d.getComments().toString());
    assertEquals(expectedHashtags.toString(), d.getHashtags().toString());
    System.out.println(d.toString());

  }

  @Test
  void showDiaryTest1() { // 다이어리 번호가 없는 다이어리를 불러왔을 때 Exception 발생 Test
    int diaryNo = 11235;
    assertThrows(FindException.class, () -> {
      diarySerivce.showDiary(diaryNo);
    });
  }

  @Test
  void writeCommentTest() throws Exception {
    Client client = new Client();
    Comment comment = new Comment();
    client.setClientId("japanwoman@gmail.com");
    comment.setDiaryNo(1);
    comment.setClient(client);
    comment.setCommentContent("유익해요");
    diarySerivce.writeComment(comment);

  }
}
