package com.my.dto;

/**
 * diary_no NUMBER NOT NULL, route_no NUMBER NOT NULL, sight_no NUMBER NOT NULL, route_content
 * VARCHAR2(500 CHAR), CONSTRAINT routes_pk PRIMARY KEY(diary_no, route_no), CONSTRAINT
 * routes_sights_fk FOREIGN KEY(sight_no) REFERENCES sights(sight_no)
 *
 */
public class Route {
  private int diaryNo;
  private int routeNo;
  // private int sightNo;
  private String routeContent;
  private Sight sight;

}
