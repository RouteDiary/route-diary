package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddException extends Exception {
  private ErrorCode errorCode;

  public AddException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

