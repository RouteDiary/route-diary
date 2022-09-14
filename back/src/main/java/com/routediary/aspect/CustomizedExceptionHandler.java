package com.routediary.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.routediary.enums.ErrorCode;
import com.routediary.exception.AddException;
import com.routediary.exception.DuplicationException;
import com.routediary.exception.EmptyContentException;
import com.routediary.exception.ExceptionResponse;
import com.routediary.exception.FindException;
import com.routediary.exception.InvalidActionException;
import com.routediary.exception.LogoutFailureException;
import com.routediary.exception.MismatchException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.NoPermissionException;
import com.routediary.exception.NotLoginedException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.exception.RemoveException;
import com.routediary.exception.WithdrawnClientException;

@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> exceptionHandle(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponseForException("오류 발생", e);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AddException.class)
  public ResponseEntity<ExceptionResponse> addExceptionHandle(AddException e, WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponseForException("데이터 추가 실패", e);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(FindException.class)
  public ResponseEntity<ExceptionResponse> findExceptionHandle(FindException e,
      WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponseForException("데이터 찾기 실패", e);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ModifyException.class)
  public ResponseEntity<ExceptionResponse> modifyExceptionHandle(ModifyException e,
      WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponseForException("데이터 수정 실패", e);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RemoveException.class)
  public ResponseEntity<ExceptionResponse> removeExceptionHandle(RemoveException e,
      WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponseForException("데이터 삭제 실패", e);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DuplicationException.class)
  public ResponseEntity<ExceptionResponse> duplicationExceptionHandle(DuplicationException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmptyContentException.class)
  public ResponseEntity<ExceptionResponse> emptyContentExceptionHandle(EmptyContentException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidActionException.class)
  public ResponseEntity<ExceptionResponse> invalidActionExceptionHandle(InvalidActionException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(LogoutFailureException.class)
  public ResponseEntity<ExceptionResponse> logoutFailureExceptionHandle(LogoutFailureException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MismatchException.class)
  public ResponseEntity<ExceptionResponse> mismatchExceptionHandle(MismatchException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoPermissionException.class)
  public ResponseEntity<ExceptionResponse> noPermissionExceptionHandle(NoPermissionException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NotLoginedException.class)
  public ResponseEntity<ExceptionResponse> notLoginedExceptionHandle(NotLoginedException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NumberNotFoundException.class)
  public ResponseEntity<ExceptionResponse> numberNotFoundExceptionHandle(NumberNotFoundException e,
      WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WithdrawnClientException.class)
  public ResponseEntity<ExceptionResponse> withdrawnClientExceptionHandle(
      WithdrawnClientException e, WebRequest request) {
    ErrorCode errorCode = e.getErrorCode();
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(JsonProcessingException.class)
  public ResponseEntity<ExceptionResponse> jsonProcessingExceptionExceptionHandle(
      WithdrawnClientException e, WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponseForException("오류 발생", e);
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ExceptionResponse createExceptionResponseForException(String message, Exception e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.setStatus(500);
    exceptionResponse.setMessage(message);
    exceptionResponse.setDetail(e.getMessage());
    return exceptionResponse;
  }
}
