$(() => {
  let queryString = location.search;
  var noticeNo = queryString.substring(1).split("=")[1];
  //   alert(noticeNo);
  $.ajax({
    method: "get",
    url: `${backPath}/notice/` + noticeNo,
    // data: "data",
    dataType: "json",
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
          url: `${backPath}/imagedownload`,
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
          success: (responseData) => {
            let url = URL.createObjectURL(responseData);
            let $imgObj = $copyImageObj.find("img.notice_image");
            $imgObj.attr("src", url);
            $imgObj.attr("style", "width:300px");
            $imgObj.attr("alt", "공지사항이미지");
          },
          error: (jqXHR) => {
            //응답실패
            alert("이미지 다운로드 에러:" + jqXHR.status);
          },
        });
        $("div.images").append($copyImageObj);
      }
      $imageObj.hide();

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
      if (jqXHR.status == 500) {
        alert("서버 오류 : " + jqXHR.status);
      } else {
        alert(jqXHR.status + "오류 : " + jqXHR.responseJSON.message);
      }
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
  $("button.modify").on("click", () => {
    location.href = "./a_notice_modify.html?noticeNo=" + noticeNo;
  });
  $("button.remove").on("click", () => {
    $.ajax({
      method: "DELETE",
      url: `${backPath}/admin/notice/` + noticeNo,
      success: (data) => {
        console.log(data);
        alert("삭제성공했습니다");
        location.href = "./notice_list.html";
      },
      error: (jqXHR) => {
        if (jqXHR.status == 500) {
          alert("서버 오류 : " + jqXHR.status);
        } else {
          alert(jqXHR.status + "오류 : " + jqXHR.responseJSON.message);
        }
      },
    });
  });
  $("#btnList").click(() => {
    window.location.href = "./notice_list.html";
  });
});
