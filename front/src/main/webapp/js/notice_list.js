$(() => {
  function showNotice(url) {
    let $notice = $("tr.notice_tr").first();
    $notice.show();

    $.ajax({
      url: url,
      method: "get",
      dataType: "json",
      success: function (jsonObj) {
        if (jsonObj.status == 200) {
          console.log(jsonObj);
          let pageBeanObj = jsonObj.t;
          let noticeObj = jsonObj.t.posts;
          $("tr.notice_tr").not($notice).empty();

          let $noticeParent = $notice.parent();

          let noticeModifyingTime = noticeObj.noticeModifyingTime;
          $(noticeObj).each((index, notices) => {
            let $noticeCopy = $notice.clone(); //복제본
            let noticeWritingTime = notices.noticeWritingTime;

            let noticeWritingDate = new Date(noticeWritingTime);
            noticeWritingDate = noticeWritingDate.toLocaleDateString();

            $noticeCopy.on("click", function () {
              location.href = "./view_notice.html?noticeNo=" + notices.noticeNo;
            });
            $noticeCopy.find("th.notice_th").html(notices.noticeNo);
            $noticeCopy.find("td.notice_td1").html(notices.noticeTitle);
            $noticeCopy.find("td.notice_td2").html(notices.adminId);
            $noticeCopy.find("td.notice_td3").html(notices.noticeViewCnt);
            $noticeCopy.find("td.notice_td4").html(noticeWritingDate);
            // $noticeCopy.find("td.notice_td5").html(notices.noticeModifyingTime);
            $noticeParent.append($noticeCopy);
          });
          $notice.hide(); // 원본 숨기기

          // 페이징 처리 시작

          let totalPage = pageBeanObj.totalPage;
          let $pagegroup = $("div.pagegroup");
          let $pagegroupHtml = "";

          if (pageBeanObj.startPage > 1) {
            $pagegroupHtml += '<span class="first">FIRST</span>';
            $pagegroupHtml += "&nbsp;&nbsp;";
            $pagegroupHtml += '<span class="prev">PREV</span>';
          }
          for (let i = pageBeanObj.startPage; i <= pageBeanObj.endPage; i++) {
            $pagegroupHtml += "&nbsp;&nbsp;";
            if (pageBeanObj.currentPage == i) {
              $pagegroupHtml += '<span class="disabled">' + i + "</span>";
            } else {
              $pagegroupHtml += "<span>" + i + "</span>";
            }
          }
          if (pageBeanObj.endPage < pageBeanObj.totalPage) {
            $pagegroupHtml += "&nbsp;&nbsp;";
            $pagegroupHtml += '<span class="next">NEXT</span>';
          }
          if (pageBeanObj.startPage < pageBeanObj.totalPage) {
            $pagegroupHtml += "&nbsp;&nbsp;";
            $pagegroupHtml += '<span class="end">END</span>';
            $pagegroupHtml +=
              '<span id="last1" style="display:none">' + totalPage + "</span>";
          }
          $pagegroup.html($pagegroupHtml);
        } else {
          alert("성공:" + jsonObj.message);
        }
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
  }
  showNotice("http://localhost:9997/back/notice/list/1");

  $("div.pagegroup").on("click", "span:not(.disabled)", function () {
    let pageNo = 1;
    let totalPage = $("#last1").html();
    if ($(this).hasClass("prev")) {
      pageNo = parseInt($(this).next().html()) - 1;
      console.log("prev:" + pageNo);
    } else if ($(this).hasClass("next")) {
      pageNo = parseInt($(this).prev().html()) + 1;
      console.log("next:" + pageNo);
    } else if ($(this).hasClass("first")) {
      pageNo = 1;
      console.log("first:" + pageNo);
    } else if ($(this).hasClass("end")) {
      pageNo = totalPage;
      console.log(pageNo);
    } else {
      pageNo = parseInt($(this).html());
      console.log(pageNo);
    }

    let keyword = $("input[name=keyword]").val().trim();
    console.log("keyword:" + keyword);
    let url = "";
    // if (keyword == "") {
    url = "http://localhost:9997/back/notice/list/" + pageNo;
    // } else {
    // url = "http://localhost:9997/back/notice/list/" + keyword + "/" + pageNo;
    // }
    showNotice(url);
    return false;
  });
  //페이징 처리 끝
  $("div.search>div.searchInput>a").click(() => {
    let keyword = $("div.search>div.searchInput>input[name=keyword]")
      .val()
      .trim();
    // let url = "http://localhost:9997/back/notice/list" + "/" + keyword + "/1";
    let url = "";
    if (keyword == "") {
      url = "http://localhost:9997/back/notice/list/" + pageNo;
    } else {
      url = "http://localhost:9997/back/notice/list/" + keyword + "/" + 1;
    }
    showNotice(url);
    return false;
  });
  $("#btnSave").click(function () {
    window.location.href = "./a_notice_write.html";
  });
});
