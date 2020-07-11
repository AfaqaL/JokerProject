<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Joker | Verify Code</title>
</head>
<body>
    <h1>Mail sent to ${sessionScope.mail}</h1>
    <form method="post">
        <label for="code">Code:</label>
        <input type="text" placeholder="Enter code" name="code" id="code">
        <br/><br/>
        <input type="submit" value="Validate code">
    </form>
</body>
</html>