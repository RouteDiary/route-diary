package com.my.dto;

/**
 * diary_no NUMBER(6), -- FK, PK id VARCHAR2(20 CHAR), -- FK , PK CONSTRAINT likes_pk PRIMARY
 * KEY(diary_no, id), CONSTRAINT likes_clients_fk FOREIGN KEY(id) REFERENCES clients(id), CONSTRAINT
 * likes_diaries_fk FOREIGN KEY(diary_no) REFERENCES diaries(diary_no)
 */
public class Like {
  private int diaryNo;
  private String clientId;

}
