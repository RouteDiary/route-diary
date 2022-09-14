package com.routediary.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Hashtag;

@SpringBootTest
public class HashtagRepositoryTest {
  @Autowired
  private HashtagRepository repository;

  @Test
  public void testinsert() throws Exception {
    Hashtag hashtag = new Hashtag();
    hashtag.setDiaryNo(3);
    hashtag.setHashtag("제육");

    repository.insert(hashtag);
  }

  @Test
  public void testupdate() throws Exception {
    Hashtag hashtag = new Hashtag();
    hashtag.setDiaryNo(3);
    hashtag.setHashtag("김치");
    repository.update(hashtag);
  }

  @Test
  public void testdelete() throws Exception {
    Hashtag hashtag = new Hashtag();
    hashtag.setDiaryNo(3);
    hashtag.setHashtag("제육");
    repository.delete(hashtag);
  }

  @Test
  public void testdeleteAll() throws Exception {
    int diaryNo = 3;
    repository.deleteAll(diaryNo);

  }


}
