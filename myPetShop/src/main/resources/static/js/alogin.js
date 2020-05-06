var xmlHttpRequest;
var isUsernameExist=true;
var PasswordNull=true;
var PasswordTheSame=true;


function createXMLHttpRequest()
{
    if (window.XMLHttpRequest) //非IE浏览器
    {
        xmlHttpRequest = new XMLHttpRequest();
    }
    else if (window.ActiveObject)//IE6以上版本的IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Msxml2.XMLHTTP");
    }
    else //IE6及以下版本IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Microsoft.XMLHTTP");
    }
}

function notChange() {
    passwordUpdate = false;
    var block = document.getElementById("changePasswordButton");
    var block2 = document.getElementById("changePassword");
    block.innerHTML = "<label>password: &nbsp;</label><a href='#' onclick=\"changePassword()\">change password</a>"
    block2.innerHTML =""
}

function changePassword() {
    // alert("a")
    passwordUpdate = true;
    var block1 = document.getElementById("changePasswordButton");
    var block2 = document.getElementById("changePassword");
    block1.innerHTML = ""
    block2.innerHTML = "<label for=\"password\">New password: &nbsp;</label><input type=\"password\" name=\"password\" id=\"password\" onblur=\"isPasswordNull();\"/>\n" +
        "            <label id=\"usernameMsg2\"></label>\n" +
        "            <div class=\"cleaner h10\"></div>\n" +
        "\n" +
        "            <label for=\"repeatedPassword\">Repeat password: &nbsp;</label><input type=\"password\" name=\"repeatedPassword\" id=\"repeatedPassword\" onblur=\"isPasswordTheSame();\"/>\n" +
        "            <label id=\"usernameMsg3\"></label>\n" +
        "            <div class=\"cleaner h10\"></div>\n" +
        "            <div id=\"notChangeButton\"><a href='#' onclick='notChange()'>I don't want to change.</a></div>"
}

function usernameIsExist() {
    var username = document.getElementById("username").value;
    if(username == ""){
        document.getElementById("usernameMsg").innerHTML = "<font color='red'><i class='fa fa-ban'></i>用户名不能为空</font>";
        isUsernameExist=true;
    }
    else {
        sendRequest("/useraccess/alogin?username=" + username);
    }
}

function isPasswordNull() {
    var newPassword = document.getElementById("password").value;
    var password = document.getElementById("repeatedPassword").value;
    if(newPassword == ""){
        document.getElementById("usernameMsg2").innerHTML = "<font color='red'><i class='fa fa-ban'></i>新密码不能为空</font>";
        PasswordNull=true;
    }
    else if(newPassword == password){
        document.getElementById("usernameMsg2").innerHTML = "<font color='green'><i class='fa fa-check-circle'></i>正确</font>";
        document.getElementById("usernameMsg3").innerHTML = "<font color='green'><i class='fa fa-check-circle'></i>正确</font>";
        PasswordNull=false;
    }
    else if (password != ""){
        document.getElementById("usernameMsg3").innerHTML = "<font color='red'><i class='fa fa-ban'></i>密码两次输入不相同</font>";
    }
    else {
        document.getElementById("usernameMsg2").innerHTML = "";
    }
}

function isPasswordTheSame() {
    var newPassword = document.getElementById("password").value;
    var password = document.getElementById("repeatedPassword").value;
    if(newPassword == ""){
        document.getElementById("usernameMsg2").innerHTML = "<font color='red'><i class='fa fa-ban'></i>新密码不能为空</font>";
        PasswordNull=true;
    }
    else if(newPassword != password){
        document.getElementById("usernameMsg3").innerHTML = "<font color='red'><i class='fa fa-ban'></i>密码两次输入不相同</font>";
        PasswordTheSame=true;
    }
    else{
        document.getElementById("usernameMsg2").innerHTML = "<font color='green'><i class='fa fa-check-circle'></i>正确</font>";
        document.getElementById("usernameMsg3").innerHTML = "<font color='green'><i class='fa fa-check-circle'></i>正确</font>";
        PasswordTheSame=false;
        PasswordNull=false;
    }
}

function sendRequest(url) {
    $.ajax({
        type: "get",
        url: url,    //向后端请求数据的url
        success: function (data) {
            var div1 = document.getElementById("usernameMsg");
            if (data == "Exist") {
                div1.innerHTML = "<font color='red'><i class='fa fa-ban'></i>用户名已存在</font>";
                isUsernameExist=true;
            } else if(data == "NotExist"){
                div1.innerHTML = "<font color='green'><i class='fa fa-check-circle'></i>用户名可用</font>";
                isUsernameExist=false;
            }
            else{
                div1.innerHTML = "";
            }
        }
    });
}



