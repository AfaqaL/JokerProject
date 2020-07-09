<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>New Password</title>
    </head>
    <body>
    <c:if test="${error != null}">
                Authentication failed: ${error}
            </c:if>
        <form method="POST">
            <label for="New password">Enter New Password.</label><br/><br/>
            <label for="newPass">New Password:</label>
            <input type="password" name="pass" id = "passID" /><br/><br/>
            <label for="confirmPass">Confirm Password:</label>
            <input type="password" name="confirmedPass" /><br/><br/>
            <input type="checkbox" onclick="showPassword()">Show Password
            <button type="submit">Login</button>
        </form>
        <script type="text/javascript">
            function showPassword() {
              var x = document.getElementById("passID");
              if (x.type == "password") {
                x.type = "text";
              } else {
                x.type = "password";
              }
            }
            </script>
    </body>
</html>