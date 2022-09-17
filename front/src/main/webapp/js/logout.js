$(() => {
  let url = `${backPath}/client/logout`;
  $.ajax({
    url: url,
    method: "GET",

    success: () => {
      location.href = "index.html";
    },

    error: (jqXHR) => {
      alert(jqXHR.responseJSON.message);
    },
  });
  return false;
});
