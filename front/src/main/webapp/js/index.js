const routes = $(".tour-route");
const all_dates = $(".tour-date");
let draggableRoute = null;

routes.on("dragstart", dragStart);
routes.on("dragend", dragEnd);

function dragStart() {
    draggableRoute = this;
    setTimeout(() => {
      this.style.display = "none";
    }, 0);
  }
  
  function dragEnd() {
    draggableRoute = null;
    setTimeout(() => {
      this.style.display = "block";
    }, 0);
  }

  all_dates.on("dragover", dragOver);
  all_dates.on("dragenter", dragEnter);
  all_dates.on("dragleave", dragLeave);
  all_dates.on("drop", dragDrop);



function dragOver(e) {
    // console.log("dragOver");
    e.preventDefault();
}
function dragEnter(){
    this.style.border = "1px dashed #ccc"
    console.log("dragEnter");
}
function dragLeave(){
    this.style.border = "none"
    console.log("dragLeave");
}

function dragDrop(){
    this.style.border = "none"
    this.appendChild(draggableRoute);
    console.log("dragDrop");
}


/* modal */


// const btns = document.querySelectorAll("[data-target-modal]");
// const close_modals = document.querySelectorAll(".close-modal");
// const overlay = document.querySelector("overlay");


// btns.forEach((btn) => {
//   btn.addEventListener("click", () => {
//     document.querySelector(btn.dataset.targetModal).classList.add("active");
//     overlay.classList.add("active");
//   });
// });

// close_modals.forEach((btn) => {
//   btn.addEventListener("click", () => {
//     const modal = btn.closest(".modal");
//     modal.classList.remove("active");
//     overlay.classList.remove("active");
//   });
// });



const btns = $("[data-target-modal]");
const modals =$('.modal');
const overlay = $("overlay");
const close_modals = $(".close-modal");


btns.on('click',function(){
  modals.toggleClass("active")
  overlay.toggleClass("active");
});
close_modals.on('click', function(){
  $(this).closest(".modal").removeClass("active");
  overlay.toggleClass("active");
})

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
var inputJS = document.querySelector('#route_pic');
var input = $(inputJS);
var preview = $('.preview');
preview.css({'height': '200px', 'line-height': '100px'});
input.change(updateImageDisplay);
function updateImageDisplay() {
  preview.empty();
  const curFiles = inputJS.files;
  if(curFiles.length === 0) {
    const para = $('<p>');
    para.textContent = 'No files currently selected for upload';
    preview.append(para);
  } else {
    // const list = $('<ol>');
    // preview.append(list);


    for(const file of curFiles) {
      const para = document.createElement('span');
      if(validFileType(file)) {
        const wrapper = $('<div>');
        wrapper.addClass('.thumbnail-wrappper');
        wrapper.css({'width':'193.5px' , 'float':'left'});
        const thumbnail = $('<div>');
        thumbnail.addClass('thumbnail');
        thumbnail.css({'height' : '100%','border': '1px solid'});
        const image = $('<img>');
        image.attr("src",URL.createObjectURL(file));
        image.css({'width':'100%','height':'177px'});
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

const fileTypes = [
  "image/jpeg",
  "image/png",
];
// file type check
function validFileType(file) {
  return fileTypes.includes(file.type);
}


/* create route  */
const route_submit = document.getElementById("route_submit");

route_submit.addEventListener("click", createRoute);

function createRoute() {
  const route_div = document.createElement("div");
  const input_val = document.getElementById("route_name").value;
  const txt = document.createTextNode(input_val);

  route_div.appendChild(txt);
  route_div.classList.add("tour-route");
  route_div.setAttribute("draggable", "true");

  /* create span */
  const span = document.createElement("span");
  const span_txt = document.createTextNode("\u00D7");
  span.classList.add("close");
  span.appendChild(span_txt);

  route_div.appendChild(span);

  tour_first.appendChild(route_div);

  span.addEventListener("click", () => {
    span.parentElement.style.display = "none";
  });

  route_div.addEventListener("dragstart", dragStart);
  route_div.addEventListener("dragend", dragEnd);

  document.getElementById("route_name").value = "";
  route_form.classList.remove("active");
  overlay.classList.remove("active");
}

const close_btns = document.querySelectorAll(".close");

close_btns.forEach((btn) => {
  btn.addEventListener("click", () => {
    btn.parentElement.style.display = "none";
  });
});