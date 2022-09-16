$(function () {
  let url = "http://localhost:9997/back/admin/login";
  let $form = $("form.loginForm");
  $form.submit(function () {
    var adminId = $("input[name=login]").val();
    var adminPwd = $("input[name=pwd]").val();

    var loginData = { adminId: adminId, adminPwd: adminPwd };
    $.ajax({
      url: url,
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(loginData),

      success: function () {
        alert("로그인 성공");
        location.href = "/";
      },

      error: function (jqXHR) {
        if (jqXHR.status == 400) {
          alert(jqXHR.responseJSON.message);
        } else {
          alert("알 수 없는 에러[" + jqXHR.status + "]");
        }
      },
    });
    return false;
  });
});
