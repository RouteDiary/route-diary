
package com.routediary.dto;

import java.sql.Date;
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
  // @NonNull
  public int diaryNo;
  // @NonNull
  public int commentNo;
  public String commentContent;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  public Date commentWritingTime;
  // @NonNull
  public Client client;

}

