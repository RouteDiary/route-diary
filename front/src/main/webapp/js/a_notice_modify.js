$(() => {
  let queryString = location.search;
  var noticeNo = queryString.substring(1).split("=")[1];
  // alert(noticeNo);

  https: $("form>div.data>input[name=imageFile]").change(function () {
    let file = this.files[0];
    $("div.image>img.preview").attr("src", URL.createObjectURL(file));
  });
  let $noticeTitle = $("input.form-control");
  let $noticeContent = $("#content");

  $.ajax({
    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    url: "http://localhost:9997/back/notice/" + noticeNo,
    method: "get",
    success: (jsonObj) => {
      console.log(jsonObj);

      let $notice = jsonObj.t.notice;
      console.log($notice);
      $noticeTitle.val($notice.noticeTitle);
      $noticeContent.html($notice.noticeContent);
      // 사진은 구현예정임.
    },
    error: (jqXHR) => {
      alert(jqXHR.status);
    },
  });

  $("#btnSave").click(() => {
    let noticeTitle = $noticeTitle.val();
    console.log(noticeTitle);
    let noticeContent = $noticeContent.val();
    console.log(noticeContent);
    let formData = new FormData();
    let data = new Object();
    data.noticeTitle = noticeTitle;
    data.noticeContent = noticeContent;
    data.noticeNo = noticeNo;
    let notice = JSON.stringify(data);
    formData.append("notice", notice);

    var fileInput = $("input[name = imageFile]");
    for (var i = 0; i < fileInput.length; i++) {
      if (fileInput[i].files.length > 0) {
        for (var j = 0; j < fileInput[i].files.length; j++) {
          formData.append(
            "imageFiles",
            $("input[name = imageFile]")[i].files[j]
          );
        }
      }
    }
    console.log("---formData---");
    console.log(formData.get("notice"));
    console.log(formData.getAll("imageFiles"));
    console.log(noticeNo);
    $.ajax({
      url: "http://localhost:9997/back/admin/notice/" + noticeNo,
      method: "put",
      contentType: false,
      processData: false,
      enctype: "multipart/form-data",
      data: formData,
      success: (jsonObj) => {
        console.log(jsonObj.message);
        alert("작성되었습니다! 목록으로 이동합니다");
        location.href = "./notice_list.html";
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
    return false;
  });
});
