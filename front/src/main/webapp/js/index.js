$(function () {
  let url = "http://localhost:9997/back/";
  $.ajax({
    url: url,
    method: "get",
    success: (jsonObj) => {
      console.log(jsonObj);
      if (jsonObj.status == 200) {
        //좋아요순 다이어리 불러오기
        let $likeCntOriginalObj = $("div.like-cnt.carousel-item.active")
          .not(".copy")
          .show(); //원본객체
        $("div.carousel-item.active.copy").remove(); //복제본객체삭제{
        $(jsonObj.t.top5DiariesByLikeCnt).each(function (index, item) {
          console.log(jsonObj.t.top5DiariesByLikeCnt);
          let $copyObj = $likeCntOriginalObj.clone();

          $.ajax({
            url: "http://localhost:9997/back/imagedownload",
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
            success: function (responseData) {
              let url = URL.createObjectURL(responseData);
              let $imgObj = $copyObj.find("img");
              $imgObj.attr("src", url);
              $imgObj.attr("alt", "다이어리이미지");
            },
            error: function (jqXHR) {
              //응답실패
              alert("이미지 다운로드 에러:" + jqXHR.status);
            },
          });

          let diaryNo = item.diaryNo;
          $diaryUrl = $copyObj.find("a.diary-url");
          $diaryUrl.attr(
            "href",
            "http://localhost/front/html/view_diary.html?diaryNo=" + diaryNo
          );
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
        $(jsonObj.t.top5DiariesByWritingTime).each(function (index, item) {
          console.log(jsonObj.t.top5DiariesByWritingTime);
          let $copyObj = $writingTimeOriginalObj.clone();
          $.ajax({
            url: "http://localhost:9997/back/imagedownload",
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
            success: function (responseData) {
              let url = URL.createObjectURL(responseData);
              let $imgObj = $copyObj.find("img");
              $imgObj.attr("src", url);
              $imgObj.attr("alt", "다이어리이미지");
            },
            error: function (jqXHR) {
              //응답실패
              alert("이미지 다운로드 에러:" + jqXHR.status);
            },
          });
          let diaryNo = item.diaryNo;
          $diaryUrl = $copyObj.find("a.diary-url");
          $diaryUrl.attr(
            "href",
            "http://localhost/front/html/view_diary.html?diaryNo=" + diaryNo
          );
          $diaryTitleObj = $copyObj.find("div.diary-title");
          $diaryTitleObj.html(item.diaryTitle);
          $("div.carousel-inner.writing-time").append($copyObj);
          $copyObj.removeClass("active write").addClass("copy");
        });
      }
    },
    error: function (jqXHR) {},
  });
  return false;
});
