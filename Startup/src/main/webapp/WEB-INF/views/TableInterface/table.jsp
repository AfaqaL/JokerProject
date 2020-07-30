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
<body onload="update('${sessionScope.gameMode}')">
<div class="player1" id = "p1">
    <p>${sessionScope.usernames.get(0)}</p>
</div>
<div class="player2" id="p2">
    <p>${sessionScope.usernames.get(1)}</p>
</div>
<div class="player3" id="p3">
    <p>${sessionScope.usernames.get(2)}</p>
</div>
<div class="player4" id="p4">
    <p>${sessionScope.usernames.get(3)}</p>
</div>

<div class="container">
    <div class="midTable" id="midTable"></div>
    <div class="btn-group-horizontal" style="display: none;" id="sayNum"></div>
    <div class="btn-group-horizontal" style="display: none" id="sup-btn-group"></div>
    <div class="btn-group-horizontal" style="display: none" id="joker-activated"></div>
    <div class="joker-first-wrapper" id="joker-first-wrapper" style="display: none">
        <div class="joker-first-labels" id="joker-first-labels"></div>
        <div class="joker-first" id="first-player-joker">
            <div class="btn-group-horizontal" id="high-card"></div>
            <div class="btn-group-horizontal" id="low-card"></div>
        </div>
    </div>
</div>

<div class="hand" id="hand"></div>

<table id="pointGrid">
    <thead>
    <tr>
        <th></th>
        <th>${sessionScope.usernamesGridSeq.get(0)}</th>
        <th></th>
        <th>${sessionScope.usernamesGridSeq.get(1)}</th>
        <th></th>
        <th>${sessionScope.usernamesGridSeq.get(2)}</th>
        <th></th>
        <th>${sessionScope.usernamesGridSeq.get(3)}</th>
    </tr>
    </thead>
</table>


<div class="superior_card" id="superior_card"></div>

</body>
</html>
