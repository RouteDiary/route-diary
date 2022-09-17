package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NoPermissionException extends Exception {
  private ErrorCode errorCode;

  public NoPermissionException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}

