$(document).ready(function () {
    $("#listOption").click(function () {
        var myList = $("#listOption").is(':checked');
        $.cookie("box1",myList);
    });
    $("#bannerOption").click(function () {
        var myBanner=$("#bannerOption").is(':checked');
        $.cookie("box2",myBanner);
    });
});


