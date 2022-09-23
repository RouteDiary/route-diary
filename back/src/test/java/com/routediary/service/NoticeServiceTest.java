package com.routediary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.exception.FindException;
import com.routediary.repository.NoticeRepository;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class NoticeServiceTest {
  NoticeRepository noticeRepository;
  @Autowired
  NoticeServiceImpl noticeService;
  
  @Test
  @DisplayName("전체 공지사항 목록 보여주기")
  void testshowNoticeBoard() throws FindException{
    int expectedcurrentPage = 1;
    int expectedcurrentPage1 = 11;
    
    int expectedtotalPage = 21;
    int expectedtotalRows = 209;
    int expectedCntPerPageGroup = 10;
    int expectedCntPerPage = 10;
    
    int expectedStartPage =1;
    int expectedStartPage1 =11;
    
    int expectedEndPage =10 ;
    int expectedEndPage1 =20 ;
    
    int expectedSize =10;
    int expectedSize1 =10;
    PageBean<Notice> notice = noticeService.showNoticeBoard(expectedcurrentPage1);
//    PageBean<Notice> notice = noticeService.showNoticeBoardByKeyword(expectedcurrentPage1, keyword1);
    // test1
//    assertNotNull(notice);
//    assertEquals(notice.getCntPerPageGroup(),expectedCntPerPageGroup);
//    assertEquals(notice.getCurrentPage(),expectedcurrentPage);
//    assertEquals(notice.getEndPage(),expectedEndPage);
//    assertEquals(notice.getStartPage(),expectedStartPage);
//    assertEquals(notice.getTotalPage(),expectedtotalPage);
//    assertEquals(notice.getPosts().size(),expectedSize);
    // test 2
    assertNotNull(notice);
    assertEquals(notice.getCntPerPageGroup(),expectedCntPerPageGroup);
    assertEquals(notice.getCurrentPage(),expectedcurrentPage1);
    assertEquals(notice.getEndPage(),expectedEndPage1);
    assertEquals(notice.getStartPage(),expectedStartPage1);
    assertEquals(notice.getTotalPage(),expectedtotalPage);
    assertEquals(notice.getPosts().size(),expectedSize1);
    
    
  }

  @Test
  @DisplayName("키워드로검색된 공지사항만 보여주기")
  void testshowNoticeBoardByKeyword() throws FindException {
//    int expectedcurrentPage = 1;
    int expectedcurrentPage1 = 11;
    
    int expectedtotalPage = 21;
    int expectedtotalRows = 202;
    int expectedCntPerPageGroup = 10;
    int expectedCntPerPage = 10;
    
//    int expectedStartPage =1;
    int expectedStartPage1 =11;
    
//    int expectedEndPage =10 ;
    int expectedEndPage1 =20 ;
    
//    int expectedSize =2;
    int expectedSize1 =10;
    
//    String keyword = "개";
    String keyword1 = "";
    
//    PageBean<Notice> notice = noticeService.showNoticeBoardByKeyword(expectedcurrentPage, keyword);
    PageBean<Notice> notice = noticeService.showNoticeBoardByKeyword(expectedcurrentPage1, keyword1);
    // test1
//    assertNotNull(notice);
//    assertEquals(notice.getCntPerPageGroup(),expectedCntPerPageGroup);
//    assertEquals(notice.getCurrentPage(),expectedcurrentPage);
//    assertEquals(notice.getEndPage(),expectedEndPage);
//    assertEquals(notice.getStartPage(),expectedStartPage);
//    assertEquals(notice.getTotalPage(),expectedtotalPage);
//    assertEquals(notice.getBoards().size(),expectedSize);
    // test 2
    assertNotNull(notice);
    assertEquals(notice.getCntPerPageGroup(),expectedCntPerPageGroup);
    assertEquals(notice.getCurrentPage(),expectedcurrentPage1);
    assertEquals(notice.getEndPage(),expectedEndPage1);
    assertEquals(notice.getStartPage(),expectedStartPage1);
    assertEquals(notice.getTotalPage(),expectedtotalPage);
    assertEquals(notice.getPosts().size(),expectedSize1);
    
    
    
  }
  @Test
  void testshowNotice() throws FindException{
    int expectedNoticeNo = 2;
    String expectedAdminId = "1111";
    String expectedNoticeTitle = "Mrs";
    String expectedNoticeContent = "Isoodon obesulus";
    //작성시간,수정시간 
    Integer expectedViewCnt = 115;
    
    Notice notice = noticeService.showNotice(expectedNoticeNo);
    
    assertNotNull(notice); 
    
    assertEquals(notice.getNoticeTitle(),expectedNoticeTitle);
    assertEquals(notice.getNoticeViewCnt(),expectedViewCnt);
    assertEquals(notice.getAdminId(),expectedAdminId);
    assertEquals(notice.getNoticeContent(),expectedNoticeContent);
//    assertEquals(notice.getNoticeWritingTime().compareTo(.valueOf("2021-03-23"),1);
    
    
  }

}
