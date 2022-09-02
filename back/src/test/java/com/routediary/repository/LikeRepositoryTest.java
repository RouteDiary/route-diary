package com.routediary.repository;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Like;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;

@SpringBootTest
public class LikeRepositoryTest {
  @Autowired
  private LikeRepository repository;

  @Test
  public void testSelectCount() throws SelectException {
    int diaryNo = 1;
    int expectedCount = 2;
    int count = repository.selectCount(diaryNo);
    assertEquals(expectedCount, count);
  }

  @Test
  public void testInsert() throws InsertException {
    Like like = new Like();
    like.setDiaryNo(1);
    like.setClientId("koreaman@gmail.com");
    repository.insert(like);
  }

  @Test
  public void testDelete() throws DeleteException {
    Like like = new Like();
    like.setDiaryNo(1);
    like.setClientId("koreaman@gmail.com");
    repository.delete(like);
  }

  @Test
  public void testDeleteAll() throws DeleteException {
    int diaryNo = 0;
    repository.deleteAll(diaryNo);
  }
}
