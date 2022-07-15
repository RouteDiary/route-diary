$(() => {
  let $inputId = $();
  let $inputPwd = $("input[name=client_pwd]");
  let $inputCellphone = $("input[name=client_cellphone_no]");
  let $inputNickname = $("input[name=client_nickname]");
  let $btUpdate = $("div>button");

  //--form 전송 START--
  let $form = $("div.clientupdate>form");
  $form.submit(() => {
    let url = "http://localhost:8888/back/clientupdate";
    let inputIdValue, inputPwdValue;
    inputIdValue = $inputId.val();
    inputPwdValue = $inputPwd.val(); //사용자가 입력해준 비밀번호값

    let data = "id=" + inputIdValue + "&pwd=" + inputPwdValue;
    $.ajax({
      url: url,
      method: "post",
      data: data,
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          alert("회원정보수정완료!");
          location.href = "index.html";
        } else {
          alert("회원정보수정 실패");
          location.href = ""; //현재사용중인 주소로 재요청
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        alert(jqXHR.status + ":" + jqXHR.statusText);
      },
    });

    return false; //event.preventDefault() + event.stopPropagation()
  });
  //--form 전송 END--
  //--수정하기 버튼 클릭 START--
  $("div.update>button.ok").click(() => {
    $.ajax({
      url: "/back/clientupdate",
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          //수정성공경우
          alert(jsonObj.msg);

          $('nav>a[href="productlist.html"]').trigger("click"); //상품목록
        } else if (jsonObj.status == 0) {
          //수정실패
          alert(jsonObj.msg);
          $('nav>a[href="login.html"]').trigger("click"); //로그인
        } else if (jsonObj.status == -1) {
          //주문실패
          alert(jsonObj.msg);
        }
      },
      error: (jqXHR) => {
        alert("오류:" + jqXHR.status);
      },
    });
  });
  //--주문하기 버튼 클릭 END--
});
