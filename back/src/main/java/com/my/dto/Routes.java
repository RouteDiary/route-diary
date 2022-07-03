package com.my.dto;

/**
 * diary_no NUMBER NOT NULL, route_no NUMBER NOT NULL, sight_no NUMBER NOT NULL, route_content
 * VARCHAR2(500 CHAR), CONSTRAINT routes_pk PRIMARY KEY(diary_no, route_no), CONSTRAINT
 * routes_sights_fk FOREIGN KEY(sight_no) REFERENCES sights(sight_no)
 *
 */
public class Routes {
  private int diaryNo;
  private int routeNo;
  private int sightNo;
  private String routeContent;
  private Sights sights;

  public Routes() {}

  public Routes(int diaryNo, int routeNo, int sightNo, String routeContent, Sights sights) {
    this.diaryNo = diaryNo;
    this.routeNo = routeNo;
    this.sightNo = sightNo;
    this.routeContent = routeContent;
    this.sights = sights;
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

  public int getSightNo() {
    return sightNo;
  }

  public void setSightNo(int sightNo) {
    this.sightNo = sightNo;
  }

  public String getRouteContent() {
    return routeContent;
  }

  public void setRouteContent(String routeContent) {
    this.routeContent = routeContent;
  }

  public Sights getSights() {
    return sights;
  }

  public void setSights(Sights sights) {
    this.sights = sights;
  }

  @Override
  public String toString() {
    return "Routes [diaryNo=" + diaryNo + ", routeNo=" + routeNo + ", sightNo=" + sightNo
        + ", routeContent=" + routeContent + ", sights=" + sights + "]";
  }
}
