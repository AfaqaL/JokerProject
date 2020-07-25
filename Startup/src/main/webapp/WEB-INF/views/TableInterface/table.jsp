<%--
  Created by IntelliJ IDEA.
  User: gross_master
  Date: 25.07.20
  Time: 01:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Table Interface</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <link href="<c:url value="/resources/CSS/Table/hand.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/Suit.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/Card.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/CSS/Table/PlayerLabels.css" />" rel="stylesheet">
</head>
<body>

<div class="hand">
    <c:forEach items="${sessionScope.hand}" var="card">
        <c:choose>
            <c:when test="${card.color == 0}">
                <div class="card suitclubs">
                    <p>${card.value}</p>
                </div>
            </c:when>
            <c:when test="${card.color == 1}">
                <div class="card suitdiamonds">
                    <p>${card.value}</p>
                </div>
            </c:when>
            <c:when test="${card.color == 2}">
                <div class="card suitspades">
                    <p>${card.value}</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="card suithearts">
                    <p>${card.value}</p>
                </div>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<div class = "PLayerLabels">
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
</div>
</body>
</html>
