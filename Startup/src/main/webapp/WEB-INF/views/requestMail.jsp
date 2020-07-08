<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Enter Mail</title>
    </head>
    <body>
    <c:if test="${error != null}">
                Authentication failed: ${error}
            </c:if>
        <form method="POST">
            <label for="enter mail">Please Enter Mail.</label><br/><br/>
            <label for="enter">Enter Mail:</label>
            <input type="text" name="mail" value = "${mail}" /><br/><br/>
            <button type="submit">Login</button>
        </form>
    </body>
</html>