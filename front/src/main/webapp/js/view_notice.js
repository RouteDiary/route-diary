$(() => {
  function showNotice(url) {
    $.ajax({
      url: url,
      method: "get",
      dataType: "json",
      success: function (jsonObj) {
        if (jsonObj.status == 200) {
          console.log(jsonObj);
          let pageBeanObj = jsonObj.t;
          let noticeObj = jsonObj.t.posts;
          console.log(noticeObj);
          let $notice = $("tr.notice_tr").first();
          $("tr.notice_tr").not($notice).empty();

          let $noticeParent = $notice.parent();

          $(noticeObj).each((index, notices) => {
            console.log(notices);

            let $noticeCopy = $notice.clone(); //복제본
            // $(selector).attr(attributeName, value);
            // $noticeCopy.$(selector).attr(attributeName, value);
            // $noticeCopy.find("a.a").attr(href, "1");
            // $noticeCopy.html($("mav-1").attr("href", "http://map.google.com"));
            $noticeCopy.find("th.notice_th").html(notices.noticeNo);
            $noticeCopy.find("td.notice_td1").html(notices.noticeTitle);
            $noticeCopy.find("td.notice_td2").html(notices.adminId);
            $noticeCopy.find("td.notice_td3").html(notices.noticeViewCnt);
            $noticeCopy.find("td.notice_td4").html(notices.noticeWritingTime);
            $noticeCopy.find("td.notice_td5").html(notices.noticeModifyingTime);
            $noticeParent.append($noticeCopy);
          });
          let $pagegroup = $("div.pagegroup");
          let $pagegroupHtml = "";
          if (pageBeanObj.startPage > 1) {
            $pagegroupHtml += '<span class="prev">PREV</span>';
          }
          for (let i = pageBeanObj.startPage; i <= pageBeanObj.endPage; i++) {
            $pagegroupHtml += "&nbsp;&nbsp;";
            if (pageBeanObj.currentPage == i) {
              //현재페이지인 경우 <span>태그 안만듦
              // $pagegroupHtml += i;
              $pagegroupHtml += '<span class="disabled">' + i + "</span>";
            } else {
              $pagegroupHtml += "<span>" + i + "</span>";
            }
          }
          if (pageBeanObj.endPage < pageBeanObj.totalPage) {
            $pagegroupHtml += "&nbsp;&nbsp;";
            $pagegroupHtml += '<span class="next">NEXT</span>';
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
});
