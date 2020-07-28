<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width">
    <title>Table Interface</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="<c:url value="/resources/JS/game.js" />"></script>
    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <link href="<c:url value="/resources/CSS/Table/Table.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/PlayerLabels.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/PointGrid.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/Card.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/hand.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/MiddleTable.css" />" rel="stylesheet">
</head>
<body onload="update()">
<div class="player1">
    <p>${sessionScope.usernames.get(0)}</p>
</div>
<div class="player2">
    <p>${sessionScope.usernames.get(1)}</p>
</div>
<div class="player3">
    <p>${sessionScope.usernames.get(2)}</p>
</div>
<div class="player4">
    <p>${sessionScope.usernames.get(3)}</p>
</div>

<div class="container">
    <div class="midTable" id="midTable"></div>
    <div class="btn-group" style="display: block; visibility: hidden" id = "sayNum">
        <% for (int i = 0; i < 10; i++) { %>
        <button onclick = "removeSayNum()"><%=i%></button>
        <% } %>
    </div>
    <div class="btn-group sup" style="display: none" id = "sup-btn-group">
        <button class = "club">♣</button>
        <button class = "diamond" style="color: red">♦</button>
        <button class = "spade">♠</button>
        <button class = "heart" style="color: red">♥</button>
        <button class = "nothing">J</button>
    </div>
</div>

<div class="hand" id="hand"></div>

<table id = "pointGrid" onclick="extendTable()">
    <thead>
    <tr>
        <th></th>
        <th>user_A</th>
        <th></th>
        <th>user_B</th>
        <th></th>
        <th>user_C</th>
        <th></th>
        <th>user_D</th>
    </tr>
    </thead>
</table>


<div class="superior_card" id="superior_card"></div>

</body>
</html>
