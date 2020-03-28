var isShow=true;
function change() {
    var password=document.getElementById("password");
    var eyes=document.getElementById("eyes");
    if (isShow){
        password.type="text";
        isShow=false;
        eyes.src="images/eye1.png";
    }
    else {
        password.type="password";
        isShow=true;
        eyes.src="images/eye2.png";
    }
}
