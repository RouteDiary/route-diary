$(function () {
  let url = "http://localhost:8888/back/diaryboard";
  let currenturl = window.location.search;

  let viewStatus = 2;
  let currentPage = currenturl.split("=")[1];
  let keyword = "";
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
    data: {
      view_status: viewStatus,
      current_page: currentPage,
      keyword: keyword,
    },
    success: (jsonObj) => {
      console.log(jsonObj);
      // 페이징 시작
      totalRows = jsonObj.totalRows;
      console.log("totalRows = " + totalRows);
      totalPage = Math.floor(totalRows / 10);
      if (totalRows % 10 > 0) {
        totalPage++;
      }
      console.log("totalPage = " + totalPage);
      currentPage = jsonObj.currentPage;
      console.log("currentPage = " + currentPage);
      let startPageListMath = Math.floor(currentPage / (viewPage + 0.1)) + 1;
      console.log("startPageListMath = " + startPageListMath);
      startPageNo = (startPageListMath - 1) * viewPage + 1;
      console.log("startPageNo = " + startPageNo);
      endPage = totalPage;
      console.log("endPage = " + endPage);
      let totalPageMath = Math.floor(totalPage / (viewPage + 0.1)) + 1;
      console.log("totalPageMath = " + totalPageMath);

      // 아래의 로직을 보고 다시 코드를 구상해보세요
      // Totalpage수 = 8
      // 경우1: 1<=currentpage<=3 : 맨앞으로(1) /1~3까지 보여주기 / 다음3개(4~6)로 / 맨뒤로(8)
      // 경우2: 4<=currentpage<=6 : 맨앞으로 (1) / 이전3개(1~3)로 / 4~6까지 보여주기 / 다음3개(7~9)로 / 맨뒤로(8)
      // 경우3: 7<=currentpage<=8 : 맨앞으로 (1) / 이전3개(4~6)로 / 7~8까지 보여주기 / 맨뒤로(8)

      if (totalPage < viewPage + 1) {
        let $paging = $(".paging");
        $paging.empty();

        for (let i = 1; i < totalPage; i++) {
          console.log(i);
          let a = "";
          a += "<a href=" + "?current_page=" + i + ">" + i + "</a> ";
          $paging.append(a);
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

        $paging.append(a);

        for (let i = startPageNo; i <= totalPage; i++) {
          let a = "";
          a += "<a href=" + "?current_page=" + i + ">" + i + "</a> ";
          $paging.append(a);
        }

        $paging.append(a);
      } else if (totalPage > viewPage) {
        let $paging = $(".paging");
        $paging.empty();
        if (startPageListMath != 1) {
          let a = "";
          a +=
            "<a href=" + "diaryboard.html" + " >" + "<<(맨처음으로)" + "</a> ";
          $paging.append(a);
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

      $.each(jsonObj.diaries, (key, value) => {
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
    error: (jqXHR) => {
      alert("에러 : " + jqXHR.status);
    },
  });
});
