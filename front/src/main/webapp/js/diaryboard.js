$(function () {
  let url = "http://localhost:8888/back/diaryboard";
  let viewStatus = 2;

  let currenturl = window.location.search;
  let currentPage = currenturl.split("=")[1];
  function dateTimeStrartFormat(dateTimeValue) {
    //시작날짜형식 바꿔주기
    var dt = new Date(dateTimeValue);
    var dateTimeStrartFormat =
      dt.getFullYear() +
      "년" +
      (dt.getMonth() + 1) +
      "월" +
      dt.getDate() +
      "일";
    return dateTimeStrartFormat;
  }
  function dateTimeEndFormat(dateTimeValue) {
    //끝날짜형식 바꿔주기
    var dt = new Date(dateTimeValue);
    var dateTimeEndFormat = dt.getDate() + "일";
    return dateTimeEndFormat;
  }
  let totalRows;
  let countPerPage = 10;
  let pageCount = 2;
  let startPageNo;
  let endPage;
  let totalPage;
  let viewPage = 3;
  let startPageListMath = Math.floor(currentPage / (viewPage + 0.1)) + 1;
  $.ajax({
    url: url,
    method: "post",
    data: { view_status: viewStatus, current_page: currentPage },
    success: function (jsonObj) {
      console.log(jsonObj);
      // 페이징 시작
      totalRows = jsonObj.totalRows;
      totalPage = Math.floor(totalRows / 10);
      if (totalRows % 10 > 0) {
        totalPage++;
      }
      currentPage = jsonObj.currentPage;
      let startPageListMath = Math.floor(currentPage / (viewPage + 0.1)) + 1;
      startPageNo = (startPageListMath - 1) * viewPage + 1;
      endPage = totalPage;
      let totalPageMath = Math.floor(totalPage / (viewPage + 0.1)) + 1;

      if (totalPage < viewPage + 1) {
        let $paging = $(".paging");
        $paging.empty();

        for (let i = startPageNo; i < totalPage; i++) {
          console.log(i);
          let a = "";
          a += "<a href=" + "?current_page=" + i + ">" + i + "</a> ";
          $($paging).append(a);
        }
      } else if (totalPageMath == startPageListMath) {
        let $paging = $(".paging");

        $paging.empty();
        let a = "";

        a += "<a href=" + "diaryboard.html" + " >" + "<<(맨처음으로)" + "</a> ";
        a +=
          "<a href=" +
          "?current_page=" +
          (startPageNo - 1) +
          " >" +
          "&nbsp;<(하나뒤로)</a> ";

        $($paging).append(a);

        for (let i = startPageNo; i <= totalPage; i++) {
          let a = "";
          a += "<a href=" + "?current_page=" + i + ">" + i + "</a> ";
          $($paging).append(a);
        }

        $($paging).append(a);
      } else if (totalPage > viewPage) {
        let $paging = $(".paging");
        $paging.empty();
        if (startPageListMath != 1) {
          let a = "";
          a +=
            "<a href=" + "diaryboard.html" + " >" + "<<(맨처음으로)" + "</a> ";
          $($paging).append(a);
          a =
            "<a href=" +
            "?current_page=" +
            (currentPage + 1) +
            " >" +
            ">(한개다음)" +
            "</a> ";
        }
      }
      //     for (let i = 0; i < startPageNo + 2; i++) {
      //       let a = "";
      //       a +=
      //         "<a href=" + "?current_page=" + (i + 1) + ">" + (i + 1) + "</a> ";
      //       $($paging).append(a);
      //     }
      //     let a = "";
      //     // a += "<a href=" + "?current_page=" + (startPageNo + 3) + " >" + "</a> ";
      //     a +=
      //       "<a href=" +
      //       "?current_page=" +
      //       endPage +
      //       " >" +
      //       ">>(맨뒤로)" +
      //       "</a> ";

      //     $($paging).append(a);
      //   }

      // 페이징 끝

      $.each(jsonObj.diaries, function (key, value) {
        result = "<tr>";
        result += "<td>" + "이미지" + "</td>";
        result += "<td>" + value.diaryViewCnt + "</td>";
        result += "<td>" + value.client.clientNickname + "</td>";
        result += "<td>" + value.diaryTitle + "</td>";
        result +=
          "<td>" +
          dateTimeStrartFormat(value.diaryStartDate) +
          "~" +
          dateTimeEndFormat(value.diaryEndDate) +
          "</td>";
        result += "<td>" + value.diaryLikeCnt + "개" + "</td>";
        result += "</tr>";
        // console.log("[main] : [value] : " + value.diaryViewCnt);
        // console.log("[main] : [value] : " + value.client.clientNickname);
        // console.log("[main] : [value] : " + value.diaryTitle);
        // console.log("[main] : [value] : " + value.diaryStartDate);
        // console.log("[main] : [value] : " + value.diaryEndDate);
        // console.log("[main] : [value] : " + value.diaryLikeCnt);
        $("#diary_list").append(result);
      });
    },
    error: function (jqXHR) {},
  });
});
