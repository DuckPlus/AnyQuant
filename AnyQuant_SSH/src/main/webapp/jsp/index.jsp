<%@ page contentType="text/html;charset=UTF-8" language="java" %><!Doctype = html>
<html>

<head>
    <style>

    </style>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link rel="stylesheet" type="text/css" href="css/buttons.css">
    <%--tab上的小图标--%>
    <link rel="shortcut icon" href="/image/shoot_cut.png">
</head>

<body id="login_back">
<h1>
    Login Page
</h1>
<a href="index" >asdsad</a>

<form action="/login" method="post">

    <fieldset>
        <label >Username:</label>
        <input id="username" name="username" type="text" required><br>
        <label >Password:</label>
        <input id="password" name="password" type="password"  required><br>
        <label>Add a new user:</label>
        <input id="addNewUser" name="addNewUser" type="checkbox"><br>
    </fieldset>
    <input type="submit" value="send">


    <a href="/json">All users</a>
    <script src="../js/login.js"></script>


</form>
<button class="button button-raised button-primary button-pill" onclick="location.href='/jsp/StockList.jsp'">直接进入</button>
<button class="button button-raised button-primary button-pill" onclick="location.href='/jsp/testPage.jsp'">test</button>
</body>

</html>