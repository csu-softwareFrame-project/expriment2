var xmlHttpRequest;

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

function updateQuantity(id,username) {
    createXMLHttpRequest();
    if(username==undefined){
        username=null;
    }
    var input=document.getElementById(id);
    var quantity=input.value;

    var url="updateQuantity?username="+username+"&itemId="+id+"&quantity="+quantity;
    xmlHttpRequest.open("GET",url,true);
    xmlHttpRequest.onreadystatechange = processResponse;
    xmlHttpRequest.send(null);
}
function processResponse() {
    console.log("a"+xmlHttpRequest.readyState);

    if (xmlHttpRequest.readyState == 4) {
        if (xmlHttpRequest.status == 200) {
            var text=xmlHttpRequest.responseText;
            var el=document.getElementById("totalcost");
            console.log("a"+text);
            el.textContent = "$" + text;
        }
        if(xmlHttpRequest.status==404){
            document.getElementById("msg").textContent="error";
        }
    }
}
