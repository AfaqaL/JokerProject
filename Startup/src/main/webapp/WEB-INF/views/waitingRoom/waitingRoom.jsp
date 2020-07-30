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
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>


</head>
<body onload="fetchData()">
    <h1> The ყომარბაზი </h1>
    <div id="existingTables1">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <!DOCTYPE html>

        <head>
            <%--    <meta name="viewport" content="width = device-width, initial scale=1.0">--%>
            <meta http-equiv="X-UA-Compatible" content="ie=edge">
            <title>smth</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script><title> waiting </title>
            <script src="${pageContext.request.contextPath}/resources/JS/waitingRoomFunctional.js" type="text/javascript"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

            <!-- jQuery library -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

            <!-- Popper JS -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

            <!-- Latest compiled JavaScript -->
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

            <link href="<c:url value="/resources/CSS/waitingRoom.css" />" rel="stylesheet">

        </head>

        <body  onload="fetchData()" >
        <nav class="navbar navbar-fixed-top black-gradients">
            <div class="container" style="position: relative">
                <label> The ყომარბაზი </label>
                <%--        <div class="avatar-menu">--%>
                <%--            <div class="dropdown">--%>
                <%--                <div class="image-shadow dropdown-toggle" type="button" data-toggle="dropdown">--%>
                <%--                    <img class="img-circle user-avatar" ng-src="https://graph.facebook.com/1240524699485740/picture?type=square&amp;height=200" alt="jokerstars_joker_avatar" src="https://graph.facebook.com/1240524699485740/picture?type=square&amp;height=200">--%>
                <%--                </div>--%>

                <%--            </div>--%>
                <a class="logOut" href="/login"> გამოსვლა </a>
                <%--        </div>--%>
            </div>
        </nav>
        <!-- Modal -->
        <div class="modal fade" id="create" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div id = "createTableDiv" >
                                <label for="bayonetValue">ხიშტი: </label>
                                <input type = "number" id = "bayonetValue"> <br>
                                <label for="passwordValue">პაროლი: </label>
                                <input type = "text" id = "passwordValue"> <br>
                                <label for="tableType">აირჩიე თამაშის ტიპი: </label> <br>
                                <div id="tableType">
                                    <input type="radio" name="gameMode" id="standard"> სტანდარტული<br>
                                    <input type="radio" name="gameMode" id="nines"> ცხრიანები <br>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick = "createTable()">Create Table</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="jumbotron">
                <div class="card">

                    <div class="card-body">
                        <h5 class="card-title">მაგიდები</h5>

                        <div class="row">
                            <div class="col-md-12 text-right">
                                <button type="button" class="btn btn-warning badge-pill " data-toggle="modal" data-target="#create" id = "createTable">მაგიდის მასპინძლობა</button>
                            </div>
                        </div>
                        <table id="existingTables" class="table table-bordered table-dark">
                            <thead>
                            <tr>
                                <th scope="col">მასპინძელი</th>
                                <th scope="col">ხიშტი</th>
                                <th scope="col">ტიპი</th>
                                <th scope="col">პაროლი</th>
                                <th scope="col">მომხმარებლები</th>
                                <th>შებრძანება</th>
                            </tr>
                            </thead>
                            <tbody id="rooms">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        </body>

</html>