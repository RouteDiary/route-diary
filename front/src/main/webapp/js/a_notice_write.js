$(() => {
  //--이미지첨부파일 변경될때 미리보기 START--
  https: $("form>div.data>input[name=imageFile]").change(() => {
    let file = this.files[0];
    $("div.image>img.preview").attr("src", URL.createObjectURL(file));
  });
  //--이미지첨부파일 변경될때 미리보기 END--

  //--글쓰기 버튼 클릭 START--
  $("#btnSave").click(() => {
    let data = new Object();
    data.noticeTitle = $("input[name=title]").val();
    data.noticeContent = $("textarea.form-control.content").val();
    let notice = JSON.stringify(data);

    let formData = new FormData();
    formData.append("notice", notice);

    //이미지 파일
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
    $.ajax({
      url: `${backPath}/admin/notice/write`,
      method: "post",
      contentType: false,
      processData: false,
      enctype: "multipart/form-data",
      data: formData,
      success: (jsonObj) => {
        console.log(jsonObj.message);
        alert("작성되었습니다! 목록으로 이동합니다");
        window.location.href = "a_notice_list.html";
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
    return false;
  });
  //--글쓰기 버튼 클릭 END--

  //공지사항 목록으로 돌아가기
  $("#btnList").click(() => {
    // $(location).attr("href", "/");
    location.href = "./a_notice_list.html";
  });
});
// $(() => {
//   //--이미지첨부파일 변경될때 미리보기 START--
//   https: $("form>div.data>input[name=imageFile]").change(() => {
//     let file = this.files[0];
//     $("div.image>img.preview").attr("src", URL.createObjectURL(file));
//   });
//   //--이미지첨부파일 변경될때 미리보기 END--

//   //--글쓰기 버튼 클릭 START--
//   $("#btnSave").click(() => {
//     let data = new Object();
//     data.noticeTitle = $("input[name=title]").val();
//     data.noticeContent = $("textarea.form-control.content").val();
//     let notice = JSON.stringify(data);

//     let formData = new FormData();
//     formData.append("notice", notice);

//     //이미지 파일
//     var fileInput = $("input[name = imageFile]");
//     for (var i = 0; i < fileInput.length; i++) {
//       if (fileInput[i].files.length > 0) {
//         for (var j = 0; j < fileInput[i].files.length; j++) {
//           formData.append(
//             "imageFiles",
//             $("input[name = imageFile]")[i].files[j]
//           );
//         }
//       }
//     }
//     console.log("---formData---");
//     console.log(formData.get("notice"));
//     console.log(formData.getAll("imageFiles"));
//     $.ajax({
//       url: `${backPath}/admin/notice/write`,
//       method: "post",
//       contentType: false,
//       processData: false,
//       enctype: "multipart/form-data",
//       data: formData,
//       success: (jsonObj) => {
//         console.log(jsonObj.message);
//         alert("작성되었습니다! 목록으로 이동합니다");
//         window.location.href = "a_notice_list.html";
//         // location.href = "./a_notice_list.html";
//       },
//       error: (jqXHR) => {
//         alert("에러:" + jqXHR.status);
//       },
//     });
//     return false;
//   });
//   //--글쓰기 버튼 클릭 END--

//   //공지사항 목록으로 돌아가기
//   $("#btnList").click(() => {
//     // $(location).attr("href", "/");
//     location.href = "./a_notice_list.html";
//   });
// });
