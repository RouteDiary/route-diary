package com.routediary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  ID_PWD_MISMATCH(400, "ID와 비밀번호가 일치하지 않습니다."), POST_NOT_FOUND(400, "없는 글번호 입니다."), INVALID_ORDER(
      400, "잘못된 정렬 요청입니다."), FAILED_TO_LOGOUT(500, "로그아웃에 실패했습니다."), NOT_LOGINED(401,
          "로그인 하세요."), NO_PERMISSION(401, "자신의 글/댓글만 수정/삭제 할 수 있습니다."), EMPTY_TITLE(404,
              "제목이 없습니다."), EMPTY_CONTENT(404, "내용이 비어있습니다."), EMPTY_DATE(404,
                  "날자를 입력하지 않있습니다."), ALREADY_WITHDRAWN_CLIENT(403,
                      "이미 탈퇴한 회원입니다."), ID_DUPLICATION(400, "중복된 아이디입니다."), NICKNAME_DUPLICATION(
                          400, "중복된 닉네임입니다."), LIKE_CANCLE_FAILURE(400,
                              "이미 좋아요한 다이어리에만 좋아요 취소를 할 수 있습니다."), INVALID_DTO_NAME(400,
                                  "부정확한 dto 이름입니다.");

  private int status;
  private String message;
}
