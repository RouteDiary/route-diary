package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WithdrawnClientException extends Exception {
  private ErrorCode errorCode;

  public WithdrawnClientException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

