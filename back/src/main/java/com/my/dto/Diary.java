package com.my.dto;

import java.util.Date;
import java.util.List;

public class Diary {
  private int diaryNo;
  private String clientId;
  private String diaryTitle;
  private String diaryWritingTime;
  private Date diaryStartDate;
  private Date diaryEndDate;
  private int diaryFlag;
  private int diaryViewCnt;
  private int diaryLikeCnt;
  private int diaryDeleteFlag;
  private Client client;
  private List<Route> route;
  private List<Comment> comment;

}
