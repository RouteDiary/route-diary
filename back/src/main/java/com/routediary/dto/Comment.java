package com.routediary.dto;

import java.sql.Date;
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

public class Comment {
  @NonNull
  private int diaryNo;
  @NonNull
  private int commentNo;
  private String commentContent;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date commentWritingTime;
  @NonNull
  private Client client;
}
