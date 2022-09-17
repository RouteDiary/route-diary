package com.routediary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {
  @NonNull
  private String clientId;
  private String clientPwd;
  private String clientCellphoneNo;
  private String clientNickname;
  private Integer clientStatusFlag;
}
