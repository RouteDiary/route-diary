package com.my.dto;


public class Sight {
  private int sightNo;
  private String sightName;
  private String sightAddr;
  private int sightId;
  private String sightCategoryName;

  public Sight() {}

  public Sight(int sightNo, String sightName, String sightAddr, int sightId,
      String sightCategoryName) {
    this.sightNo = sightNo;
    this.sightName = sightName;
    this.sightAddr = sightAddr;
    this.sightId = sightId;
    this.sightCategoryName = sightCategoryName;
  }

  public int getSightNo() {
    return sightNo;
  }

  public void setSightNo(int sightNo) {
    this.sightNo = sightNo;
  }

  public String getSightName() {
    return sightName;
  }

  public void setSightName(String sightName) {
    this.sightName = sightName;
  }

  public String getSightAddr() {
    return sightAddr;
  }

  public void setSightAddr(String sightAddr) {
    this.sightAddr = sightAddr;
  }

  public int getSightId() {
    return sightId;
  }

  public void setSightId(int sightId) {
    this.sightId = sightId;
  }

  public String getSightCategoryName() {
    return sightCategoryName;
  }

  public void setSightCategoryName(String sightCategoryName) {
    this.sightCategoryName = sightCategoryName;
  }

  @Override
  public String toString() {
    return "Sight [sightNo=" + sightNo + ", sightName=" + sightName + ", sightAddr=" + sightAddr
        + ", sightId=" + sightId + ", sightCategoryName=" + sightCategoryName + "]";
  }


}
