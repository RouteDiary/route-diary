package com.routediary.exception;

import com.routediary.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ModifyException extends Exception {
  private ErrorCode errorCode;

  public ModifyException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }
}
