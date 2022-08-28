package com.routediary.respository;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.routediary.dto.Notice;
import com.routediary.exception.InsertException;
import com.routediary.exception.UpdateException;
import com.routediary.repository.NoticeRepository;

import oracle.security.o3logon.a;
@SpringBootTest
class NoticeTest {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	NoticeRepository repository;
	@Test
	void InsertTest() throws InsertException {
		Notice notice = new Notice();
		notice.setAdminId("msk");
		notice.setNoticeTitle("강만두");
		notice.setNoticeContent("만두를좋아하는사람");
		repository.insert(notice);
	}

	@Test
	void UpdateTest() throws UpdateException, InsertException{

		Notice notice1 = new Notice();
		notice1.setAdminId("msk");
		notice1.setNoticeTitle("sorry");
		notice1.setNoticeContent("만두를좋아하는사람");
		repository.insert(notice1);

//		notice1.setNoticeTitle("강만두");
//		notice1.setNoticeNo(notice1.getNoticeNo());
		notice1.setNoticeContent("만두를안좋아함");
		repository.update(notice1);
		assertEquals(notice1.getNoticeContent(), "만두를안좋아함");

	}

}
