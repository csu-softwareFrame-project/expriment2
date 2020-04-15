var names;
$(document).ready(function () {
    $.ajax({
        type:"POST",
        contentType: "application/json",
        url:"json",
        dataType: "json",
        success:function (data) {
            names=data;
            autocompleteFn(data);
        }
    });
});

function autocompleteFn(names) {
    $("#tags").autocomplete({source:names})
}
