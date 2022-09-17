$(() => {
  let $form = $("form.login_form");
  $form.submit(() => {
    let url = `${backPath}/client/login`;
    let $clientId = $("input.form-control.client-id");
    let $clientPwd = $("input.form-control.client-pwd");
    let loginData = { clientId: $clientId.val(), clientPwd: $clientPwd.val() };
    let loginJson = JSON.stringify(loginData);
    $.ajax({
      url: url,
      method: "post",
      contentType: "application/json",
      data: loginJson,
      headers: {
        "content-Type": "application/json",
      },
      success: (jsonObj) => {
        alert(jsonObj.message);
        location.href = "index.html";
      },

      error: (jqXHR) => {
        alert(" 에러 : [" + jqXHR.message + "]");
      },
    });
    return false;
  });
});
