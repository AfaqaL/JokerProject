<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Joker | Password Recovery</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    <link href="<c:url value="/resources/CSS/passwordRecovery.css" />" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/JS/showPassword.js"></script>
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h5>შეიყვანეთ ახალი პაროლი</h5>
            </div>
            <form method="POST">
                <div class="input-group form-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"><i class="fas fa-key"></i></span>
                    </div>
                    <label for="passID"></label><input type="password" class="form-control" name="pass" id="passID"
                                                       placeholder="ახალი პაროლი">
                    <span class="p-viewer">
                        <i class="fa fa-eye" aria-hidden='true' onclick="showPasswordForTwo(this)"></i>
                    </span>
                </div>
                <div class="input-group form-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"><i class="fas fa-key"></i></span>
                    </div>
                    <label for="confPassID"></label><input type="password" class="form-control" name="confirmedPass"
                                                           id="confPassID"
                                                           placeholder="გაიმეორეთ პაროლი">
                </div>
                <div class="form-group pass_show">
                    <input type="submit" value="დადასტურება" class="btn float-right confirm_btn">
                </div>
            </form>
            <div class="card-footer">
                <div class="d-flex justify-content-center">
                    <c:if test="${incorrectPassword != null}">
                        <h5>პაროლი არასწორია</h5>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>