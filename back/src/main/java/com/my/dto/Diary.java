package com.my.dto;

import java.util.Date;
import java.util.List;

public class Diary {
  private int diaryNo;
  private String clientId;
  private String diaryTitle;
  private Date diaryWritingTime;
  private Date diaryStartDate;
  private Date diaryEndDate;
  private int diaryDisclosureFlag;
  private int diaryViewCnt;
  private int diaryLikeCnt;
  private int diaryDeleteFlag;
  private Client client;
  private List<Route> route;
  private List<Comment> comment;

  public Diary() {}

  public Diary(int diaryNo, String clientId, String diaryTitle, Date diaryWritingTime,
      Date diaryStartDate, Date diaryEndDate, int diaryDisclosureFlag, int diaryViewCnt,
      int diaryLikeCnt, int diaryDeleteFlag, Client client, List<Route> route,
      List<Comment> comment) {
    this.diaryNo = diaryNo;
    this.clientId = clientId;
    this.diaryTitle = diaryTitle;
    this.diaryWritingTime = diaryWritingTime;
    this.diaryStartDate = diaryStartDate;
    this.diaryEndDate = diaryEndDate;
    this.diaryDisclosureFlag = diaryDisclosureFlag;
    this.diaryViewCnt = diaryViewCnt;
    this.diaryLikeCnt = diaryLikeCnt;
    this.diaryDeleteFlag = diaryDeleteFlag;
    this.client = client;
    this.route = route;
    this.comment = comment;
  }

  public int getDiaryNo() {
    return diaryNo;
  }

  public void setDiaryNo(int diaryNo) {
    this.diaryNo = diaryNo;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getDiaryTitle() {
    return diaryTitle;
  }

  public void setDiaryTitle(String diaryTitle) {
    this.diaryTitle = diaryTitle;
  }

  public Date getDiaryWritingTime() {
    return diaryWritingTime;
  }

  public void setDiaryWritingTime(Date diaryWritingTime) {
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

  public int getDiaryDisclosureFlag() {
    return diaryDisclosureFlag;
  }

  public void setDiaryDisclosureFlag(int diaryDisclosureFlag) {
    this.diaryDisclosureFlag = diaryDisclosureFlag;
  }

  public int getDiaryViewCnt() {
    return diaryViewCnt;
  }

  public void setDiaryViewCnt(int diaryViewCnt) {
    this.diaryViewCnt = diaryViewCnt;
  }

  public int getDiaryLikeCnt() {
    return diaryLikeCnt;
  }

  public void setDiaryLikeCnt(int diaryLikeCnt) {
    this.diaryLikeCnt = diaryLikeCnt;
  }

  public int getDiaryDeleteFlag() {
    return diaryDeleteFlag;
  }

  public void setDiaryDeleteFlag(int diaryDeleteFlag) {
    this.diaryDeleteFlag = diaryDeleteFlag;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<Route> getRoute() {
    return route;
  }

  public void setRoute(List<Route> route) {
    this.route = route;
  }

  public List<Comment> getComment() {
    return comment;
  }

  public void setComment(List<Comment> comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return "Diary [diaryNo=" + diaryNo + ", clientId=" + clientId + ", diaryTitle=" + diaryTitle
        + ", diaryWritingTime=" + diaryWritingTime + ", diaryStartDate=" + diaryStartDate
        + ", diaryEndDate=" + diaryEndDate + ", diaryDisclosureFlag=" + diaryDisclosureFlag
        + ", diaryViewCnt=" + diaryViewCnt + ", diaryLikeCnt=" + diaryLikeCnt + ", diaryDeleteFlag="
        + diaryDeleteFlag + ", client=" + client + ", route=" + route + ", comment=" + comment
        + "]";
  }

}
