package com.routediary.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Client;
import com.routediary.dto.Comment;


@SpringBootTest
class CommentRepositoryTest {

  @Autowired
  CommentRepository commentRepository;

  @Test
  void InsertCommentTest() throws Exception {
    Client client = new Client();
    Comment comment = new Comment();
    client.setClientId("japanwoman@gmail.com");
    comment.setDiaryNo(67);
    comment.setClient(client);
    comment.setCommentContent("유익해요");
    commentRepository.insert(comment);
  }

  @Test
  void UpdateCommentTest() throws Exception {
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
  void DeleteCommentTest() throws Exception {
    int diaryNo = 4;
    int commentNo = 3;
    commentRepository.delete(diaryNo, commentNo);
  }

  @Test
  void DeleteAllCommentTest() throws Exception {
    int diaryNo = 1;
    commentRepository.deleteAll(diaryNo);
  }
}
