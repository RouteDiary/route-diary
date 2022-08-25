package com.routediary.dto;

import java.sql.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Diary {
  @NonNull
  private int diaryNo;
  private String diaryTitle;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date diaryWritingTime;
  private Date diaryStartDate;
  private Date diaryEndDate;
  private int diaryDisclosureFlag;
  private int diaryViewCnt;
  private int diaryLikeCnt;
  @NonNull
  private Client client;
  @NonNull
  private List<Route> routes;
  private List<Comment> comments;
  private List<Comment> hashtags;
  private Like like;
  private Date diaryModifyingTime;
}
