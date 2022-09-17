package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NumberNotFoundException extends Exception {
  private ErrorCode errorCode;

  public NumberNotFoundException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}
