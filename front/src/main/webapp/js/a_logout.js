$(() => {
  let url = `${backPath}/admin/logout`;
  $.ajax({
    url: url,
    method: "GET",

    success: () => {
      location.href = "a_index.html";
    },

    error: (jqXHR) => {
      alert(jqXHR.responseJSON.message);
    },
  });
  return false;
});
