package com.my.dto;

import java.util.Date;

/**
 * diary_no NUMBER(6) NOT NULL, -- PK id VARCHAR2(20 CHAR) NOT NULL, -- FK 20자리까지 가능 diary_title
 * VARCHAR2(30 CHAR) NOT NULL, -- 한글로 30자 가능 diary_writing_time DATE NOT NULL, diary_start_date DATE
 * NOT NULL, diary_end_date DATE NOT NULL, diary_flag NUMBER(1) NOT NULL, -- 공개 = 1 / 비공개 = 0
 * view_cnt NUMBER(5) NOT NULL, like_cnt NUMBER(5) NOT NULL, CONSTRAINT diaries_pk PRIMARY
 * KEY(diary_no), CONSTRAINT diaries_clients_fk FOREIGN KEY(id) REFERENCES clients(id)
 *
 */
public class Diaries {
  private int diaryNo;
  private String id;
  private String diaryTitle;
  private String diaryWritingTime;
  private Date diaryStartDate;
  private Date diaryEndDate;
  private int diaryFlag;
  private int viewCnt;
  private int likeCnt;
  private Clients clients;
  private Routes routes;
  private Comments comments;

  public Diaries() {}

  public Diaries(int diaryNo, String id, String diaryTitle, String diaryWritingTime,
      Date diaryStartDate, Date diaryEndDate, int diaryFlag, int viewCnt, int likeCnt,
      Clients clients, Routes routes) {
    this.diaryNo = diaryNo;
    this.id = id;
    this.diaryTitle = diaryTitle;
    this.diaryWritingTime = diaryWritingTime;
    this.diaryStartDate = diaryStartDate;
    this.diaryEndDate = diaryEndDate;
    this.diaryFlag = diaryFlag;
    this.viewCnt = viewCnt;
    this.likeCnt = likeCnt;
    this.clients = clients;
    this.routes = routes;
  }

  public Diaries(int diaryNo, String id, String diaryTitle, String diaryWritingTime,
      Date diaryStartDate, Date diaryEndDate, int diaryFlag, int viewCnt, int likeCnt,
      Clients clients, Routes routes, Comments comments) {
    this.diaryNo = diaryNo;
    this.id = id;
    this.diaryTitle = diaryTitle;
    this.diaryWritingTime = diaryWritingTime;
    this.diaryStartDate = diaryStartDate;
    this.diaryEndDate = diaryEndDate;
    this.diaryFlag = diaryFlag;
    this.viewCnt = viewCnt;
    this.likeCnt = likeCnt;
    this.clients = clients;
    this.routes = routes;
    this.comments = comments;
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

  public String getDiaryTitle() {
    return diaryTitle;
  }

  public void setDiaryTitle(String diaryTitle) {
    this.diaryTitle = diaryTitle;
  }

  public String getDiaryWritingTime() {
    return diaryWritingTime;
  }

  public void setDiaryWritingTime(String diaryWritingTime) {
    this.diaryWritingTime = diaryWritingTime;
  }

  public Date getDiaryStartDate() {
    return diaryStartDate;
  }

  public void setDiaryStartDate(Date diaryStartDate) {
    this.diaryStartDate = diaryStartDate;
  }

  public Date getDiaryEndDate() {
    return diaryEndDate;
  }

  public void setDiaryEndDate(Date diaryEndDate) {
    this.diaryEndDate = diaryEndDate;
  }

  public int getDiaryFlag() {
    return diaryFlag;
  }

  public void setDiaryFlag(int diaryFlag) {
    this.diaryFlag = diaryFlag;
  }

  public int getViewCnt() {
    return viewCnt;
  }

  public void setViewCnt(int viewCnt) {
    this.viewCnt = viewCnt;
  }

  public int getLikeCnt() {
    return likeCnt;
  }

  public void setLikeCnt(int likeCnt) {
    this.likeCnt = likeCnt;
  }

  public Clients getClients() {
    return clients;
  }

  public void setClients(Clients clients) {
    this.clients = clients;
  }

  public Routes getRoutes() {
    return routes;
  }

  public void setRoutes(Routes routes) {
    this.routes = routes;
  }

  public Comments getComments() {
    return comments;
  }

  public void setComments(Comments comments) {
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "Diaries [diaryNo=" + diaryNo + ", id=" + id + ", diaryTitle=" + diaryTitle
        + ", diaryWritingTime=" + diaryWritingTime + ", diaryStartDate=" + diaryStartDate
        + ", diaryEndDate=" + diaryEndDate + ", diaryFlag=" + diaryFlag + ", viewCnt=" + viewCnt
        + ", likeCnt=" + likeCnt + ", clients=" + clients + ", routes=" + routes + ", comments="
        + comments + "]";
  }
}
