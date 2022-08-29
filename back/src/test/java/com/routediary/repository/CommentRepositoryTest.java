package com.routediary.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Client;
import com.routediary.dto.Comment;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;


@SpringBootTest
class CommentRepositoryTest {

  @Autowired
  CommentRepository commentRepository;

  @Test
  void InsertCommentTest() throws InsertException {
    Client client = new Client();
    Comment comment = new Comment();
    client.setClientId("japanwoman@gmail.com");
    comment.setDiaryNo(4);
    comment.setClient(client);
    comment.setCommentContent("유익해요");
    commentRepository.insert(comment);
  }

  @Test
  void UpdateCommentTest() throws UpdateException, SelectException {
    Comment comment = new Comment();
    int diaryNo = 4;
    int commentNo = 1;
    String commentContent = "이거진짜인가요?";
    comment.setDiaryNo(diaryNo);
    comment.setCommentNo(commentNo);
    comment.setCommentContent(commentContent);
    commentRepository.update(comment);

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
    commentRepository.deleteAll(diaryNo);
  }
}
