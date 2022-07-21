const routes = $(".tour-route");

const all_dates = $(".tour-date");
let draggableRoute = null;

// routes.on("dragstart", dragStart);
all_dates.on("dragstart", ".tour-route", dragStart);
all_dates.on("dragend", ".tour-route", dragEnd);

// routes.on("dragend", dragEnd);

function dragStart() {
  draggableRoute = $(this);
  setTimeout(() => {
    $(this).css("display", "none");
  }, 0);
}

function dragEnd() {
  draggableRoute = null;
  setTimeout(() => {
    $(this).css("display", "flex");
  }, 0);
}

all_dates.on("dragover", dragOver);
all_dates.on("dragenter", dragEnter);
all_dates.on("dragleave", dragLeave);
all_dates.on("drop", dragDrop);

function dragOver(e) {
  console.log("dragOver");
  e.preventDefault();
}
function dragEnter() {
  $(this).css("border", "1px dashed #ccc");
  console.log("dragEnter");
}
function dragLeave() {
  $(this).css("border", "none");
  console.log("dragLeave");
}

function dragDrop() {
  $(this).css("border", "none");
  $(this).append(draggableRoute);
  console.log("dragDrop");
}

const btns = $("[data-target-modal]");
const modals = $(".modal");
const overlay = $("#overlay");
const close_modals = $(".close-modal");
//route insert start
btns.on("click", () => {
  modals.toggleClass("active");
  overlay.toggleClass("active");
  modals.find("#route_name").val("");
  modals.find("#route_content").val("");
  modals.find("#route_pic").val("");
});
close_modals.on("click", (event) => {
  var $btn = $(event.currentTarget);
  $btn.closest(".modal").removeClass("active");
  overlay.toggleClass("active");
});

window.onclick = (event) => {
  if (event.target == overlay) {
    // const modals = $(".modal");
    // modals.on(() => {
    //   modals.removeClass("active");
    // })
    const modal = document.querySelector(".modal");
    modals.forEach((modal) => modal.classList.remove("active"));
    overlay.classList.remove("active");
  }
};
/* create image preview */
var inputJS = document.querySelector("#route_pic");
var input = $(inputJS);
var preview = $(".preview");
preview.css({ height: "200px", "line-height": "100px" });
input.change(updateImageDisplay);
function updateImageDisplay() {
  preview.empty();
  const curFiles = inputJS.files;
  if (curFiles.length === 0) {
    const para = $("<p>");
    para.textContent = "No files currently selected for upload";
    preview.append(para);
  } else {
    // const list = $('<ol>');
    // preview.append(list);

    for (const file of curFiles) {
      const para = $("<span>");
      if (validFileType(file)) {
        const wrapper = $("<div>");
        wrapper.addClass(".thumbnail-wrappper");
        wrapper.css({ width: "193.5px", float: "left" });
        const thumbnail = $("<div>");
        thumbnail.addClass("thumbnail");
        thumbnail.css({ height: "100%", border: "1px solid" });
        const image = $("<img>");
        image.attr("src", URL.createObjectURL(file));
        image.css({ width: "100%", height: "177px" });
        thumbnail.append(image);
        wrapper.append(thumbnail);
        wrapper.append(para);
        // list.append(wrapper);
        preview.append(wrapper);
      } else {
        para.textContent = `File name ${file.name}: Not a valid file type. Update your selection.`;
        // list.append(para);
      }

      // list.appendChild(listItem);
    }
  }
}

const fileTypes = ["image/jpeg", "image/png"];
// file type check
function validFileType(file) {
  return fileTypes.includes(file.type);
}

/* create route  */
const $route_submit = $("#route_submit");

$route_submit.on("click", createRoute);

function createRoute() {
  $tour_name_input = $("#route_name");
  name_val = $tour_name_input.val();
  $tour_content_input = $("#route_content");
  content_val = $tour_content_input.val();
  //// if문으로 val들이 다 비어있으면 입력 아니면 수정으로 짜야할듯?
  $tour_pic = $("#route_pic");
  pic_val = $tour_pic.val();

  const routeString = `<div class="tour-route" draggable="true">
  <span class="name_txt" autocomplete="off">${name_val} </span>
  <span class="tour-content" style="visibility : hidden" autocomplete="off">${content_val}</span>
  <span class="tour-image" style="visibility : hidden"></span>
  <span class="close">&times;</span>
</div>`;

  $route = $($.parseHTML(routeString));
  console.log($route);
  $route.find(".close").on("click", () => {
    $route.remove();
    console.log("close");
  });

  const $tour_first = $("#tour_first");
  $tour_first.append($route);

  $route.find("div").on("dragstart", dragStart);
  $route.find("div").on("dragend", dragEnd);

  let $route_form = $("#route_form");

  $route_form.removeClass("active");
  overlay.removeClass("active");
}

$close_btns = $(".close");
$close_btns.on("click", (event) => {
  var $btn_close = $(event.currentTarget);
  $btn_close.parent(".tour-route").remove();
});

all_dates.on("click", ".tour-route", function (event) {
  // appear modal
  modals.toggleClass("active");
  overlay.toggleClass("active");
  // input into modal this data
  // console.log($(this).find(".name_txt").text());
  console.log($(event.target));
  console.log($(this));
  modals.find("#route_name").val($(event.target).find(".name_txt").text());
  // console.log($(event.target).find(".name_txt").text());
  console.log(modals.find("#route_name"));
  modals
    .find("#route_content")
    .val($(event.target).find(".tour-content").text());
});

// function showTourInfo(event) {

//   // modals.children("#route_name").text($(this).children(".tour-route").text());
// }
