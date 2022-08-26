package com.routediary.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Client;
import com.routediary.dto.Comment;
import com.routediary.exception.InsertException;


@SpringBootTest
public class CommentRepositoryTest {

  @Autowired
  CommentRepository repository;

  @Test
  public void commentInsertTest() throws InsertException {

    Comment c = new Comment();
    Client client = new Client();
    client.setClientId("koreaman@gmail.com");

    c.setDiaryNo(1);
    c.setDiaryNo(1);
    c.setClient(client);
    c.setCommentContent("tq");
    repository.insert(c);

  }
}
