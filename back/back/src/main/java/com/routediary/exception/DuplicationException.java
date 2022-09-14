package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DuplicationException extends Exception {
  private ErrorCode errorCode;

  public DuplicationException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

