package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmptyContentException extends Exception {
  private ErrorCode errorCode;

  public EmptyContentException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

