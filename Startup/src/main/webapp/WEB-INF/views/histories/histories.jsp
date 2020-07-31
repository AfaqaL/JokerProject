<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tatena
  Date: 7/28/2020
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Joker | Histories</title>
    <script src="${pageContext.request.contextPath}/resources/JS/histories.js" type="text/javascript"></script>

    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>


    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel = "stylesheet" type = "text/css">
    <link href="<c:url value="/resources/CSS/histories.css" />" rel="stylesheet">
</head>
<body onload="fetchData()">
    <nav class="navbar navbar-fixed-top black-gradients">
        <div class="container" style="position: relative">
            <label class = "header"> თამაშების ისტორია </label>
            <button onclick="window.location='/waiting-room'" class="btn btn-dark"> უკან დაბრუნება </button>
        </div>
    </nav>
    <div id = "userGames" class="card-deck">

    </div>
</body>
</html>
