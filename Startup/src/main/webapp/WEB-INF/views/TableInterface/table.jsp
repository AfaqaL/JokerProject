<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
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
<div class="player1" data-letters="A">
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
    <div class="btn-group" style="display: block" id = "sayNum">
        <% for (int i = 0; i < 10; i++) { %>
        <button onclick = "removeSayNum()"><%=i%></button>
        <% } %>
    </div>
</div>

<div class="hand" id="hand"></div>

<table>
    <tr>
        <th></th>
        <th>${sessionScope.usernames.get(0)}</th>
        <th class="num"></th>
        <th>${sessionScope.usernames.get(1)}</th>
        <th class="num"></th>
        <th>${sessionScope.usernames.get(2)}</th>
        <th class="num"></th>
        <th>${sessionScope.usernames.get(3)}</th>
    </tr>
    <% for (int i = 0; i < 5; i++) { %>
    <tr>
        <td>1</td>
        <td class="red">100</td>
        <td>-</td>
        <td class="green">0</td>
        <td>-</td>
        <td>10</td>
        <td>-</td>
        <td class="last">20</td>
    </tr>
    <% } %>
    <tr class="final_points">
        <td></td>
        <td>25.2</td>
        <td></td>
        <td>20</td>
        <td></td>
        <td>40</td>
        <td></td>
        <td class="last">20</td>
    </tr>
    <tr class="final_points last">
        <td></td>
        <td>25.2</td>
        <td></td>
        <td>20</td>
        <td></td>
        <td>40</td>
        <td></td>
        <td class="last">20</td>
    </tr>
</table>
<div class="superior_card" id="superior_card">
    <figure>
        <img src="${pageContext.request.contextPath}/resources/images/6C.png" alt="superior_card">
        <figcaption>კოზირი: ბეზი</figcaption>
    </figure>
</div>

</body>
</html>
