$(() => {
  //id & id 중복확인
  let $clientId = $("input.form-control.id");
  let $clientPwd = $("input.form-control.pwd");
  let $clientPwdCheck = $("input.form-control.pwd-check");
  let $clientNickname = $("input.form-control.nickname");
  let $clientCellPhoneNo = $("input.form-control.cellphone-no");

  let $submitBtn = $("input.btn.btn-primary.btn-lg");
  let $idCheckBtn = $("button.btn.btn-primary.idcheck");
  let $nicknameCheckBtn = $("button.btn.btn-primary.nicknamecheck");

  //아이디 중복체크
  $idCheckBtn.click(() => {
    let idForCheck = {
      clientId: $clientId.val(),
    };
    $.ajax({
      url: `${backPath}/client/idcheck`,
      method: "get",
      data: idForCheck,
      success: (jsonObj) => {
        console.log(jsonObj);
        if ($clientId.val() != "") {
          alert("사용가능");
          // $submitBtn.show();
        } else {
          alert("중복된 닉네임");
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        alert(jqXHR.message);
      },
    });
    return false;
  });
  // $clientId.focus(() => {
  //   $submitBtn.hide();
  // });

  //닉네임 중복체크
  $nicknameCheckBtn.click(() => {
    let nicknameForCheck = {
      clientNickname: $clientNickname.val(),
    };
    $.ajax({
      url: `${backPath}/client/nicknamecheck`,
      method: "get",
      data: nicknameForCheck,
      success: (jsonObj) => {
        console.log(jsonObj);
        if ($clientNickname.val() != "") {
          alert("사용가능");
          // $submitBtn.show();
        } else {
          alert("중복된 닉네임");
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        alert(jqXHR.message);
      },
    });
    return false;
  });
  // $clientNickname.focus(() => {
  //   $submitBtn.hide();
  // });

  //가입버튼 클릭 이벤트 발생 ->
  //form submit이벤트 발생 -> 기본처리 (전송)
  //form 객체 찾기
  let $form = $("form.signup_form");
  $form.submit(() => {
    //비밀번호 일치 확인
    if ($clientPwd.val() != $clientPwdCheck.val()) {
      alert("비밀번호가 일치하지 않습니다.");
      $clientPwd.focus();
      return false;
    }
    alert("비밀번호 일치합니다");
    let url = `${backPath}/client/signup`;
    let clientObj = {
      clientId: $clientId.val(),
      clientPwd: $clientPwd.val(),
      clientNickname: $clientNickname.val(),
      clientCellphoneNo: $clientCellPhoneNo.val(),
    };
    let data = JSON.stringify(clientObj);
    console.log("client_id:" + $clientId);
    $.ajax({
      url: url,
      headers: {
        "content-Type": "application/json",
      },
      method: "post",
      data: data,
      success: (jsonObj) => {
        alert(jsonObj.message);
        console.log(jsonObj);
        location.href = "login.html";
      },
      error: (jqXHR) => {
        alert("회원가입 실패 : " + jqXHR.message);
      },
    });
    return false;
  });
});

//  // ------가입버튼 클릭 START----
//   $('form.form_signup').submit(function(){
//     console.log(this); //이벤트가 발생된 객체가 this
//   });

//   $('form.form_signup').submit((e)=>{
//     console.log(this); //window객체
//     console.log(e.target); //이벤트가 발생된 객체가
//   });

//   $('form.form_signup').submit((e)=>{
//     alert("submit");
//     let $clientIdVal = $clientId.val();
//     let $clientPwdVal = $clientPwd.val();
//     let $clientNicknameVal = $clientNickname.val();
//     let $clientCellPhoneNoVal = $clientCellPhoneNo.val();
//     let clientObj = {};
//     clientObj.clientId = $clientIdVal;
//     clientObj.clientPwd = $clientPwdVal;
//     clientObj.clientNickname = $clientNicknameVal;
//     clientObj.clientCellphoneNo = $clientCellPhoneNoVal;

//     let data = JSON.stringify(clientObj);
//     $.ajax({
//       url: "http://localhost:9997/back/client/signup",
//       method: "POST",
//       timeout: 0,
//       headers: {
//               "": "Content-Type,value:application/json",
//               "Content-Type": "application/json"
//       },
//       data : data,
//       success: function(jsonObj){
//         console.log(jsonObj);
//     //     if(jsonObj.status != 200){
//     //       alert(jsonObj.message);
//     //     }else{
//     //     location.html = "./index.html";
//     //     }
//     //   },
//     //   error: function(jqXHR){
//     //     alert(jqXHR.status);
//       }

//     });

//     return false;
//   });
// });
//  // ------가입버튼 클릭 END----
