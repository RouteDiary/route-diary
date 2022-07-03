package com.my.dto;

/**
 * region_no NUMBER NOT NULL, -- PK region_name VARCHAR2(8 CHAR) NOT NULL, CONSTRAINT regions_pk
 * PRIMARY KEY(region_no)
 *
 */
public class Regions {
  private int regionNo;
  private String regionName;

  public Regions() {}

  public Regions(int regionNo, String regionName) {
    this.regionNo = regionNo;
    this.regionName = regionName;
  }

  public int getRegionNo() {
    return regionNo;
  }

  public void setRegionNo(int regionNo) {
    this.regionNo = regionNo;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  @Override
  public String toString() {
    return "Regions [regionNo=" + regionNo + ", regionName=" + regionName + "]";
  }
}
