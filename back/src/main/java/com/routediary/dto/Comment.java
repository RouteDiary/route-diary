package com.routediary.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
  private int diaryNo;
  private int commentNo;
  private String commentContent;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date commentWritingTime;
  private Client client;
}
