<%--
  Created by IntelliJ IDEA.
  User: levan
  Date: 1/22/2021
  Time: 1:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title> Hello WebSocket </title>
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="/app.js"></script>
</head>
<body>
    <button type="button" <%--class="btn btn-success" data-dismiss="modal"--%> onclick = "myconnect()">connect</button>
    <button type="button" <%--class="btn btn-success" data-dismiss="modal"--%> onclick = "mydisconnect()">disconnect</button>
    <button type="button" <%--class="btn btn-success" data-dismiss="modal"--%> onclick = "mysend()">send</button>
</body>
</html>
