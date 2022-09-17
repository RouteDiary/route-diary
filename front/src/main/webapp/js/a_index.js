$(() => {
  let url = `${backPath}/`;
  $.ajax({
    url: url,
    method: "get",
    success: (jsonObj) => {
      console.log(jsonObj);
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
