package com.routediary.respository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.routediary.dto.Diary;
import com.routediary.dto.Hashtag;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.UpdateException;
import com.routediary.repository.HashtagRepository;

@SpringBootTest
public class HashtagRepositoryTest {
	@Autowired
	private HashtagRepository repository;
	
	@Test
	public void testinsert() throws InsertException{
		Hashtag hashtag = new Hashtag();
		hashtag.setDiaryNo(3);
		hashtag.setHashTag("제육");

		repository.insert(hashtag);
	}
	
	@Test
	public void testupdate() throws UpdateException{
		Hashtag hashtag = new Hashtag();
		hashtag.setDiaryNo(3);
		hashtag.setHashTag("김치");
		repository.update(hashtag);
	}
	@Test
	public void testdelete() throws DeleteException{
		Hashtag hashtag = new Hashtag();
		hashtag.setDiaryNo(3);
		hashtag.setHashTag("제육");
		repository.delete(hashtag);
	}
	
	@Test
	public void testdeleteAll() throws DeleteException{
		int diaryNo = 3;
		repository.deleteAll(diaryNo);
		
	}

	
}
