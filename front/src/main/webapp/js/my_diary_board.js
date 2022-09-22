$(() => {
  //1페이지 불러오기 (작성일자순)
  viewMyDiaryBoard(1);

  //페이지에 따른 다이어리 불러오기
  $("div.pagination").on("click", "span", function (e) {
    let pageNo = $(e.target).html();
    console.log("pageNo : " + pageNo);
    if ($(e.target).hasClass("prev")) {
      pageNo = parseInt($(e.target).next().html()) - 1;
    } else if ($(e.target).hasClass("next")) {
      pageNo = parseInt($(e.target).prev().html()) + 1;
    } else {
      pageNo = parseInt($(e.target).html());
    }

    viewMyDiaryBoard(pageNo);
  });

  function viewMyDiaryBoard(pageNo) {
    // 변수선언
    let $tdObj = $("div.one_diary").not(".copy").show(); //원본 객체
    $("div.one_diary.copy").remove(); //복제본객체 삭제
    // 변수선언끝

    $.ajax({
      url: `${backPath}/diary/mylist/` + pageNo,
      method: "get",
      success: (jsonObj) => {
        console.log(jsonObj);
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
        //페이지 create
        $pageObj = $("div.pagination");
        let startPage = jsonObj.t.startPage;
        let endPage = jsonObj.t.endPage;
        let totalPage = jsonObj.t.totalPage;
        let paging = "";
        if (startPage > 1) {
          paging += '<span class="prev">PREV</span>&nbsp;&nbsp;';
        }
        for (var i = startPage; i <= endPage; i++) {
          paging += ' <span class="paging">' + i + "</span>&nbsp;&nbsp;";
        }
        if (endPage < totalPage) {
          paging += '<span class="next">NEXT</span>';
        }
        $pageObj.html(paging);

        //다이어리 불러오기
        $(jsonObj.t.posts).each((index, item) => {
          let $copyObj = $tdObj.clone(); // tdObj의 복제본 만들기
          let $imgObj = $copyObj.find("img");
          // $imgObj.attr(
          //   "src",
          //   "D:/files/images/diary_images/diary" + diaryNo + "/thumbnail.png"
          // );
          // $imgObj.attr("alt", "썸네일이미지");
          $.ajax({
            url: `${backPath}/imagedownload`,
            method: "get",
            data: {
              imageFileName: "thumbnail.png",
              postNo: item.diaryNo,
              dto: "diary",
            },

            cache: false, //이미지 다운로드용 설정 (필수)
            xhrFields: {
              //이미지 다운로드용 설정 (필수)
              responseType: "blob",
            },
            success: (responseData) => {
              let url = URL.createObjectURL(responseData);
              let $imgObj = $copyObj.find("img");
              $imgObj.attr("src", url);
              $imgObj.attr("alt", "다이어리이미지");
            },
          });
          // 다이어리 내용 불러오기
          let $url = $copyObj.find("a.link");
          let diaryNo = item.diaryNo;
          $url.attr("href", "./view_diary.html?diaryNo=" + diaryNo);

          let $like_cnt = $copyObj.find("div.like_cnt");
          $like_cnt.html("좋아요수 : " + item.diaryLikeCnt);

          let $diaryTitle = $copyObj.find("div.diary_title");
          $diaryTitle.html("제목 : " + item.diaryTitle);

          let $writingTime = $copyObj.find("div.writing_time");
          $writingTime.html("작성일자 : " + item.diaryWritingTime);

          let $travelDate = $copyObj.find("div.travel_date");
          $travelDate.html(
            "여행일자 : " + item.diaryStartDate + " ~ " + item.diaryEndDate
          );

          let $view_cnt = $copyObj.find("div.view_cnt");
          $view_cnt.html("조회수 : " + item.diaryViewCnt);

          $("div.diary_board").append($copyObj); //div.table의 막내 자식으로 $copyObj를 추가
          $copyObj.addClass("copy");
        });
        $tdObj.hide();
      },
      error: (jqXHR) => {
        if (jqXHR.status == 500) {
          alert("서버 오류 : " + jqXHR.status);
        } else {
          alert(jqXHR.status + "오류 : " + jqXHR.responseJSON.message);
        }
      },
    });
  }
});
