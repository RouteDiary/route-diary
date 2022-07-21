$(() => {
  let $clientId = $("input[name=client_id]");
  let $cliendPwd = $("input[name=client_pwd]");
  let $clientNickname = $("input[name=client_nickname]");
  let $clientCellPhoneNo = $("input[name=client_cellphone_no]");
  let $buttonIdDuplicationCheck = $("button[name=idduplicationcheck]");
  let $buttonNicknameDuplicationCheck = $(
    "button[name=nicknameduplicationcheck]"
  );
  let isIdDuplicationCheck = false;
  let isNicknameDuplicationCheck = false;
  let $buttonSubmit = $("input[value=SIGNUP]");
  $buttonSubmit.css("display", "none");

  //id중복체크
  $buttonIdDuplicationCheck.click(() => {
    $.ajax({
      url: "/back/idduplicationcheck",
      type: "get",
      data: { client_id: $clientId.val() },
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          alert(jsonObj.message);
          isIdDuplicationCheck = true;
          console.log("id : " + isIdDuplicationCheck);
          if (
            (isIdDuplicationCheck = true && isNicknameDuplicationCheck == true)
          ) {
            $buttonSubmit.css("display", "inline");
          }
        } else {
          alert(jsonObj.message);
          $clientId.focus();
        }
      },
      error: function (jqXHR) {
        alert(jqXHR.status + ":" + jqXHR.statusText);
      },
    });
    return false;
  });

  $clientId.focus(() => {
    isIdDuplicationCheck = false;
    $buttonSubmit.css("display", "none");
    console.log("id : " + isIdDuplicationCheck);
  });

  //닉네임중복체크
  $buttonNicknameDuplicationCheck.click(() => {
    $.ajax({
      url: "/back/nicknameduplicationcheck",
      type: "get",
      data: { client_nickname: $clientNickname.val() },
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          alert(jsonObj.message);
          isNicknameDuplicationCheck = true;
          console.log("nickname : " + isNicknameDuplicationCheck);
          if (
            (isIdDuplicationCheck = true && isNicknameDuplicationCheck == true)
          ) {
            $buttonSubmit.css("display", "inline");
          }
        } else {
          alert(jsonObj.message);
          $clientNickname.focus();
        }
      },
      error: function (jqXHR) {
        alert(jqXHR.status + ":" + jqXHR.statusText);
      },
    });
    return false;
  });

  $clientNickname.focus(() => {
    isNicknameDuplicationCheck = false;
    $buttonSubmit.css("display", "none");
    console.log("id : " + isNicknameDuplicationCheck);
  });

  // 가입
  let $buttonFormSubmit = $("form.signup");
  $buttonFormSubmit.submit(() => {
    //비밀번호 중복확인
    let $cliendPwdCheck = $("input.client_pwd_check");
    if ($cliendPwd.val() != $cliendPwdCheck.val()) {
      alert("비밀번호가 일치하지 않습니다.");
      console.log("$cliendPwd의 값 : " + $cliendPwd.val());
      console.log("$cliendPwdcheck의 값 : " + $cliendPwdCheck.val());
      $cliendPwd.focus();
      return false;
    }
    $.ajax({
      url: "/back/signup",
      type: "post",
      data: {
        client_id: $clientId.val(),
        client_pwd: $cliendPwd.val(),
        client_nickname: $clientNickname.val(),
        client_cellphone_no: $clientCellPhoneNo.val(),
      },
      success: function (jsonObj) {
        if (jsonObj.status == 1) {
          alert(jsonObj.message);
          location.href = "/front/html/index.html";
        } else {
          alert(jsonObj.message);
        }
      },
      error: function (jqXHR) {
        alert("errortest");
        alert(jqXHR.status + ": " + jqXHR.statusText);
      },
    });
    return false;
  });

  //카카오 회원가입
  $kakaoSignupButton = $("a.kakao_signup_botton");
  Kakao.init("0779ea6d9f42a70e98491db350719d86"); //발급받은 javascript키를 사용
  console.log("sdk초기화여부판단 - " + Kakao.isInitialized()); // sdk초기화여부판단
  isGottenKakaoIdFromAPI = false;
  $kakaoSignupButton.click(() => {
    Kakao.Auth.login({
      success: (response) => {
        Kakao.API.request({
          url: "/v2/user/me",
          success: (response) => {
            kakaoId = response.id;
            isGottenKakaoIdFromAPI = true;
            console.log(kakaoId);
          },
          fail: (response) => {
            console.log(error);
          },
        });
      },
      fail: (response) => {
        console.log(error);
      },
    });

    if (isGottenKakaoIdFromAPI == true) {
      console.log(kakaoId + " in Ajax");
      let url = "/back/kakaosignup";
      let data = "client_id=" + kakaoId;
      $.ajax({
        url: url,
        method: "post",
        data: data,
        success: (jsonObj) => {
          if (jsonObj.status == 1) {
            location.href = "/front/html/index.html";
          } else {
            alert(jsonObj.message);
          }
        },
        error: (jqXHR, textStatus, errorThrown) => {
          errorThrown = "회원가입에 실패하였습니다.";
          alert(errorThrown + " 사유 : " + jqXHR.status);
        },
      });
      return false;
    }
  });
});
