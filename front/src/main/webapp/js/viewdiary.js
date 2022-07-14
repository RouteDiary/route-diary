$(function () {
  //---화면 로딩---
  let queryString = location.search.substring(1); //diary_no=104
  // console.log(queryString);

  $.ajax({
    url: "/back/viewdiary",
    method: "POST",
    data: queryString,
    success: function (jsonObj) {
      console.log(jsonObj);

      //---다이어리 정보 호출---
      let diaryTitle = jsonObj.diary.diaryTitle;
      let diaryNo = jsonObj.diary.diaryNo;
      let client = jsonObj.diary.client.clientNickname;

      var diaryWritingTime = jsonObj.diary.diaryWritingTime;
      let diaryWritingDate = new Date(diaryWritingTime);
      diaryWritingDate = diaryWritingDate.toLocaleString();

      let diaryStartDate = jsonObj.diary.diaryStartDate;
      let diaryStartTime = new Date(diaryStartDate);
      diaryStartTime = diaryStartTime.toLocaleDateString();

      let diaryViewCnt = jsonObj.diary.diaryViewCnt;
      let diaryLikeCnt = jsonObj.diary.diaryLikeCnt;
      let routes = jsonObj.diary.routes;

      //---다이어리 form 가져오기---
      // for 문 돌리기
      let routesArr = [];
      for (let i = 0; i < routes.length; i++) {
        routesArr.push(routes[i].routeContent);
      }
      console.log(routesArr);

      //---다이어리 정보 WEB에 붙이기---
      $("div.diary span.diary_title").html(diaryTitle);
      $("div.diary dl>dt>span.diary_no").html(diaryNo);
      $("div.info dl>dt>span.client").html(client);
      $("div.diary dl>dt>span.diary_writing_time").html(diaryWritingDate);
      $("div.diary dl>dt>span.diary_view_cnt").html(diaryViewCnt);
      $("div.diary_start_date span.diary_start_date").html(diaryStartTime);
      $("div.diary span.diary_like_cnt").html(diaryLikeCnt);
      let $routesObj = $("div.routes");

      //$("div.route").html(); // route 갯수만큼 html 태그 구조 카피해야함
      let $routeObj = $("div.route");
      $(routes).each(function (i, element) {
        $routeCopyObj = $routeObj.clone(); //루트1개

        let route = "<fieldset>";
        // route += '<p class="img">다이어리 사진' + img + "</p>";

        route +=
          '<form class="route_content">다이어리 기록' +
          element.routeContent +
          "</form>";
        route += "</fieldset>";
        //$copyObj.find("div.viewdiary").html(route);
        $routeCopyObj.find("textarea").val(element.routeContent);
        $routesObj.append($routeCopyObj);
      });
      $routeObj.hide();

      //---작성된 댓글 보여주기---
      let loginInfo = jsonObj.loginInfo; //로그인된 아이디
      let jsonarr = jsonObj.diary.comments;
      let $commentObj = $("div.comment");
      $(jsonarr).each(function (i, element) {
        $copyObj = $commentObj.clone();
        $copyObj
          .find("span.clientNickname")
          .html(element.client.clientNickname);
        $copyObj
          .find("span.commentWritingTime")
          .html(element.commentWritingTime);
        $copyObj
          .find("input[name=commentContent]")
          .val(element.commentContent)
          .attr("readonly", "readonly");
        $copyObj
          .find("input[name=commentContent]")
          .attr("onfocus", "this.blur();");
        //댓글작성버튼 사라지기
        $copyObj.find("button.insert").hide();

        //로그인된 아이디와 댓글작성자가 같은 경우 - 수정,삭제버튼 보여주기
        if (loginInfo == element.client.clientId) {
          $copyObj.find("button.update").show();
          $copyObj.find("button.delete").show();
        }
        $("div.comments").append($copyObj);
      });
    },
    error: function (jqXHR) {
      alert("오류:" + jqXHR.status);
    },
  });

  //---댓글 등록---
  $("div.comments>div.comment>button.insert").click(function () {
    alert("등록되었습니다");
    $.ajax({
      url: "/back/commentinsert",
      method: "POST",
      data:
        queryString +
        "&comment_content=" +
        $(this).parent().find("input[name=commentContent]").val(),
      success: function (jsonObj) {
        if (jsonObj.status == 1) {
          location.href = "";
        } else {
          alert(jsonObj.msg);
        }
      },
    });
  });

  //---댓글 수정---
  $("div.comments>div.comment>button.update").click(function () {
    alert("update clicked");
    $.ajax({
      url: "/back/commentupdate",
      method: "POST",
      data:
        queryString +
        "&comment_content=" +
        $(this).parent().find("input[name=commentContent]").val(),
      success: function (jsonObj) {
        if (jsonObj.status == 1) {
          location.href = "";
        } else {
          alert(jsonObj.msg);
        }
      },
    });
  });

  //---댓글 삭제---
  $("div.comments>div.comment>button.delete").click(function () {
    alert("delete clicked");
    $.ajax({
      url: "/back/commentdelete",
      method: "POST",
      data:
        queryString +
        "&comment_content=" +
        $(this).parent().find("input[name=commentContent]").val(),
      success: function (jsonObj) {
        if (jsonObj.status == 1) {
          location.href = "";
        } else {
          alert(jsonObj.msg);
        }
      },
    });
  });
});
