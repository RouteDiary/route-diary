$(() => {
  //hashtag 관련 부분
  let hashtags = document.querySelector("input.hashtags");
  let tagify = new Tagify(hashtags); // initialize Tagify
  //1페이지 불러오기 (작성일자순)
  viewDiaryBoard(2, 1, null);

  $("div.form-check").on("click", () => {
    let hashtagsArr = new Array();
    $(tagify.value).each((index, item) => {
      console.log("item.value : " + item.value);
      hashtagsArr.push(item.value);
    });
    let paramObj = {
      hashtags: hashtagsArr,
    };
    let orderNo = $("input[name=order]:checked").val();
    console.log(orderNo);
    console.log("paramObj-------");
    // console.log(paramObj);
    viewDiaryBoard(orderNo, 1, paramObj);
  });
  //페이지에 따른 다이어리 불러오기
  $("div.pagination").on("click", "span", (e) => {
    //해시태그
    let hashtagsArr = new Array();
    $(tagify.value).each((index, item) => {
      console.log("item.value : " + item.value);
      hashtagsArr.push(item.value);
    });
    let paramObj = {
      hashtags: hashtagsArr,
    };
    //페이지넘버
    let pageNo = $(e.target).html();
    console.log("pageNo : " + pageNo);
    if ($(e.target).hasClass("prev")) {
      pageNo = parseInt($(e.target).next().html()) - 1;
    } else if ($(e.target).hasClass("next")) {
      pageNo = parseInt($(e.target).prev().html()) + 1;
    } else {
      pageNo = parseInt($(e.target).html());
    }

    let orderNo = $("input[name=order]:checked").val();
    console.log(orderNo);
    console.log("paramObj-------");
    console.log(paramObj);
    viewDiaryBoard(orderNo, pageNo, paramObj);
  });

  // 다이어리 내용 불러오기
  function viewDiaryBoard(order, pageNo, hashtagsObj) {
    // 변수선언
    let $tdObj = $("div.one_diary").not(".copy").show(); //원본 객체
    $("div.one_diary.copy").remove(); //복제본객체 삭제
    // 변수선언끝

    $.ajax({
      url: `${backPath}/admin/diary/list/` + order + `/` + pageNo,
      method: "get",
      data: hashtagsObj,
      dataType: "json",
      //contentType: "application/x-www-form-urlencoded; charset=UTF-8",
      success: (jsonObj) => {
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

        console.log(jsonObj);
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
          // let $imgObj = $copyObj.find("img");

          let diaryNo = item.diaryNo;
          $copyObj.on("click", () => {
            location.href = "./a_view_diary.html?diaryNo=" + diaryNo;
          });
          // $url.attr("href", "./a_view_diary.html?diaryNo=" + diaryNo);
          let $like_cnt = $copyObj.find("div.like_cnt");
          $like_cnt.html("좋아요수 : " + item.diaryLikeCnt);

          let $nickname = $copyObj.find("div.nickname");
          $nickname.html("작성자 : " + item.client.clientNickname);

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
            error: (jqXHR) => {},
          });

          $("div.diary_board").append($copyObj); //div.table의 막내 자식으로 $copyObj를 추가
          $copyObj.addClass("copy");
        });
        $tdObj.hide();
      },
      error: (jqXHR) => {
        alert("에러 : " + jqXHR.status);
      },
    });
  }
});
