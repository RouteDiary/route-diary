//카카오로그인
//질문사항 : 카카오 로그인으로 어떻게 client_id(카카오 api 토큰 값)를 back으로 넘기고,
//back에서 front로 status, message값 (로그인 성공, 실패여부)을 json으로 다시 전달하여 front에서 받는 로직을 어떻게 구현해야하나요???
Kakao.init("43f79556604648838efe6f30d077c9a0"); //발급받은 javascript키를 사용
console.log(Kakao.isInitialized()); // sdk초기화여부판단
function kakaoLogin() {
  Kakao.Auth.login({
    success: function (response) {
      Kakao.API.request({
        url: "/v2/user/me",
        success: function (response) {
          console.log(response);
        },
        fail: function (error) {
          console.log(error);
        },
      });
    },
    fail: function (error) {
      console.log(error);
    },
  });
}

//일반 로그인
$(function () {
  let $form = $("form.general_login");
  let url = "/back/login";
  $form.submit(function () {
    let $inputId = $("input[name=client_id]");
    let $inputPwd = $("input[name=client_pwd]");
    let inputIdValue, inputPwdValue;
    inputIdValue = $inputId.val();
    inputPwdValue = $inputPwd.val();
    let data = "client_id=" + inputIdValue + "&client_pwd=" + inputPwdValue;

    $.ajax({
      url: url,
      method: "post",
      data: data,
      success: function (jsonObj) {
        if (jsonObj.status == 1) {
          location.href = "/front/html/index.html";
        } else {
          alert(jsonObj.message);
        }
      },
      error: function (jqXHR, textStatus, errorThrown) {
        errorThrown = "로그인에 실패하였습니다.";
        alert(errorThrown + " 사유 : " + jqXHR.status);
      },
    });
    return false;
  });
});
