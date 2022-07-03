package com.my.dto;

/**
 * diary_no NUMBER(6), -- FK, PK id VARCHAR2(20 CHAR), -- FK , PK CONSTRAINT likes_pk PRIMARY
 * KEY(diary_no, id), CONSTRAINT likes_clients_fk FOREIGN KEY(id) REFERENCES clients(id), CONSTRAINT
 * likes_diaries_fk FOREIGN KEY(diary_no) REFERENCES diaries(diary_no)
 */
public class Likes {
  private int diaryNo;
  private String id;

  public Likes() {}

  public Likes(int diaryNo, String id) {
    this.diaryNo = diaryNo;
    this.id = id;
  }

  public int getDiaryNo() {
    return diaryNo;
  }

  public void setDiaryNo(int diaryNo) {
    this.diaryNo = diaryNo;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Likes [diaryNo=" + diaryNo + ", id=" + id + "]";
  }
}
