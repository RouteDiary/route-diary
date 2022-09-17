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
      // nav bar start
      if (jsonObj.loginInfo == null) {
        let loginHtml =
          '<a class="nav-link login" data-value="Login" href="login.html">로그인</a>';
        $("li.nav-item.login").html(loginHtml);
      } else {
        let myDiaryBoardHtml =
          '<a class="nav-link my-diary-board" data-value="MyDiaryBoard" href="my_diary_board.html">내 다이어리 게시판</a>';
        $("li.nav-item.my-diary-board").html(myDiaryBoardHtml);
        let diaryWriteHtml =
          '<a class="nav-link write-diary" data-value="DiaryWrite" href="diary_write.html" >다이어리 작성하기</a>';
        $("li.nav-item.write-diary").html(diaryWriteHtml);
        let clientUpdateHtml =
          '<a class="nav-link client-update" data-value="ClientUpdate" href="client_check.html" >회원정보 수정/탈퇴</a>';
        $("li.nav-item.client-update").html(clientUpdateHtml);
        let logoutHtml =
          '<a class="nav-link logout" data-value="Logout" href=' +
          `${backPath}/client/logout` +
          ">로그아웃</a>";
        $("li.nav-item.login").html(logoutHtml);
      }
      //navbar end
      console.log(jsonObj);
      $inputId.val(jsonObj.t.clientId);
      $inputPwd.val(jsonObj.t.clientPwd);
      $inputCellphoneNo.val(jsonObj.t.clientCellphoneNo);
      $inputNickname.val(jsonObj.t.clientNickname);
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
      url: `${backPath}/client/nicknamecheck`,
      method: "get",
      data: nicknameForCheck,
      success: (jsonObj) => {
        console.log(jsonObj);
        alert("사용가능");
        $buttonUpdateSubmit.show();
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
        url: `${backPath}/client/modify`,
        method: "put",
        headers: {
          "content-Type": "application/json",
        },
        data: clientJsonObj,
        success: (jsonObj) => {
          alert(jsonObj.message);
          location.href = "index.html";
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
      url: `${backPath}/client/remove`,
      method: "delete",
      headers: {
        "content-Type": "application/json",
      },
      data: clientJsonObj2,
      success: (jsonObj) => {
        alert(jsonObj.message);
        location.href = "index.html";
      },
      error: (jqXHR, textStatus, errorThrown) => {
        errorThrown = "회원탈퇴에 실패하였습니다.";
        alert(errorThrown + " 사유 : " + jqXHR.status);
      },
    });
    return false;
  });
});
