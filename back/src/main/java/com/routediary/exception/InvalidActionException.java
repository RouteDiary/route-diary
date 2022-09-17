package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvalidActionException extends Exception {
  private ErrorCode errorCode;

  public InvalidActionException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

