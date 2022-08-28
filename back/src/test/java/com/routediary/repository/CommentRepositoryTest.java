package com.routediary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Client;
import com.routediary.dto.Comment;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.UpdateException;


@SpringBootTest
class CommentRepositoryTest {

  @Autowired
  CommentRepository commentRepository;

  @Test
  void InsertCommentTest() throws InsertException {
    Client client = new Client();
    Comment c = new Comment();
    client.setClientId("japanwoman@gmail.com");
    c.setDiaryNo(4);
    c.setDiaryNo(4);
    c.setClient(client);
    c.setCommentContent("제육없이도 살수있어!");
    commentRepository.insert(c);

  }

  @Test
  void UpdateCommentTest() throws UpdateException {
    Client client = new Client();
    Comment c1 = new Comment();
    client.setClientId("koreawoman@gmail.com");
    c1.setDiaryNo(4);
    c1.setDiaryNo(4);
    c1.setClient(client);
    c1.setCommentContent("살수없어");
    commentRepository.update(c1);
    assertEquals(c1.getCommentContent(), "살수없어");

  }

  @Test
  void DeleteCommentTest() throws DeleteException {
    int diaryNo = 4;
    int commentNo = 3;

    commentRepository.delete(diaryNo, commentNo);

  }

  @Test
  void DeleteAllCommentTest() throws DeleteException {
    int diaryNo = 1;

    commentRepository.delete(diaryNo, diaryNo);
  }

}
