package com.routediary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
  SIGNUP_SUCCESS(1, "회원가입 성공"), VAILD_ID(1, "사용가능한 ID입니다."), VAILD_NICKNAME(1,
      "사용가능한 닉네임입니다."), LOGIN_SUCCESS(1, "로그인 성공"), LOGOUT_SUCCESS(1, "로그아웃 성공"), PAGE_LOAD_SUCCESS(
          1, "페이지 로드 성공"), DIARY_LOAD_SUCCESS(1, "다이어리 로드 성공"), NOTICE_LOAD_SUCCESS(1,
              "공지사항 로드 성공"), SUCCESS_TO_WRITE(1, "글/댓글 작성 성공"), SUCCESS_TO_MODIFY(1,
                  "글/댓글 수정 성공"), SUCCESS_TO_REMOVE(1, "글/댓글 삭제 성공"), SUCCESS_TO_MODIFY_ACCOUNT(1,
                      "회원정보 수정 성공"), SUCCESS_TO_REMOVE_ACCOUNT(1,
                          "회원정보 삭제 성공"), LIKE_HANDLING_SUCCESS(1,
                              "좋아요/좋아요취소 성공"), SUCCESS_TO_BRING_CLIENT_INFO(1, "회원정보 불러오기 성공");

  private int status;
  private String message;
}
