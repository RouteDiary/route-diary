package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FindException extends Exception {
  private ErrorCode errorCode;

  public FindException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

