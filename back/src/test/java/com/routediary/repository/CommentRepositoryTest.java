package com.routediary.respository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.routediary.dto.Client;
import com.routediary.dto.Comment;
import com.routediary.exception.InsertException;
import com.routediary.repository.CommentRepository;

import lombok.NonNull;


@SpringBootTest
public class CommentRepositoryTest {

  @Autowired
  CommentRepository repository1;

  @Test
  public void InsertTest() throws InsertException {

    Client client = new Client();
    
    Comment c = new Comment();
    client.setClientId("chinaman@gmail.com");
    c.setDiaryNo(1);
    c.setDiaryNo(1);
    c.setClient(client);
    c.setCommentContent("tq");
    repository1.insert(c);


  }
}
