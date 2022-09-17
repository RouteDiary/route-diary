package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RemoveException extends Exception {
  private ErrorCode errorCode;

  public RemoveException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

