$(() => {
  let $inputId = $("input.form-control.client-id");
  let $inputPwd = $("input.form-control.client-pwd");
  let $inputPwdCheck = $("input.client-pwd-check");

  //회원정보 불러오기
  $.ajax({
    url: `${backPath}/client/clientinfo`,
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
          '<a class="nav-link logout" data-value="Logout" href="logout.html">로그아웃</a>';
        $("li.nav-item.login").html(logoutHtml);
      }
      //navbar end

      console.log(jsonObj);
      $inputId.val(jsonObj.t.clientId);
      $inputPwdCheck.val(jsonObj.t.clientPwd);
    },
    error: (jqXHR, textStatus, errorThrown) => {
      if (jqXHR.status == 500) {
        alert("서버 오류 : " + jqXHR.status);
      } else {
        alert(jqXHR.status + "오류 : " + jqXHR.responseJSON.message);
      }
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
