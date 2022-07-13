//카카오로그인
Kakao.init("43f79556604648838efe6f30d077c9a0"); //발급받은 키 중 javascript키를 사용해준다.
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
