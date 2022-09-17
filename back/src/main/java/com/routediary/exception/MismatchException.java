package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MismatchException extends Exception {
  private ErrorCode errorCode;

  public MismatchException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}
