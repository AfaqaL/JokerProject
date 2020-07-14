<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 7/13/2020
  Time: 3:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>homepage</title>
    <script src="resources/static/waitingRoomFunctional.js" text="text/javascript"></script>
</head>
<body>
    <h1> The ყომარბაზი </h1>
    <div id="existingTables">
        <!-- created tables will be added here! -->
    </div> <br>
    <button onclick="enterTable()" id="enter">Enter Table</button>
    <button onclick="createTable()" id="create">Create Table</button>

</body>
</html>
