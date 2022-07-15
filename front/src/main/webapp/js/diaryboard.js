$(function () {
  let url = "http://localhost:8888/back/diaryboard";
  let currenturl = window.location.search;
  let viewStatus = 1;
  // $(document).ready(function () {
  $(".btn-primary").on("click", function () {
    //버튼 클릭시 호출되는 함수
    $(this).addClass("active"); //클릭된 버튼에 속성(class 추가)
    console.log($(this).val()); //클릭된 버튼의 value 값  alert 띄우기
    viewStatus = $(this).val(); //클릭된 버튼의 value 값  alert 띄우기
    // console.log(viewStatus);
    test(viewStatus);
  });
  // });

  // $(".btn-primary").click(function () {
  //   var value = $(arg0).val();
  //   console.log(value);
  // });

  // let viewStatus = 1;

  // console.log(viewStatus);

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
  function test(viewStatus) {
    let currentPage = currenturl.split("=")[1];
    let keyword = "";
    let totalRows;
    let countPerPage = 10;
    let pageCount = 2;
    let startPageNo;
    let endPageNo;
    let totalPage;
    let viewPage = 3;
    let startPageListMath = Math.floor(currentPage / (viewPage + 0.1)) + 1;
    alert(viewStatus);
    $.ajax({
      url: url,
      method: "get",
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
        let endPageNo = startPageNo + viewPage - 1;
        console.log("endPage = " + endPageNo);
        let totalPageListMath = Math.floor(totalPage / (viewPage + 0.1)) + 1;
        console.log("totalPageMath = " + totalPageListMath);

        // 아래의 로직을 보고 다시 코드를 구상해보세요
        // Totalpage수 = 8
        // 경우1: 1<=currentpage<=3 : 맨앞으로(1) /1~3까지 보여주기 / 다음3개(4~6)로 / 맨뒤로(8)
        // 경우2: 4<=currentpage<=6 : 맨앞으로 (1) / 이전3개(1~3)로 / 4~6까지 보여주기 / 다음3개(7~9)로 / 맨뒤로(8)
        // 경우3: 7<=currentpage<=8 : 맨앞으로 (1) / 이전3개(4~6)로 / 7~8까지 보여주기 / 맨뒤로(8)
        let $paging = $(".paging");
        $paging.empty();

        if (currentPage != 1) {
          // console.log(currentPage);
          let a = "";
          a +=
            "<a href=" + "diaryboard.html" + " >" + "<<(맨처음으로)" + "</a> ";
          $paging.append(a);
        }

        if (startPageListMath != 1) {
          let a = "";
          a +=
            "<a href=" +
            "?current_page=" +
            (startPageNo - 1) +
            " >" +
            "<(이전리스트로)" +
            "</a> ";
          $paging.append(a);
        }
        if (endPageNo < totalPage) {
          for (let i = startPageNo; i <= endPageNo; i++) {
            let a = "";
            a += "<a href=" + "?current_page=" + i + ">" + i + "</a> ";
            $paging.append(a);
          }
        } else {
          for (let i = startPageNo; i <= totalPage; i++) {
            let a = "";
            a += "<a href=" + "?current_page=" + i + ">" + i + "</a> ";
            $paging.append(a);
          }
        }

        if (startPageListMath < totalPageListMath) {
          let a = "";
          a +=
            "<a href=" +
            "?current_page=" +
            (startPageNo + viewPage) +
            " >" +
            ">(다음리스트로)" +
            "</a> ";
          $paging.append(a);
        }
        if (currentPage < totalPage) {
          let a = "";
          a +=
            "<a href=" +
            "?current_page=" +
            totalPage +
            " >" +
            ">>(맨마지막으로)" +
            "</a> ";
          $paging.append(a);
        }

        // 페이징 끝
        $("#diary_list").empty();
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
  }
  test(1);
});
