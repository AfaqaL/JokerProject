<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Joker | Login</title>
</head>
<body>

    <h1>Welcome!</h1>

    <form method="post">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username">

        <br/><br/>


        <label for="password">Password:</label>
        <input type="password" name="password" id="password">

        <br/><br/>

        <input type="submit" value="Log In">
    </form>
    <a href="/register" > registration </a>
    <br/><br/>
    <a href="/forgetPassword"> forget password </a>

</body>
</html>