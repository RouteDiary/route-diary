$(() => {
  function showNotice(url) {
    let $notice = $("tr.notice_tr").first();
    $notice.show();

    $.ajax({
      url: url,
      method: "get",
      dataType: "json",
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

          $noticeCopy.on("click", () => {
            location.href = "./a_view_notice.html?noticeNo=" + notices.noticeNo;
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
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
  }
  showNotice(`${backPath}/notice/list/1`);

  $("div.pagegroup").on("click", "span:not(.disabled)", (e) => {
    // let pageNo = 1;
    let pageNo = $(e.target).html();
    let totalPage = $("#last1").html();
    if ($(e.target).hasClass("prev")) {
      pageNo = parseInt($(e.target).next().html()) - 1;
      console.log("prev:" + pageNo);
    } else if ($(e.target).hasClass("next")) {
      pageNo = parseInt($(e.target).prev().html()) + 1;
      console.log("next:" + pageNo);
    } else if ($(e.target).hasClass("first")) {
      pageNo = 1;
      console.log("first:" + pageNo);
    } else if ($(e.target).hasClass("end")) {
      pageNo = totalPage;
      console.log(pageNo);
    } else {
      pageNo = parseInt($(e.target).html());
      console.log(pageNo);
    }

    let keyword = $("input[name=keyword]").val().trim();
    console.log("keyword:" + keyword);
    let url = "";
    url = `${backPath}/notice/list/` + pageNo;
    showNotice(url);
    return false;
  });
  //페이징 처리 끝
  $("div.search>div.searchInput>a").click(() => {
    let keyword = $("input[name=keyword]").val().trim();
    console.log(keyword);
    let url = "";
    if (keyword == "") {
      url = `${backPath}/notice/list/` + pageNo;
    } else {
      url = `${backPath}/notice/list/` + keyword + "/" + 1;
    }
    showNotice(url);
    return false;
  });
  $("#btnSave").click(() => {
    window.location.href = "./a_notice_write.html";
  });
});
