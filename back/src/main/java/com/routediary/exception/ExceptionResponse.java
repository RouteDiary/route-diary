package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponse {
  private int status;
  private String message;
  private String detail;

  public ExceptionResponse(ErrorCode errorCode) {
    this.status = errorCode.getStatus();
    this.message = errorCode.getMessage();
  }

  public ExceptionResponse(ErrorCode errorCode, String detail) {
    this.status = errorCode.getStatus();
    this.message = errorCode.getMessage();
    this.detail = detail;
  }
}
