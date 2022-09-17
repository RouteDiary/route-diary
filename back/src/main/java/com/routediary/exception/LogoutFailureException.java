package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LogoutFailureException extends Exception {
  private ErrorCode errorCode;

  public LogoutFailureException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

