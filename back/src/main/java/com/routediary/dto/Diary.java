package com.routediary.dto;


import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Diary {
  private int diaryNo;
  private String diaryTitle;
  @JsonFormat(pattern = "yy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date diaryWritingTime;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date diaryModifyingTime;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  private Date diaryStartDate;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  private Date diaryEndDate;
  private Integer diaryDisclosureFlag;
  private int diaryViewCnt;
  private int diaryLikeCnt;
  private Client client;
  private List<Route> routes;
  private List<Comment> comments;
  private List<Hashtag> hashtags;
  private List<Like> likes;
}
