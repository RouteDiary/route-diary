$(() => {
  let $inputId = $("input.form-control.client-id");
  let $inputPwd = $("input.form-control.client-pwd");
  let $inputCellphoneNo = $("input.form-control.client-cellphone-no");
  let $inputNickname = $("input.form-control.client-nickname");
  let $buttonUpdateSubmit = $("input.btn.btn-primary.update");

  //회원정보 불러오기
  $.ajax({
    url: "http://localhost:9997/back/client/clientinfo",
    method: "get",
    success: (jsonObj) => {
      console.log(jsonObj);
      if (jsonObj.status == 200) {
        $inputId.val(jsonObj.t.clientId);
        $inputPwd.val(jsonObj.t.clientPwd);
        $inputCellphoneNo.val(jsonObj.t.clientCellphoneNo);
        $inputNickname.val(jsonObj.t.clientNickname);
      } else {
        alert(jsonObj.message);
      }
    },
    error: (jqXHR, textStatus, errorThrown) => {
      alert("에러 : " + jqXHR.status);
    },
  });

  //닉네임중복체크
  let $buttonNicknameDuplicationCheck = $(
    "button[name=nicknameduplicationcheck]"
  );
  $buttonNicknameDuplicationCheck.click(() => {
    console.log($("input.form-control.client-nickname").val());
    let nicknameForCheck = {
      clientNickname: $("input.form-control.client-nickname").val(),
    };
    $.ajax({
      url: "http://localhost:9997/back/client/nicknamecheck",
      method: "get",
      data: nicknameForCheck,
      success: (jsonObj) => {
        console.log(jsonObj);
        if (jsonObj.status == 200) {
          alert("사용가능");
          $buttonUpdateSubmit.show();
        } else {
          alert("중복된 닉네임");
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        alert("사용불가능한 닉네임 : " + jqXHR.status);
      },
    });
    return false;
  });
  $inputNickname.focus(() => {
    $buttonUpdateSubmit.hide();
  });

  //정보수정
  $formObj = $("form.update");
  $formObj.submit(() => {
    //비밀번호 일치확인
    let $inputPwdConfirm = $("input.form-control.client-pwd-check");
    if ($inputPwd.val() != $inputPwdConfirm.val()) {
      alert("비밀번호가 일치하지 않습니다.");
      console.log("$inputPwd의 값 : " + $inputPwd.val());
      console.log("$inputPwdConfirm의 값 : " + $inputPwdConfirm.val());
      $inputPwd.focus();
    } else {
      let clientObj = {
        clientId: $inputId.val(),
        clientPwd: $inputPwd.val(),
        clientNickname: $inputNickname.val(),
        clientCellphoneNo: $inputCellphoneNo.val(),
        clientStatusFlag: 1,
      };
      let clientJsonObj = JSON.stringify(clientObj);
      $.ajax({
        url: "http://localhost:9997/back/client/modify",
        method: "put",
        headers: {
          "content-Type": "application/json",
        },
        data: clientJsonObj,
        success: (jsonObj) => {
          if (jsonObj.status == 200) {
            alert(jsonObj.message);
            location.href = "index.html";
          } else {
            alert("정보수정 실패 : " + jsonObj.message);
          }
        },
        error: (jqXHR, textStatus, errorThrown) => {
          errorThrown = "회원정보수정에 실패하였습니다.";
          alert(errorThrown + " 사유 : " + jqXHR.status);
        },
      });
      return false;
    }
  });
  //회원탈퇴
  $withdrawlBtn = $("button.btn.btn-primary.delete");
  $withdrawlBtn.click(() => {
    let clientObj2 = {
      clientId: $inputId.val(),
      clientStatusFlag: 0,
    };
    let clientJsonObj2 = JSON.stringify(clientObj2);
    $.ajax({
      url: "http://localhost:9997/back/client/remove",
      method: "delete",
      headers: {
        "content-Type": "application/json",
      },
      data: clientJsonObj2,
      success: (jsonObj) => {
        if (jsonObj.status == 200) {
          alert(jsonObj.message);
          location.href = "index.html";
        } else {
          alert("회원탈퇴 실패 : " + jsonObj.message);
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        errorThrown = "회원탈퇴에 실패하였습니다.";
        alert(errorThrown + " 사유 : " + jqXHR.status);
      },
    });
    return false;
  });
});
