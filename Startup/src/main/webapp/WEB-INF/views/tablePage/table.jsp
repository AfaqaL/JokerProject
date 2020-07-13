<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 7/13/2020
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Joker | Table</title>
</head>
<body>
    <h1> This should be 4 player game </h1>
    <div style="alignment: center; color: blueviolet" id="main-content" class="container">
        <div class="players">
            <div class="player1">
                <label style="align-content: center">Player1:</label>
                <% for (int i = 0; i < 9; i++) { %>
                    <button id="card<%=i%>" class="btn btn-default" type="submit">Card<%=i + 1%></button>
                <%}%>
                <br>
            </div>
            <div class="player2">
            <label>Player2:</label>
            <% for (int i = 9; i < 18; i++) { %>
            <button id="card<%=i%>" class="btn btn-default" type="submit">Card<%=i + 1%></button>
            <%}%>
            <br>
        </div>
            <div class="player3">
                <label>Player3:</label>
                <% for (int i = 18; i < 27; i++) { %>
                <button id="card<%=i%>" type="submit" onclick="func(<%=i + 1%>)">Card<%=i + 1%></button>
                <script>
                    function func(i){
                        console.log("Player 3 pressed on card" + i);
                        let currCard = document.getElementById("card" + (i-1));
                        currCard.remove();
                        document.getElementById("moves").append("card" + i + "<br>");
                    }
                </script>
                <%}%>
                <br>
            </div>
            <div class="player4">
                <label>Player4:</label>
                <% for (int i = 27; i < 36; i++) { %>
                <button id="card<%=i%>" class="btn btn-default" type="submit">Card<%=i + 1%></button>
                <%}%>
                <br>
            </div>
        </div>
        <div class="moved">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>Moves made:</th>
                </tr>
                </thead>
                <tbody id="moves">
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
