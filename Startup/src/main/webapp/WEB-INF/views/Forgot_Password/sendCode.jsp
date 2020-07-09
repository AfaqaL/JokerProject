<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Send Code</title>
    </head>
    <body>
        <label for="New password">We will send code on mail: ${mail}</label><br/><br/>
        <form action = "/forgot_password/verifyCode" method="POST">
            <button type="submit">Send Code</button>
        </form>
    </body>
</html>