document.getElementById('popWin').style.display="none";
function dianwo(e){
    document.getElementById('popWin').style.display="";
    alert(e.currentTarget.id)
    var accountList = "<%=session.getAttribute('accountList')%>";
    alert(accountList)
}
function hidder(){
    document.getElementById('popWin').style.display="none";
}