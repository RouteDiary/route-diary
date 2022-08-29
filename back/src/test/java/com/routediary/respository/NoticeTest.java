package com.routediary.respository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.routediary.dto.Notice;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;
import com.routediary.repository.NoticeRepository;

@SpringBootTest
class NoticeTest {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	NoticeRepository repository;

	@Test
	void InsertTest() throws InsertException { // 공지사항 작성 테스트
		Notice notice = new Notice();
		notice.setAdminId("msk");
		notice.setNoticeTitle("강만두");
		notice.setNoticeContent("만두를좋아하는사람");
		repository.insert(notice);
	}

	@Test
	void UpdateTest() throws UpdateException, SelectException { // 공지사항 수정 및 조회수 증가 테스트
																//조회수 증가 테스트 하려면 content,title 주석처리하고 진행

		int noticeNo = 1;
		Notice b = repository.selectNotice(noticeNo);
		String noticeContent = b.getNoticeContent();
		String noticeTitle = b.getNoticeTitle();
		Integer noticeViewCnt = b.getNoticeViewCnt();// 3
		
		Notice b1 = new Notice();
		b1.setNoticeTitle(noticeTitle+"만두");
		b1.setNoticeContent(noticeContent+"만두");
		b1.setNoticeViewCnt(noticeViewCnt); //
		b1.setNoticeNo(noticeNo);
		repository.update(b1);
		
		Notice b2 = repository.selectNotice(noticeNo);
		assertNotEquals(noticeTitle, b2.getNoticeTitle());
		assertNotEquals(noticeContent, b2.getNoticeContent());
		assertNotEquals(noticeViewCnt, b2.getNoticeViewCnt());
	}
	@Test
	void DeleteTest() throws DeleteException { //공지사항 삭제 테스트

		int noticeNo = 64;
		repository.delete(noticeNo);

	}
	@Test
	void SelectOneTest() throws SelectException { // 공지사항 클릭했을때 한개 보여주기 테스트
		
		int noticeNo = 65;
		
		String expectedAdminId = "9101";
	    String expectedTitle = "서포터즈 지원자 이벤트";
	    String expectedNoticeContent = "서포터즈 지원자 이벤트를 준비해보았습니다! 여행의 설레임을 마음껏 나누어주세요!";
	    Notice notice = repository.selectNotice(noticeNo);
	    assertEquals(expectedAdminId, notice.getAdminId());
	    assertEquals(expectedTitle, notice.getNoticeTitle());
	    assertEquals(expectedNoticeContent, notice.getNoticeContent());
		
	}
	@Test
	void SelectCountTest() throws SelectException{ // 공지사항 총개수 테스트
		
		int expectedCnt = 13;
		int cnt = repository.selectCount();
		assertEquals(expectedCnt, cnt);
	}
	@Test
	void SelectNoticesTest() throws SelectException{ // 공지사항 목록 테스트
		int currentPage = 1;
		int cntPerPage = 10;
		int endRow = currentPage * cntPerPage; //10
		int startRow = endRow - cntPerPage + 1; // 1
		
		int expectedSize = 10;
//		int []expectedNoticeNoArr = {12,11,10,9,8,7,6,5,4,3}; 
		List<Notice> list = repository.selectNotices(startRow, endRow,null);
		assertNotNull(list);
		assertEquals(expectedSize, list.size());
//		for(int i = 0; i<list.size(); i++) {
//			assertEquals(expectedNoticeNoArr[i], list.get(i).getNoticeNo());
//		}
	}
	@Test
	void SelectNoticesWordTest() throws SelectException{ // 공지사항 검색어를통한 목록 테스
		int currentPage = 1;
		int cntPerPage = 10;
		int endRow = currentPage * cntPerPage; //10
		int startRow = endRow - cntPerPage + 1; // 1
		
		int expectedSize = 3;
		String keyWord = "개";
		List<Notice> list = repository.selectNotices(startRow, endRow,keyWord);
		assertNotNull(list);
		assertEquals(expectedSize, list.size());
	}
}
