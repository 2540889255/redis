
var webSocket=null;
//判断当前电脑是否支持websocket
/*if ('websocket' in window){
    //创建服务器websocket对象，连接服务器端点
    webSocket=new WebSocket("ws://localhost:8086/ws");
    alert("你成功连接上了")
}else {
    alert("not support websocket")
}*/
if (typeof WebSocket != 'undefined') {
    console.log("您的浏览器支持Websocket通信协议")
    webSocket=new WebSocket("ws://localhost:8086/ws");
} else {
    alert("您的浏览器不支持Websocket通信协议，请使用Chrome或者Firefox浏览器！")
}
//连接发生错误时的回调方法
webSocket.onerror = function () {
    appendMessage("error");
};

//连接成功建立的时候回调方法
webSocket.onopen = function (event) {
    appendMessage("open");
}

//接收到消息的回调方法
webSocket.onmessage = function (event) {
    appendMessage(event.data)
}

//连接关闭的方法
webSocket.onclose = function () {
    appendMessage("close")
}

//监听窗口关闭事件 当窗口关闭的时候也 主动关闭websocket
//防止连接还断开 就关闭窗口 server都4端会报错
window.onbeforeunload = function () {
    webSocket.close();
}

//将消息显示在网页上
function appendMessage(message) {
    var context =$("#context").html() +"<br/>" +message;
    $("#context").html(context)
}

//关闭连接
function closeWebSocket() {
    webSocket.close();
}
//发送消息
function sendMessage() {
    var message=$("#message").val();
    webSocket.send(message);
    alert("你正在发送消息");
}
