package com.my.dto;


/**
 * sight_no NUMBER NOT NULL, region_no NUMBER NOT NULL, sight_name VARCHAR2(20 CHAR) NOT NULL,
 * sight_addr VARCHAR2(30 CHAR), sight_id NUMBER NOT NULL, sight_category_name VARCHAR2(8 CHAR),
 * CONSTRAINT sights_pk PRIMARY KEY(sight_no), CONSTRAINT sights_uk UNIQUE(sight_id), CONSTRAINT
 * sights_regions_fk FOREIGN KEY(region_no) REFERENCES regions(region_no)
 *
 */
public class Sights {
  private int sightNo;
  private int regionNo;
  private String sightName;
  private String sightAddr;
  private int sightId;
  private String sightCategoryName;

  public Sights() {}

  public Sights(int sightNo, int regionNo, String sightName, String sightAddr, int sightId,
      String sightCategoryName) {
    this.sightNo = sightNo;
    this.regionNo = regionNo;
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

  public int getRegionNo() {
    return regionNo;
  }

  public void setRegionNo(int regionNo) {
    this.regionNo = regionNo;
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
    return "Sights [sightNo=" + sightNo + ", regionNo=" + regionNo + ", sightName=" + sightName
        + ", sightAddr=" + sightAddr + ", sightId=" + sightId + ", sightCategoryName="
        + sightCategoryName + "]";
  }
}
