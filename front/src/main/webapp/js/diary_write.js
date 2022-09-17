$(() => {
  //-----카카오 맵 지도 생성 관련 코드-----
  // 마커를 담을 배열입니다
  var markers = [];

  var mapContainer = document.getElementById("map"), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };

  // 지도를 생성합니다
  var map = new kakao.maps.Map(mapContainer, mapOption);

  // 장소 검색 객체를 생성합니다
  var ps = new kakao.maps.services.Places();

  // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
  var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

  // 키워드 검색을 요청하는 함수입니다
  function searchPlaces() {
    var keyword = document.getElementById("keyword").value;

    if (!keyword.replace(/^\s+|\s+$/g, "")) {
      alert("키워드를 입력해주세요!");
      return false;
    }
    ps.keywordSearch(keyword, placesSearchCB);

    var places = new kakao.maps.services.Places();

    var callback = function (result, status) {
      if (status === kakao.maps.services.Status.OK) {
        console.log(result);
      }
    };

    places.keywordSearch(keyword, callback);
  }

  // 여행지 검색을 하는 함수 입니다.
  let btClick = document.querySelector("#search>button");
  btClick.addEventListener("click", function () {
    let returnValue = searchPlaces();
    return false;
  });

  // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
  function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      // 정상적으로 검색이 완료됐으면
      // 검색 목록과 마커를 표출합니다
      displayPlaces(data);

      // 페이지 번호를 표출합니다
      displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
      alert("검색 결과가 존재하지 않습니다.");
      return;
    } else if (status === kakao.maps.services.Status.ERROR) {
      alert("검색 결과 중 오류가 발생했습니다.");
      return;
    }
  }

  // 검색 결과 목록과 마커를 표출하는 함수입니다
  function displayPlaces(places) {
    var listEl = document.getElementById("placesList"),
      menuEl = document.getElementById("menu_wrap"),
      fragment = document.createDocumentFragment(),
      bounds = new kakao.maps.LatLngBounds(),
      listStr = "";

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for (var i = 0; i < places.length; i++) {
      // 마커를 생성하고 지도에 표시합니다
      var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
        marker = addMarker(placePosition, i),
        itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(placePosition);

      // 마커와 검색결과 항목에 mouseover 했을때
      // 해당 장소에 인포윈도우에 장소명을 표시합니다
      // mouseout 했을 때는 인포윈도우를 닫습니다
      ((marker, title) => {
        kakao.maps.event.addListener(marker, "mouseover", () => {
          displayInfowindow(marker, title);
        });

        kakao.maps.event.addListener(marker, "mouseout", () => {
          infowindow.close();
        });

        itemEl.onmouseover = () => {
          displayInfowindow(marker, title);
        };

        itemEl.onmouseout = () => {
          infowindow.close();
        };
      })(marker, places[i].place_name);

      fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
  }

  // 검색결과 항목을 Element로 반환하는 함수입니다
  function getListItem(index, places) {
    var el = document.createElement("li"),
      itemStr =
        '<span class="markerbg marker_' +
        (index + 1) +
        '"></span>' +
        '<div class="info">' +
        "   <h5>" +
        places.place_name +
        "</h5>";

    if (places.road_address_name) {
      itemStr +=
        "    <span>" +
        places.road_address_name +
        "</span>" +
        '   <span class="jibun gray">' +
        places.address_name +
        "</span>";
    } else {
      itemStr += "    <span>" + places.address_name + "</span>";
    }

    itemStr +=
      '  <span class="tel">' +
      places.phone +
      "</span>" +
      '  장소 ID : <span class="kakao-map-id-value">' +
      places.id +
      "</span>";

    el.innerHTML = itemStr;
    el.className = "item";
    el.setAttribute("draggable", "true");

    return el;
  }

  // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
  function addMarker(position, idx, title) {
    var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
      imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
      imgOptions = {
        spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
        spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
        offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
      },
      markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
      marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage,
      });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker); // 배열에 생성된 마커를 추가합니다

    return marker;
  }

  // 지도 위에 표시되고 있는 마커를 모두 제거합니다
  function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  }

  // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
  function displayPagination(pagination) {
    var paginationEl = document.getElementById("pagination"),
      fragment = document.createDocumentFragment(),
      i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
      paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
      var el = document.createElement("a");
      el.href = "#";
      el.innerHTML = i;

      if (i === pagination.current) {
        el.className = "on";
      } else {
        el.onclick = ((i) => {
          return () => {
            pagination.gotoPage(i);
          };
        })(i);
      }

      fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
  }

  // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
  // 인포윈도우에 장소명을 표시합니다
  function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

    infowindow.setContent(content);
    infowindow.open(map, marker);
  }

  // 검색결과 목록의 자식 Element를 제거하는 함수입니다
  function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
      el.removeChild(el.lastChild);
    }
  }

  //----다이어리 작성 관련 코드----
  //-----업로드할 이미지 보여주기----
  function showImage(event) {
    let $galary = event.data.$galary;
    $galary.empty();
    $(this.files).each((index, file) => {
      let $imgObj = $("<img>");
      let blobURL = URL.createObjectURL(file);
      $imgObj.attr("src", blobURL);
      $imgObj.attr("alt", file.name);
      $imgObj.css("max-width", "50px");
      $galary.append($imgObj);
    });
  }

  //이미지 업로드할때 업로드할 이미지 보여주기
  let $divImage = $("div.images");
  $("input.custom-file-input.files").change({ $galary: $divImage }, showImage);

  //drag and drop 기능 구현
  $("#placesList").on("dragstart", "li.item", (ev) => {
    dragStart(ev);
  });
  $("div.accordion").on("dragover", "div.kakao-map-id", (ev) => {
    $(ev.target).empty();
    allowDrop(ev);
  });

  $("div.accordion").on("drop", "div.kakao-map-id", (ev) => {
    drop(ev);
  });

  function allowDrop(ev) {
    ev.preventDefault();
  }

  function dragStart(ev) {
    ev.originalEvent.dataTransfer.setData("obj", $(ev.target).html());
  }

  function drop(ev) {
    ev.preventDefault();
    var data = ev.originalEvent.dataTransfer.getData("obj");
    $(ev.target).html(data);
  }

  //----루트 작성 폼 추가-----(한번에 여러개씩 추가됨)
  let $routeFormObj = $("div.card");
  $("button.btn.btn-primary.float-right").click(() => {
    let $copiedRouteFormObj = $routeFormObj.clone();
    let closeTag =
      '<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    $copiedRouteFormObj.find("span.btn-close").html(closeTag);
    $copiedRouteFormObj.find("div.kakao-map-id").empty();
    $copiedRouteFormObj
      .find("div.kakao-map-id")
      .html("검색한 여행지 주소를 여기에 drag & drop 해주세요.");
    $copiedRouteFormObj.find("textarea.form-control.route-content").empty();
    $("div.accordion").append($copiedRouteFormObj);
  });
  //루트 작성 폼 삭제
  $("div.accordion").on("click", "button.close", (e) => {
    console.log(e.target);
    console.log(e.currentTarget);
    $(e.target).parent().parent().parent().remove();
  });

  //----해시태그 관련 코드----
  let hashtags = document.querySelector("input.hashtags");
  let tagify = new Tagify(hashtags); // initialize Tagify
  console.log("tagify : ");
  console.log(tagify);

  //날짜형식 변경함수
  function getFormatDate(date) {
    var d = new Date(date),
      month = "" + (d.getMonth() + 1),
      day = "" + d.getDate(),
      year = d.getFullYear();
    if (month.length < 2) month = "0" + month;
    if (day.length < 2) day = "0" + day;
    return [year, month, day].join("/");
  }

  //-----ajax 요청-----
  $("button.btn.btn-secondary.write").click(() => {
    //----루트내용 가져오기----
    let diaryTitle = $("div.page-item>input.form-control.diary-title").val();
    let oldDiaryStartDate = $(
      "div.page-item>input.form-control.diary-start-date"
    ).val();
    let diaryStartDate = getFormatDate(oldDiaryStartDate);
    let oldDiaryEndDate = $(
      "div.page-item>input.form-control.diary-end-date"
    ).val();
    let diaryEndDate = getFormatDate(oldDiaryEndDate);
    console.log("diaryDate : " + diaryStartDate + " : " + diaryEndDate);
    let diaryDisclosureFlag = $(
      "input[name=diary-disclosure-flag]:checked"
    ).val();
    //----- ajax 요청용 formData만들기
    let formData = new FormData();
    // diary 제출용 json객체 만들기
    let data = new Object();
    data.diaryTitle = diaryTitle;
    data.diaryStartDate = diaryStartDate;
    data.diaryEndDate = diaryEndDate;
    data.diaryDisclosureFlag = diaryDisclosureFlag;
    let routesArr = new Array();
    $("div.card").each((index, item) => {
      var routes = new Object();
      routes.routeContent = $(item)
        .find("textarea.form-control.route-content")
        .val();
      routes.kakaoMapId = $(item)
        .find("div.kakao-map-id span.kakao-map-id-value")
        .html();
      routesArr.push(routes);
    });
    data.routes = routesArr;
    let hashtagsArr = new Array();
    $(tagify.value).each((index, item) => {
      var hashtags = new Object();
      hashtags.hashtag = item.value;
      hashtagsArr.push(hashtags);
    });

    data.hashtags = hashtagsArr;
    let diary = JSON.stringify(data);
    formData.append("diary", diary);
    //이미지 파일
    var fileInput = $("input.custom-file-input.files");
    for (var i = 0; i < fileInput.length; i++) {
      if (fileInput[i].files.length > 0) {
        for (var j = 0; j < fileInput[i].files.length; j++) {
          formData.append(
            "imageFiles",
            $("input.custom-file-input.files")[i].files[j]
          );
        }
      }
    }
    console.log("---formData---");
    console.log(formData.get("diary"));
    console.log(formData.getAll("imageFiles"));
    formData.forEach((value, key) => {
      console.log(key + ":" + value);
    });
    $.ajax({
      url: `${backPath}/diary/write`,
      method: "post",
      contentType: false,
      processData: false,
      enctype: "multipart/form-data",
      data: formData,
      success: (jsonObj) => {
        console.log(jsonObj.message);
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
    return false;
  });
});
