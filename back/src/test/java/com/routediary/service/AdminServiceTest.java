package com.routediary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Admin;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Hashtag;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.Route;
import com.routediary.repository.AdminRepository;
import com.routediary.repository.DiaryRepository;
import com.routediary.repository.NoticeRepository;

@SpringBootTest
class AdminServiceTest {

  @Autowired
  AdminRepository adminRepository;
  @Autowired
  NoticeRepository noticeRepository;
  @Autowired
  DiaryRepository diaryRepository;
  @Autowired
  AdminServiceImpl adminService;

  @Test
  void TestAdminLogin() throws Exception {
    // 성공~~
    String expectedId = "msk@kosta.com";
    String expectedPwd = "1111";
    Admin admin = adminService.login(expectedId, expectedPwd);
    assertEquals(expectedId, admin.getAdminId());
    assertEquals(expectedPwd, admin.getAdminPwd());

    // 실패 : FindException 없는 아이디
    // String expectedId = "msk없는아이디@kosta.com";
    // String expectedPwd = "1111";
    // Admin admin = adminService.login(expectedId, expectedPwd);
    // assertEquals(expectedId,admin.getAdminId());
    // assertEquals(expectedPwd,admin.getAdminPwd());

    // 실패 : FindException 없는 비밀번호
    // String expectedId = "msk@kosta.com";
    // String expectedPwd = "11112";
    // Admin admin = adminService.login(expectedId, expectedPwd);
    // assertEquals(expectedId,admin.getAdminId());
    // assertEquals(expectedPwd,admin.getAdminPwd());

  }

  @Test
  void TestRemoveDiary() throws Exception {
    int expectedDiaryNo = 2;

    adminService.removeDiary(expectedDiaryNo);
    Diary diary = diaryRepository.selectDiary(expectedDiaryNo);
    assertNull(diary);
  }

  @Test
  void TestRemoveNotice() throws Exception {
    int expectedNoticeNo = 1;
    adminService.removeNotice(expectedNoticeNo);
    Notice notice = noticeRepository.selectNotice(expectedNoticeNo);
    assertNull(notice);
  }

  @Test
  void TestRemoveComment() throws Exception {
    int expectedDiaryNo = 3;
    int expectedCommentNo = 1;

    adminService.removeComment(expectedDiaryNo, expectedCommentNo);

  }

  @Test
  void TestWriteNotice() throws Exception {
    Notice notice = new Notice();

    String expectedAdminId = "msk@kosta.com";
    String expectedNoticeTitle = "긴급긴급 강민주 만두 먹는다.";
    String expectedNoticeContent = "제목그대로임 ㅅㄱ";
    notice.setAdminId(expectedAdminId);
    notice.setNoticeContent(expectedNoticeTitle);
    notice.setNoticeTitle(expectedNoticeContent);
    // adminService.writeNotice(notice);

    assertNotNull(notice);
  }

  @Test
  void TestshowDiaryBoard() throws Exception {
    int expectedOrder = 2;// 최신순
    int expectedCurrentPage = 1;
    int expectedSize = 10;

    int expectedcurrentPage = 1;
    int expectedcurrentPage1 = 11;

    int expectedtotalPage = 2;
    int expectedtotalRows = 209;
    int expectedCntPerPageGroup = 10;
    int expectedCntPerPage = 10;

    int expectedStartPage = 1;
    int expectedStartPage1 = 11;

    int expectedEndPage = 2;
    int expectedEndPage1 = 20;
    PageBean<Diary> diary = adminService.showDiaryBoard(expectedOrder, expectedCurrentPage, null);
    assertNotNull(diary);
    assertEquals(expectedSize, diary.getPosts().size());
    assertEquals(expectedcurrentPage, diary.getCurrentPage());
    assertEquals(expectedtotalPage, diary.getTotalPage());
    assertEquals(expectedStartPage, diary.getStartPage());
    assertEquals(expectedEndPage, diary.getEndPage());


  }

  @Test
  void TestShowDiary() throws Exception {
    int expectedDiaryNo = 3;
    String expectedClientId = "americaman@gmail.com";
    String expectedDiaryTitle = "즐거운 광주으로 떠나요";
    int expectedViewCnt = 15231;
    int expectedLikeCnt = 5;

    Diary diary = adminService.showDiary(expectedDiaryNo);

    List<Route> expectedRoutes = diary.getRoutes();
    List<Comment> expectedComments = diary.getComments();
    List<Hashtag> expectedHastags = diary.getHashtags();

    // Diary diary1 = new Diary();
    System.out.println(diary);
    // diary1.setDiaryNo(diary.getDiaryNo());

    assertNotNull(diary);
    assertEquals(expectedDiaryNo, diary.getDiaryNo());
  }

  @Test
  void TestModifyNotice() throws Exception {
    String expectedAdminId = "1111";
    String noticeTitle = "modified notice";
    String noticeContent = "modified notice";
    Integer expectedViewCnt = 116;
    Notice notice = new Notice();
    notice.setNoticeNo(2);
    notice.setNoticeTitle(noticeTitle);
    notice.setNoticeContent(noticeContent);
    // adminService.modifyNotice(notice);

    Notice noticeInDb = noticeRepository.selectNotice(2);
    assertEquals(noticeTitle, noticeInDb.getNoticeTitle());
    assertEquals(expectedAdminId, noticeInDb.getAdminId());
    assertEquals(expectedViewCnt, noticeInDb.getNoticeViewCnt());

  }
}
