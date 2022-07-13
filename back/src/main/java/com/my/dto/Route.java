package com.my.dto;


public class Route {
  private int diaryNo;
  private int routeNo;
  private String routeContent;
  private String kakaoMapId;

  public Route() {}

  public Route(int diaryNo, int routeNo, String routeContent, String kakaoMapId) {
    this.diaryNo = diaryNo;
    this.routeNo = routeNo;
    this.routeContent = routeContent;
    this.kakaoMapId = kakaoMapId;
  }

  public int getDiaryNo() {
    return diaryNo;
  }

  public void setDiaryNo(int diaryNo) {
    this.diaryNo = diaryNo;
  }

  public int getRouteNo() {
    return routeNo;
  }

  public void setRouteNo(int routeNo) {
    this.routeNo = routeNo;
  }

  public String getRouteContent() {
    return routeContent;
  }

  public void setRouteContent(String routeContent) {
    this.routeContent = routeContent;
  }

  public String getKakaoMapId() {
    return kakaoMapId;
  }

  public void setKakaoMapId(String kakaoMapId) {
    this.kakaoMapId = kakaoMapId;
  }

  @Override
  public String toString() {
    return "Route [diaryNo=" + diaryNo + ", routeNo=" + routeNo + ", routeContent=" + routeContent
        + ", kakaoMapId=" + kakaoMapId + "]";
  }
}
