package com.my.dto;


public class Route {
  private int diaryNo;
  private int routeNo;
  private String routeContent;
  private Sight sight;

  public Route() {}

  public Route(int diaryNo, int routeNo, String routeContent, Sight sight) {
    this.diaryNo = diaryNo;
    this.routeNo = routeNo;
    this.routeContent = routeContent;
    this.sight = sight;
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

  public Sight getSight() {
    return sight;
  }

  public void setSight(Sight sight) {
    this.sight = sight;
  }

  @Override
  public String toString() {
    return "Route [diaryNo=" + diaryNo + ", routeNo=" + routeNo + ", routeContent=" + routeContent
        + ", sight=" + sight + "]";
  }

}
