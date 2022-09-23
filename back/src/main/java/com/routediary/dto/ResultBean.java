package com.routediary.dto;

import com.routediary.enums.SuccessCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultBean<T> {
  private int status;
  private String message;
  private T t;
  private String loginInfo;

  public ResultBean(SuccessCode successCode) {
    this.status = successCode.getStatus();
    this.message = successCode.getMessage();
  }

  public ResultBean(SuccessCode successCode, T t, String loginInfo) {
    this.status = successCode.getStatus();
    this.message = successCode.getMessage();
    this.t = t;
    this.loginInfo = loginInfo;
  }
}
