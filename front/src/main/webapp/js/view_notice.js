$(() => {
  let queryString = location.search;
  var noticeNo = queryString.substring(1).split("=")[1];
  //   alert(noticeNo);
  $.ajax({
    method: "get",
    url: "http://localhost:9997/back/notice/" + noticeNo,
    // data: "data",
    dataType: "json",
    success: function (jsonObj) {
      console.log(jsonObj);
      let notice = jsonObj.t.notice;
      let noticeNo = notice.noticeNo;
      let noticeTitle = notice.noticeTitle;
      let noticeId = notice.adminId;
      let noticeViewCnt = notice.noticeViewCnt;

      let noticeWritingTime = notice.noticeWritingTime;
      let noticeWritingDate = new Date(noticeWritingTime);
      noticeWritingDate = noticeWritingDate.toLocaleDateString();

      let noticeModifyingTime = notice.noticeModifyingTime;

      let noticeContent = notice.noticeContent;

      let imageFilesCount = jsonObj.t.imageFilesCount;
      let $imageObj = $("div.images>div.image");
      for (var i = 1; i <= imageFilesCount; i++) {
        let $copyImageObj = $imageObj.clone();
        $.ajax({
          url: "http://localhost:9997/back/imagedownload",
          method: "get",
          data: {
            imageFileName: i + ".jpg",
            postNo: noticeNo,
            dto: "notice",
          },
          cache: false, //이미지 다운로드용 설정 (필수)
          xhrFields: {
            //이미지 다운로드용 설정 (필수)
            responseType: "blob",
          },
          success: function (responseData) {
            let url = URL.createObjectURL(responseData);
            let $imgObj = $copyImageObj.find("img.notice_image");
            $imgObj.attr("src", url);
            $imgObj.attr("style", "height:100vw");
            $imgObj.attr("alt", "공지사항이미지");
          },
          error: function (jqXHR) {
            //응답실패
            alert("이미지 다운로드 에러:" + jqXHR.status);
          },
        });
        $("div.images").append($copyImageObj);
      }
      $imageObj.hide();

      //   console.log(noticeNo);
      //   console.log(noticeTitle);
      //   console.log(noticeId);
      //   console.log(noticeViewCnt);
      //   console.log(noticeWritingTime);
      //   console.log(noticeModifyingTime);
      //   console.log(noticeModifyingDate);
      //   console.log(noticeWritingDate);

      $("p.notice_no").html(noticeNo + "번째 공지사항입니다!");
      $("h2.notice_title").html(noticeTitle);
      $("li.notice_id").html("작성자:" + noticeId);
      $("li.notice_viewCnt").html("조회수:" + noticeViewCnt);
      $("li.notice_writing_time").html("작성날짜:" + noticeWritingDate);
      $("li.notice_modifying_time").html("수정날짜:" + noticeModifyingTime);
      $("div.article").html(noticeContent);

      if (noticeModifyingTime == null) {
        $("li.notice_modifying_time").hide();
      } else {
        let noticeModifyingDate = new Date(noticeModifyingTime);
        noticeModifyingDate = noticeModifyingDate.toLocaleDateString();
        $("li.notice_modifying_time").html("수정날짜:" + noticeModifyingDate);
        $("li.notice_modifying_time").show();
      }
    },
    error: (jqXHR) => {
      alert("에러:" + jqXHR.status);
    },
  });
  //   --- 세션 받아오면 사용해봐야함 ;;
  //   if (adminId == null) {
  //     $("button.modify").hide();
  //     $("button.remove").hide();
  //   } else {
  //     $("button.modify").show();
  //     $("button.remove").show();
  //   }
  $("button.modify").on("click", function () {
    location.href = "./a_notice_modify.html?noticeNo=" + noticeNo;
  });
  $("button.remove").on("click", function () {
    $.ajax({
      method: "DELETE",
      url: "http://localhost:9997/back/admin/notice/" + noticeNo,
      success: function (data) {
        console.log(data);
        alert("삭제성공했습니다");
        location.href = "./notice_list.html";
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
  });
});
