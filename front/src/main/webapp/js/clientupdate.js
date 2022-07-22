$(() => {
  let $inputId = $("input[name=client_id]");
  let $inputPwd = $("input[name=client_pwd]");
  let $inputCellphoneNo = $("input[name=client_cellphone_no]");
  let $inputNickname = $("input[name=client_nickname]");
  let $buttonUpdateSubmit = $("input.update_submit");
  $buttonUpdateSubmit.css("display", "none");

  //회원정보 불러오기
  $(() => {
    $.ajax({
      contentType: "application/x-www-form-urlencoded; charset=UTF-8",
      url: "/back/clientinfo",
      method: "post",
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          $inputId.val(jsonObj.client.clientId);
          $inputPwd.val(jsonObj.client.clientPwd);
          $inputCellphoneNo.val(jsonObj.client.clientCellphoneNo);
          $inputNickname.val(jsonObj.client.clientNickname);
        } else {
          alert(jsonObj.message);
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        errorThrown = "회원정보수정에 실패하였습니다.";
        alert(errorThrown + " 사유 : " + jqXHR.status);
      },
    });
    return false;
  });
  //닉네임중복체크
  let $buttonNicknameDuplicationCheck = $(
    "button[name=nicknameduplicationcheck]"
  );
  $buttonNicknameDuplicationCheck.click(() => {
    $.ajax({
      contentType: "application/x-www-form-urlencoded; charset=UTF-8",
      url: "/back/nicknameduplicationcheck",
      type: "get",
      data: { client_nickname: $inputNickname.val() },
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          alert(jsonObj.message);
          $buttonUpdateSubmit.css("display", "inline");
        } else {
          alert(jsonObj.message);
          $clientNickname.focus();
        }
      },
      error: (jqXHR) => {
        alert(jqXHR.status + ":" + jqXHR.statusText);
      },
    });
    return false;
  });
  $inputNickname.focus(() => {
    $buttonUpdateSubmit.css("display", "none");
  });

  //정보수정
  $formObj = $("form.update");
  $formObj.submit(() => {
    //비밀번호 일치확인
    let $inputPwdConfirm = $("input.client_pwd_confirm");
    if ($inputPwd.val() != $inputPwdConfirm.val()) {
      alert("비밀번호가 일치하지 않습니다.");
      console.log("$inputPwd의 값 : " + $inputPwd.val());
      console.log("$inputPwdConfirm의 값 : " + $inputPwdConfirm.val());
      $cliendPwd.focus();
      return false;
    }
    $.ajax({
      url: "/back/clientupdate",
      method: "post",
      data: {
        client_pwd: $inputPwd.val(),
        client_nickname: $inputNickname.val(),
        client_cellphone_no: $inputCellphoneNo.val(),
      },
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          alert(jsonObj.message);
          location.href = "/front/html/index.html";
        } else {
          alert(jsonObj.message);
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        errorThrown = "회원정보수정에 실패하였습니다.";
        alert(errorThrown + " 사유 : " + jqXHR.status);
      },
    });
    return false;
  });
});
