package com.my.dto;


public class Like {
  private int diaryNo;
  private String clientId;

  public Like() {}

  public Like(int diaryNo, String clientId) {
    this.diaryNo = diaryNo;
    this.clientId = clientId;
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

  @Override
  public String toString() {
    return "Like [diaryNo=" + diaryNo + ", clientId=" + clientId + "]";
  }

}
