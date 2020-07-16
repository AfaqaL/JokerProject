<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 7/13/2020
  Time: 3:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Joker | Home</title>
<%--    <script><%@include file="../../../resources/JS/waitingRoomFunctional.js"%></script>--%>
    <script src="${pageContext.request.contextPath}/resources/JS/waitingRoomFunctional.js" type="text/javascript"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body onload="fetchData()">
    <h1> The ყომარბაზი </h1>
    <div id="existingTables">
        <!-- created tables will be added here! -->

<%--        <button onclick="enterTable()" id="enter">Enter Table</button> <br><br><br>--%>
    </div> <br>


    <div id = "createTableDiv" style="float:right">
        <label for="bayonetValue">ხიშტი: </label>
        <input type = "number" id = "bayonetValue"> <br>
        <label for="passwordValue">პაროლი: </label>
        <input type = "text" id = "passwordValue"> <br>
        <label for="tableType">აირჩიე თამაშის ტიპი: </label> <br>
        <div id="tableType">
            <input type="radio" name="gameMode" id="standard"> სტანდარტული<br>
            <input type="radio" name="gameMode" id="nines"> ცხრიანები <br>
        </div>
        <button onclick="createTable()" id="create">Create Table</button>
    </div>



</body>
</html>
