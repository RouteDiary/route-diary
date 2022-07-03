package com.my.dto;

import java.util.Date;

/**
 * 
 * comment_no NUMBER(8) NOT NULL, -- PK diary_no NUMBER(6) NOT NULL, -- FK comment_content
 * VARCHAR2(300 CHAR) NOT NULL, comment_writing_time DATE NOT NULL, CONSTRAINT comments_pk PRIMARY
 * KEY(comment_no, diary_no), CONSTRAINT comments_travels_fk FOREIGN KEY(diary_no) REFERENCES
 * diaries(diary_no)
 *
 */
public class Comments {
  private int commentNo;
  private int diaryNo;
  private String commentContent;
  private Date commentWritingTime;

  public Comments() {}

  public Comments(int commentNo, int diaryNo, String commentContent, Date commentWritingTime) {
    this.commentNo = commentNo;
    this.diaryNo = diaryNo;
    this.commentContent = commentContent;
    this.commentWritingTime = commentWritingTime;
  }

  public int getCommentNo() {
    return commentNo;
  }

  public void setCommentNo(int commentNo) {
    this.commentNo = commentNo;
  }

  public int getDiaryNo() {
    return diaryNo;
  }

  public void setDiaryNo(int diaryNo) {
    this.diaryNo = diaryNo;
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

  @Override
  public String toString() {
    return "Comments [commentNo=" + commentNo + ", diaryNo=" + diaryNo + ", commentContent="
        + commentContent + ", commentWritingTime=" + commentWritingTime + "]";
  }
}
