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
</head>
<body>
    <h1> The ყომარბაზი </h1>
    <div id="existingTables">
        <!-- created tables will be added here! -->
    </div> <br>
    <button onclick="enterTable()" id="enter">Enter Table</button> <br><br><br>

    <div id = "createTableDiv">
        <label>ხიშტი: </label>
        <input type = "number" id = "bayonetValue"> <br>
        <label>პაროლი: </label>
        <input type = "text" id = "passwordValue"> <br>
        <label> აირჩიე თამაშის ტიპი </label> <br>
        <input type="radio" name="gameMode" value="standard"> სტანდარტული <br>
        <input type="radio" name="gameMode" value="nines"> ცხრიანები <br>

    </div>
    <button onclick="createTable()" id="create">Create Table</button>



</body>
</html>
