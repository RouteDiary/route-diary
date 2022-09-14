$(() => {
  // let loginedId = "koreaman@gmail.com";
  // let queryString = location.search.substring(1); //다이어리 목록에서 다이어리 1개를 선택 했을 때 ?에 대한 값을 가져오는 역할
  // let diary_no = queryString.split("=")[1]; //=로 split으로 나눈 1번 인덱스
  $.ajax({
    url: "http://localhost:9997/back/diary/" + 1,
    method: "GET",
    error: (jqXHR) => {
      alert(jqXHR.stauts);
    },
    success: (jsonObj) => {
      if (jsonObj.status == 200) {
        console.log(jsonObj);
        // let resultBeanObj = jsonObj;

        //---다이어리 정보 호출---
        let diaryTitle = jsonObj.t.diaryTitle;
        let diaryNo = jsonObj.t.diaryNo;
        let client = jsonObj.t.client.clientNickname;

        var diaryWritingTime = jsonObj.t.diaryWritingTime;
        let diaryWritingDate = new Date(diaryWritingTime);
        diaryWritingDate = diaryWritingDate.toLocaleString();

        let diaryStartDate = jsonObj.t.diaryStartDate;
        let diaryStartTime = new Date(diaryStartDate);
        diaryStartTime = diaryStartTime.toLocaleDateString();

        let diaryEndDate = jsonObj.t.diaryEndDate;
        let diaryEndTime = new Date(diaryEndDate);
        diaryEndTime = diaryEndTime.toLocaleDateString();

        let diaryViewCnt = jsonObj.t.diaryViewCnt;
        let diaryLikeCnt = jsonObj.t.diaryLikeCnt;
        let routes = jsonObj.t.routes;

        // for 문 돌리기
        let routesArr = [];
        for (let i = 0; i < routes.length; i++) {
          routesArr.push(routes[i].routeContent);
        }
        console.log(routesArr);

        //---다이어리 정보 WEB에 붙이기---
        $("div.diary span.diary_title").html(diaryTitle);
        $("div.diary dl>dt>span.diary_no").html(diaryNo);
        $("div.diary dl>dt>span.client").html(client);
        $("div.diary dl>dt>span.diary_writing_time").html(diaryWritingTime);
        $("div.diary dl>dt>span.diary_start_date").html(diaryStartDate);
        $("div.diary dl>dt>span.diary_end_date").html(diaryEndDate);
        $("div.diary dl>dt>span.diary_view_cnt").html(diaryViewCnt);
        $("div.diary dl>dt>span.diary_like_cnt").html(diaryLikeCnt);
        let $routesObj = $("div.routes");

        //$("div.route").html(); // route 갯수만큼 html 태그 구조 카피해야함
        let $routeObj = $("div.route");
        $(routes).each((i, element) => {
          $routeCopyObj = $routeObj.clone(); //루트1개

          let route = "<fieldset>";
          //route += '<p class="img">다이어리 사진' + img + "</p>";
          route +=
            '<form class="route_content" >여행 일지' +
            element.routeContent +
            "</form>";
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

        //--댓글 보여주기 start--
        // let loginInfo = jsonObj.loginInfo; //로그인된 아이디
        let jsonarr = jsonObj.t.comments;
        let $commentObj = $("div.comment_view");
        $(jsonarr).each((i, element) => {
          $copyObj = $commentObj.clone();
          var commentWritingTime = element.commentWritingTime;
          let commentWritingDate = new Date(commentWritingTime);
          commentWritingDate = commentWritingDate.toLocaleString();

          $copyObj.find("span.clientId").html(jsonObj.t.client.clientId);
          // $copyObj.find("span.clientId").hide();

          $copyObj.find("span.commentNo").html(jsonarr[i].commentNo);
          // $copyObj.find("span.commentNo").hide();

          $copyObj
            .find("span.clientNickname")
            .html("작성자: " + jsonObj.t.client.clientNickname);

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
      }
    },
    error: (jqXHR) => {
      alert("오류:" + jqXHR.status);
    },
  });

  //--댓글 보여주기 end--

  //---댓글 등록---
  $("div.comments>div.comment>button.insert").click((e) => {
    let commentContent = $(e.target)
      .siblings("textarea[name=commentContent]")
      .val();
    let data = JSON.stringify({
      diaryNo: 1,
      commentContent: commentContent,
    });
    alert("등록되었습니다");
    $.ajax({
      url: "http://localhost:9997/back/diary/" + 1 + "/comment",
      method: "post",
      headers: {
        "content-Type": "application/json",
      },
      data: data,
      success: (jsonObj) => {
        console.log("comment에서 실행" + jsonObj);
        if (jsonObj.status == 1) {
          location.href = "";
        } else {
          alert(jsonObj.msg);
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
    // console.log($(e.target).siblings('input.commentContent').val());

    let commentNo = $(e.target).siblings("span.commentNo").html();
    console.log("댓글번호:" + commentNo);

    let commentContent = $(e.target).siblings("input.commentContent").val();
    console.log("댓글내용:" + commentContent);

    let data = JSON.stringify({
      diaryNo: 1,
      commentNo: commentNo,
      commentContent: commentContent,
    });

    $.ajax({
      url: "http://localhost:9997/back/diary/" + 1 + "/comment",
      method: "put",
      headers: {
        "content-Type": "application/json",
      },
      data: data,
      success: (jsonObj) => {
        console.log("comment에서 실행" + jsonObj);
        if (jsonObj == 1) {
          // $commentContent.val("");
        } else {
          console.log("성공" + jsonObj);
        }
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
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

    let originalData = new Object();
    originalData.diaryNo = 1;
    originalData.commentNo = commentNo;
    originalData.client.clientId = clientId;
    let data = JSON.stringify(originalData);

    alert("삭제되었습니다.");

    $.ajax({
      url: "http://localhost:9997/back/diary/" + 1 + "/comment",
      method: "delete",
      headers: {
        "content-Type": "application/json",
      },
      data: data,
      success: (jsonObj) => {
        console.log("comment에서 실행" + jsonObj);
        if (jsonObj == 1) {
        } else {
          console.log("성공" + jsonObj);
        }
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
    return false;
  });
});
