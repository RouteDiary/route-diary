package com.routediary.dto;

import com.routediary.enums.SuccessCode;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ResultBean<T> {
  private int status;
  private String message;
  private T t;

  public ResultBean(SuccessCode successCode) {
    this.status = successCode.getStatus();
    this.message = successCode.getMessage();
  }

  public ResultBean(SuccessCode successCode, T t) {
    this.status = successCode.getStatus();
    this.message = successCode.getMessage();
    this.t = t;
  }


}
