<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Joker | Password Recovery</title>
</head>
<body>
<c:if test="${incorrectPassword != null}">
    Authentication failed: ${incorrectPassword}
</c:if>
<form method="POST">
    <label for="New password">Enter New Password.</label><br/><br/>
    <label for="newPass">New Password:</label>
    <input type="password" name="pass" id = "passID" /><br/><br/>
    <label for="confirmPass">Confirm Password:</label>
    <input type="password" name="confirmedPass" id = "confPassID" /><br/><br/>
    <input type="checkbox" onclick="showPassword()">Show Password
    <button type="submit">Login</button>
</form>
<script type="text/javascript">
    function showPassword() {
        var x = document.getElementById("passID");
        var y = document.getElementById("confPassID");
        if (x.type == "password") {
            x.type = "text";
            y.type = "text";
        } else {
            x.type = "password";
            y.type = "password";
        }
    }
</script>
</body>
</html>