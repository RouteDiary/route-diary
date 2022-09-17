$(() => {
  let url = `${backPath}/`;
  $.ajax({
    url: url,
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
          '<a class="nav-link logout" data-value="Logout" href=' +
          `${backPath}/client/logout` +
          ">로그아웃</a>";
        $("li.nav-item.login").html(logoutHtml);
      }
      //navbar end

      //좋아요순 다이어리 불러오기
      let $likeCntOriginalObj = $("div.like-cnt.carousel-item.active")
        .not(".copy")
        .show(); //원본객체
      $("div.carousel-item.active.copy").remove(); //복제본객체삭제{
      $(jsonObj.t.top5DiariesByLikeCnt).each((index, item) => {
        let $copyObj = $likeCntOriginalObj.clone();

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
            let $imgObj = $copyObj.find("img.d-block.w-100");
            console.log("테스트");
            console.log($imgObj);
            $imgObj.attr("src", url);
            $imgObj.attr("alt", "다이어리이미지");
          },
          error: (jqXHR) => {},
        });

        let diaryNo = item.diaryNo;
        $diaryUrl = $copyObj.find("a.diary-url");
        $diaryUrl.attr("href", "./view_diary.html?diaryNo=" + diaryNo);
        $diaryTitleObj = $copyObj.find("div.diary-title");
        $diaryTitleObj.html(item.diaryTitle);
        $("div.carousel-inner.like-cnt").append($copyObj);
        $copyObj.removeClass("active like").addClass("copy");
      });

      //최신순 다이어리 불러오기
      let $writingTimeOriginalObj = $("div.writing-time.carousel-item.active")
        .not(".copy")
        .show(); //원본객체
      $("div.carousel-item.active.copy").remove(); //복제본객체삭제{
      $(jsonObj.t.top5DiariesByWritingTime).each((index, item) => {
        let $copyObj = $writingTimeOriginalObj.clone();
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
        let diaryNo = item.diaryNo;
        $diaryUrl = $copyObj.find("a.diary-url");
        $diaryUrl.attr("href", "./view_diary.html?diaryNo=" + diaryNo);
        $diaryTitleObj = $copyObj.find("div.diary-title");
        $diaryTitleObj.html(item.diaryTitle);
        $("div.carousel-inner.writing-time").append($copyObj);
        $copyObj.removeClass("active write").addClass("copy");
      });
    },
    error: (jqXHR) => {
      alert(jqXHR.message);
    },
  });
  return false;
});
