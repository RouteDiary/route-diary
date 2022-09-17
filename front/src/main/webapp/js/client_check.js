$(() => {
  let $inputId = $("input.form-control.client-id");
  let $inputPwd = $("input.form-control.client-pwd");
  let $inputPwdCheck = $("input.client-pwd-check");

  //회원정보 불러오기
  $.ajax({
    url: "http://localhost:9997/back/client/clientinfo",
    method: "get",
    success: (jsonObj) => {
      console.log(jsonObj);
      if (jsonObj.status == 200) {
        $inputId.val(jsonObj.t.clientId);
        $inputPwdCheck.val(jsonObj.t.clientPwd);
      } else {
        alert(jsonObj.message);
      }
    },
    error: (jqXHR, textStatus, errorThrown) => {
      alert("에러 : " + jqXHR.status);
    },
  });

  //비밀번호 확인
  $("button.btn.btn-primary.check").click(() => {
    //비밀번호 일치확인
    if ($inputPwd.val() == $inputPwdCheck.val()) {
      location.href = "client_update.html";
    } else {
      $inputPwd.focus();
      alert("비밀번호가 일치하지 않습니다.");
    }
  });
});
