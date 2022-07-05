package com.my.dto;

import java.util.Date;

public class Comment {
  private int diaryNo;
  private int commentNo;
  // private String clientId;
  private String commentContent;
  private Date commentWritingTime;
  private Client client;

  public Comment() {}

  public Comment(int diaryNo, int commentNo, String commentContent, Date commentWritingTime,
      Client client) {
    this.diaryNo = diaryNo;
    this.commentNo = commentNo;
    this.commentContent = commentContent;
    this.commentWritingTime = commentWritingTime;
    this.client = client;
  }

  public int getDiaryNo() {
    return diaryNo;
  }

  public void setDiaryNo(int diaryNo) {
    this.diaryNo = diaryNo;
  }

  public int getCommentNo() {
    return commentNo;
  }

  public void setCommentNo(int commentNo) {
    this.commentNo = commentNo;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public Date getCommentWritingTime() {
    return commentWritingTime;
  }

  public void setCommentWritingTime(Date commentWritingTime) {
    this.commentWritingTime = commentWritingTime;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Override
  public String toString() {
    return "Comment [diaryNo=" + diaryNo + ", commentNo=" + commentNo + ", commentContent="
        + commentContent + ", commentWritingTime=" + commentWritingTime + ", client=" + client
        + "]";
  }


}
