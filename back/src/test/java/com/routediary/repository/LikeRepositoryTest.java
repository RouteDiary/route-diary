package com.routediary.repository;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Like;

@SpringBootTest
public class LikeRepositoryTest {
  @Autowired
  private LikeRepository repository;

  @Test
  public void testSelectCount() throws Exception {
    int diaryNo = 1;
    int expectedCount = 2;
    int count = repository.selectCount(diaryNo);
    assertEquals(expectedCount, count);
  }

  @Test
  public void testInsert() throws Exception {
    Like like = new Like();
    like.setDiaryNo(67);
    like.setClientId("koreaman@gmail.com");
    repository.insert(like);
  }

  @Test
  public void testDelete() throws Exception {
    Like like = new Like();
    like.setDiaryNo(1);
    like.setClientId("koreaman@gmail.com");
    repository.delete(like);
  }

  @Test
  public void testDeleteAll() throws Exception {
    int diaryNo = 0;
    repository.deleteAll(diaryNo);
  }
}
