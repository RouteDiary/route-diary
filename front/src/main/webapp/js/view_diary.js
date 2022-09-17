$(() => {
  let queryString = location.search.substring(1); //다이어리 목록에서 다이어리 1개를 선택 했을 때 ?에 대한 값을 가져오는 역할
  let diary_no = queryString.split("=")[1]; //=로 split으로 나눈 1번 인덱스
  $.ajax({
    url: `${backPath}/diary/` + diary_no,
    method: "GET",
    error: (jqXHR) => {
      alert(jqXHR.stauts);
    },
    success: (jsonObj) => {
      console.log(jsonObj);
      // let resultBeanObj = jsonObj;
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
      //--다이어리 정보 호출--
      let loginedId = jsonObj.t.loginedId;
      $("div.diary input.loginedId").val(loginedId);

      let diaryTitle = jsonObj.t.diary.diaryTitle;
      let diaryNo = jsonObj.t.diary.diaryNo;
      let clientNickname = jsonObj.t.diary.client.clientNickname;

      var diaryWritingTime = jsonObj.t.diary.diaryWritingTime;
      let diaryWritingDate = new Date(diaryWritingTime);
      diaryWritingDate = diaryWritingDate.toLocaleString();

      let diaryStartDate = jsonObj.t.diary.diaryStartDate;
      let diaryStartTime = new Date(diaryStartDate);
      diaryStartTime = diaryStartTime.toLocaleDateString();

      let diaryEndDate = jsonObj.t.diary.diaryEndDate;
      let diaryEndTime = new Date(diaryEndDate);
      diaryEndTime = diaryEndTime.toLocaleDateString();

      let diaryViewCnt = jsonObj.t.diary.diaryViewCnt;
      let diaryLikeCnt = jsonObj.t.diary.diaryLikeCnt;

      let clientId = jsonObj.t.diary.client.clientId; //hide() 처리

      let routes = jsonObj.t.diary.routes;

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
          success: (responseData) => {
            let url = URL.createObjectURL(responseData);
            let $imgObj = $copyImageObj.find("img.diary_image");
            $imgObj.attr("src", url);
            $imgObj.attr("alt", "다이어리이미지");
          },
          error: (jqXHR) => {},
        });

        $("div.images").append($copyImageObj);
      }
      $imageObj.hide();

      //--해시태그 for문--
      let hashtags = jsonObj.t.diary.hashtags;
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
      $("div.diary dl>dt>span.clientId").html(clientId).hide();

      //$("div.route").html(); // route 갯수만큼 html 태그 구조 카피해야함
      let $routesObj = $("div.routes");
      let $routeObj = $("div.route");
      $(routes).each((i, element) => {
        $routeCopyObj = $routeObj.clone(); //루트1개

        let route = "<fieldset>";
        route +=
          '<form class="route_content" >' + element.routeContent + "</form>";
        route += "</fieldset>";

        //$copyObj.find("div.viewdiary").html(route);
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
      //좋아요 보여주기 start
      //로그인된 사용자가 좋아요를 눌렀는지 여부에 따라 이미지보여주기
      let $likeImgObj = $("div.like_view>span.like>img");
      if (jsonObj.t.loginedId == null || jsonObj.t.loginedId == "") {
        //로그인 안된경우 -
        $likeImgObj.hide();
      } else {
        $likeImgObj.show();
        if (jsonObj.t.likeFlag == false) {
          //로그인 되고 좋아요 누르지 않은 경우
          $likeImgObj.attr("src", "../images/unlike.png");
        } else {
          //로그인 되고 좋아요 누른 경우
          $likeImgObj.attr("src", "../images/like.png");
        }
      }
      //좋아요 보여주기 end
      //--댓글 보여주기 start--
      // let loginInfo = jsonObj.loginInfo; //로그인된 아이디
      let jsonarr = jsonObj.t.diary.comments;
      let $commentObj = $("div.comment_view");
      $(jsonarr).each((i, element) => {
        $copyObj = $commentObj.clone();
        var commentWritingTime = element.commentWritingTime;
        let commentWritingDate = new Date(commentWritingTime);
        commentWritingDate = commentWritingDate.toLocaleString();

        //댓글정보 copy
        $copyObj.find("span.clientId").html(jsonarr[i].client.clientId);
        $copyObj.find("span.clientId").hide();

        $copyObj.find("span.commentNo").html(jsonarr[i].commentNo);
        // $copyObj.find("span.commentNo").hide();

        $copyObj
          .find("span.clientNickname")
          .html("작성자: " + jsonObj.t.diary.client.clientNickname);

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

        //로그인된 아이디와 댓글작성자가 같은 경우 - 수정,삭제버튼 보여주기
        // if (loginInfo == jsonObj.t.client) {
        //   $copyObj.find("button.update").show();
        //   $copyObj.find("button.delete").show();
        // }
        $("div.comments").append($copyObj);
      });

      //input태그, 수정, 삭제 버튼 숨기기
      $commentObj.hide();
      //--댓글 보여주기 end--
    },
    error: (jqXHR) => {
      alert("오류:" + jqXHR.status);
    },
  });

  //---댓글 등록---
  $("div.comments>div.comment>button.insert").click((e) => {
    let commentContent = $(e.target)
      .siblings("textarea[name=commentContent]")
      .val();
    let diaryNo = $("span.diary_no").html();
    let data = JSON.stringify({
      diaryNo: diaryNo,
      commentContent: commentContent,
    });
    alert("등록되었습니다");
    $.ajax({
      url: `${backPath}/diary/` + diaryNo + "/comment",
      method: "post",
      headers: {
        "content-Type": "application/json",
      },
      data: data,
      success: (jsonObj) => {
        location.reload();
        console.log("comment에서 실행" + jsonObj);
        if (jsonObj.status == 1) {
          location.href = "";
        } else {
          alert(jsonObj.message);
        }
      },
    });
    return false;
  });

  //---댓글 수정---
  $("div.comments").on("click", "div.comment_view>button.update", (e) => {
    if (
      $(e.target).siblings("input.commentContent").attr("readonly") !==
      undefined
    ) {
      $(e.target)
        .siblings("input.commentContent")
        .attr({ readonly: false, onfocus: false });
      $(e.currentTarget).html("수정완료");
      alert("댓글을 수정하세요");
    } else {
      $(e.target)
        .siblings("input.commentContent")
        .attr({ readonly: "readonly", onfocus: "this.blur()" });

      $(e.currentTarget).html("수정");
      alert("댓글 수정완료");
    }
    let clientId = $(e.target).siblings("span.clientId").html();
    console.log("아이디:" + clientId);

    let commentNo = $(e.target).siblings("span.commentNo").html();
    console.log("댓글번호:" + commentNo);

    let commentContent = $(e.target).siblings("input.commentContent").val();
    console.log("댓글내용:" + commentContent);

    let diaryNo = $("span.diary_no").html();

    let data = JSON.stringify({
      diaryNo: diaryNo,
      commentNo: commentNo,
      commentContent: commentContent,
      client: { clientId: clientId },
    });

    $.ajax({
      url: `${backPath}/diary/` + diaryNo + "/comment",
      method: "put",
      headers: {
        "content-Type": "application/json",
      },
      data: data,
      success: (jsonObj) => {
        console.log("comment에서 실행" + jsonObj);
        if (jsonObj == 1) {
          console.log("성공" + jsonObj);
        }
      },
      error: (jqXHR) => {
        alert("수정 권한이 업습니다.:" + jqXHR.status);
        location.reload();
      },
    });
    return false;
  });

  // ---댓글 삭제---
  $("div.comments").on("click", "div.comment_view>button.delete", (e) => {
    let commentNo = $(e.target).siblings("span.commentNo").html();
    console.log("댓글번호:" + commentNo);

    let clientId = $(e.target).siblings("span.clientId").html();
    console.log("아이디:" + clientId);

    let diaryNo = $("span.diary_no").html();
    let jsondata = {
      diaryNo: diaryNo,
      commentNo: commentNo,
      client: { clientId: clientId },
    };
    alert("삭제되었습니다.");

    $.ajax({
      url: `${backPath}/diary/` + diaryNo + "/comment",
      method: "delete",
      contentType: "application/json",
      data: JSON.stringify(jsondata),
      success: (jsonObj) => {
        location.reload();
        console.log("comment에서 실행" + jsonObj);
        if (jsonObj == 1) {
          console.log("성공" + jsonObj);
        }
      },
      error: (jqXHR) => {
        alert("삭제 권한이 없습니다.:" + jqXHR.status);
      },
    });
    return false;
  });

  //--좋아요--
  $("div.like_view>span.like>img").on("click", (e) => {
    let diaryNo = $("div.diary dl>dt>span.diary_no").html(); //현재 다이어리의 번호를 가져옴
    console.log("번호: " + diaryNo);
    let clientId = $("div.diary input.loginedId").val();
    console.log("아이디: " + clientId);
    let data = JSON.stringify({
      diaryNo: diaryNo,
      clientId: clientId,
    });

    let likeImageSrc = $(e.target).attr("src");
    let likeStatus = false; //기본값 또는 좋아요 취소
    if (likeImageSrc == "../images/unlike.png") {
      likeStatus = true; //좋아요
    }
    $.ajax({
      url: `${backPath}/like/` + likeStatus,
      method: "post",
      contentType: "application/json",
      data: data,
      success: (jsonObj) => {
        console.log("like에서 실행");
        console.log(jsonObj);
        console.log("------------");
        location.reload();
        if (likeImageSrc == "../images/unlike.png") {
          $(e.target).attr("src", "../images/like.png");
        } else {
          $(e.target).attr("src", "../images/unlike.png");
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
    let diaryNo = $("div.diary dl>dt>span.diary_no").html();
    let clientId = $("div.diary dl>dt>span.clientId").html();
    let data = { diaryNo: diaryNo, client: { clientId: clientId } };
    console.log(JSON.stringify(data));
    // alert("다이어리가 삭제되었습니다.");

    $.ajax({
      url: `${backPath}/diary/` + diaryNo,
      method: "delete",
      contentType: "application/json",
      data: JSON.stringify(data),
      success: (jsonObj) => {
        location.href = "./diary_board.html";
        console.log("diary에서 실행" + jsonObj);
        if (jsonObj == 1) {
          console.log("삭제");
          console.log(jsonObj);
        }
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.message);
      },
    });
    return false;
  });
});
