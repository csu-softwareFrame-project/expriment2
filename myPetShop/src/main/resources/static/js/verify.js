function verify() {
    // 获取img元素
    // 为了让浏览器发送请求到servlet, 所以一定要改变src
    document.getElementById("btn").src =
        "verifyCode?time=" + new Date().getTime();
};