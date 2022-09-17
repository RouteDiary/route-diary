$(() => {
  let queryString = location.search.substring(1); //다이어리 목록에서 다이어리 1개를 선택 했을 때 ?에 대한 값을 가져오는 역할
  let diaryNo = queryString.split("=")[1]; //=로 split으로 나눈 1번 인덱스
  console.log(diaryNo);
  $.ajax({
    url: `${backPath}/admin/diary/` + diaryNo,
    method: "GET",
    error: (jqXHR) => {
      alert(jqXHR.stauts);
    },
    success: (jsonObj) => {
      if ((jsonObj.status = 1)) {
        //nav bar start
        if (jsonObj.loginInfo == null) {
          let loginHtml =
            '<a class="nav-link login" data-value="Login" href="a_login.html">로그인</a>';
          $("li.nav-item.login").html(loginHtml);
        } else {
          let diaryWriteHtml =
            '<a class="nav-link write-diary" data-value="DiaryWrite" href="a_notice_write.html" >공지사항 작성하기</a>';
          $("li.nav-item.write-diary").html(diaryWriteHtml);
          let logoutHtml =
            '<a class="nav-link logout" data-value="Logout" href="a_logout.html">로그아웃</a>';
          $("li.nav-item.login").html(logoutHtml);
        }
        //nav bar end

        let diary = jsonObj.t;
        console.log(diary);
        let diaryClient = diary.client;
        let routes = diary.routes;
        let hashtags = diary.hashtags;
        let comments = diary.comments;
        console.log(comments);
        console.log(comments.client);
        let diaryTitle = diary.diaryTitle;
        let diaryNo = diary.diaryNo;
        let clientNickname = diaryClient.clientNickname;
        //--다이어리 정보 호출--

        var diaryWritingTime = diary.diaryWritingTime;
        let diaryWritingDate = new Date(diaryWritingTime);
        diaryWritingDate = diaryWritingDate.toLocaleString();

        let diaryStartDate = diary.diaryStartDate;
        let diaryStartTime = new Date(diaryStartDate);
        diaryStartTime = diaryStartTime.toLocaleDateString();

        let diaryEndDate = diary.diaryEndDate;
        let diaryEndTime = new Date(diaryEndDate);
        diaryEndTime = diaryEndTime.toLocaleDateString();

        let diaryViewCnt = diary.diaryViewCnt;
        let diaryLikeCnt = diary.diaryLikeCnt;

        let imageFilesCount = jsonObj.t.imageFilesCount;
        let $imageObj = $("div.images>div.image");
        for (var i = 1; i <= imageFilesCount; i++) {
          let $copyImageObj = $imageObj.clone();
          $.ajax({
            url: `${backPath}/imagedownload`,
            method: "get",
            data: {
              imageFileName: i + ".jpg",
              postNo: diaryNo,
              dto: "diary",
            },
            cache: false, //이미지 다운로드용 설정 (필수)
            xhrFields: {
              //이미지 다운로드용 설정 (필수)
              responseType: "blob",
            },
            success: function (responseData) {
              let url = URL.createObjectURL(responseData);
              let $imgObj = $copyImageObj.find("img.diary_image");
              $imgObj.attr("src", url);
              $imgObj.attr("alt", "다이어리이미지");
            },
            error: function (jqXHR) {
              //응답실패
              alert("이미지 다운로드 에러:" + jqXHR.status);
            },
          });
          $("div.images").append($copyImageObj);
        }
        $imageObj.hide();

        //--해시태그 for문--
        let hashtagsStr = "";
        for (let i = 0; i < hashtags.length; i++) {
          hashtagsStr += hashtags[i].hashtag;
          hashtagsStr += ", ";
        }

        //--루트정보 for 문 돌리기--
        let routesArr = [];
        for (let i = 0; i < routes.length; i++) {
          routesArr.push(routes[i].routeContent);
        }
        console.log(routesArr);

        //---다이어리 정보 WEB에 붙이기---
        $("div.diary span.diary_title").html(diaryTitle);
        $("div.diary dl>dt>span.diary_no").html(diaryNo);
        $("div.diary dl>dt>span.clientNickname").html(clientNickname);
        $("div.diary dl>dt>span.diary_writing_time").html(diaryWritingTime);
        $("div.diary dl>dt>span.diary_start_date").html(diaryStartDate);
        $("div.diary dl>dt>span.diary_end_date").html(diaryEndDate);
        $("div.diary dl>dt>span.diary_view_cnt").html(diaryViewCnt);
        $("div.diary dl>dt>span.diary_like_cnt").html(diaryLikeCnt);
        $("div.hashtag span.hash_tag").html(hashtagsStr);

        let $routesObj = $("div.routes");
        let $routeObj = $("div.route");
        $(routes).each((i, element) => {
          $routeCopyObj = $routeObj.clone(); //루트1개

          let route = "<fieldset>";
          route +=
            '<form class="route_content" >여행 일지' +
            element.routeContent +
            "</form>";
          route += "</fieldset>";

          $routeCopyObj
            .find("textarea")
            .val(element.routeContent)
            .attr("readonly", "readonly")
            .css("border", "none")
            .attr("onfocus", "this.blur();");
          $routesObj.append($routeCopyObj);
        });
        $routeObj.hide();
        console.log($routeCopyObj);

        //--댓글 보여주기 start--
        let jsonarr = comments;
        let $commentObj = $("div.comment_view");
        $(jsonarr).each((i, element) => {
          console.log("element:" + element);
          $copyObj = $commentObj.clone();
          var commentWritingTime = element.commentWritingTime;
          console.log(commentWritingTime);
          let commentWritingDate = new Date(commentWritingTime);
          commentWritingDate = commentWritingDate.toLocaleString();

          //댓글정보 copy
          $copyObj.find("span.clientId").html(jsonarr[i].client.clientId);
          $copyObj.find("span.clientId").hide();

          $copyObj.find("span.commentNo").html(jsonarr[i].commentNo);

          $copyObj
            .find("span.clientNickname")
            .html("작성자: " + jsonarr[i].client.clientNickname);

          $copyObj
            .find("span.commentWritingTime")
            .html("작성시간: " + commentWritingTime);

          $copyObj
            .find("input.commentContent")
            .val(jsonarr[i].commentContent)
            .attr("readonly", "readonly")
            .css("border", "none");
          $copyObj.find("input.commentContent").attr("onfocus", "this.blur()");

          //댓글작성버튼 사라지기 (로그인 안되있을시)
          $copyObj.find("button.insert").hide();

          $("div.comments").append($copyObj);
        });

        // //input태그, 수정, 삭제 버튼 숨기기
        $commentObj.hide();
        //--댓글 보여주기 end--
      }
    },
    error: (jqXHR) => {
      alert("오류:" + jqXHR.status);
    },
  });

  // ---댓글 삭제---

  $("div.comments").on("click", "div.comment_view>button.delete", (e) => {
    let commentNo = $(e.target).siblings("span.commentNo").html();
    //-- sampledata
    // let diaryNo = 10;

    let jsondata = {
      diaryNo: diaryNo,
      commentNo: commentNo,
      //   admin: { adminId: adminId },
    };
    // alert("삭제되었습니다.");

    $.ajax({
      url: `${backPath}/admin/diary/` + diaryNo + `/comment`,
      method: "delete",
      contentType: "application/json",
      data: JSON.stringify(jsondata),
      success: (jsonObj) => {
        location.reload();
        console.log("comment에서 실행" + jsonObj);
        if ((jsonObj = 1)) {
          console.log("성공" + jsonObj);
        }
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
    return false;
  });

  //--다이어리 삭제--
  $("div.diary_delete").on("click", "button.delete", (e) => {
    let data = { diaryNo: diaryNo };
    console.log(JSON.stringify(data));
    $.ajax({
      url: `${backPath}/admin/diary/` + diaryNo /*diaryNo*/,
      method: "delete",
      contentType: "application/json",
      data: JSON.stringify(data),
      success: (jsonObj) => {
        location.href = "a_diary_board.html";
        console.log("diary에서 실행" + jsonObj);
        alert("삭제되었습니다.");
        if (jsonObj == 1) {
          console.log("삭제");
          console.log(jsonObj);
        }
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
    return false;
  });
});
