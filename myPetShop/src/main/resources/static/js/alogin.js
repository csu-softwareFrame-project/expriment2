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

function usernameIsExist() {
    var username = document.getElementById("username").value;
    if(username == ""){
        document.getElementById("usernameMsg").innerHTML = "<font color='red'><i class='fa fa-ban'></i>用户名不能为空</font>";
        isUsernameExist=true;
    }
    else {
        sendRequest("alogin?username=" + username);
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
        PasswordNull=false;
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
    createXMLHttpRequest();
    xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.onreadystatechange = processResponse;
    xmlHttpRequest.send(null);
}

function processResponse() {
    if (xmlHttpRequest.readyState == 4) {
        if (xmlHttpRequest.status == 200) {
            var responseInfo = xmlHttpRequest.responseXML.
            getElementsByTagName("msg")[0].firstChild.data;

            var div1 = document.getElementById("usernameMsg");

            if (responseInfo == "Exist") {
                div1.innerHTML = "<font color='red'><i class='fa fa-ban'></i>用户名已存在</font>";
                isUsernameExist=true;
            } else if(responseInfo == "NotExist"){
                div1.innerHTML = "<font color='green'><i class='fa fa-check-circle'></i>用户名可用</font>";
                isUsernameExist=false;
            }
            else{
                div1.innerHTML = "";
            }
        }
    }
}

