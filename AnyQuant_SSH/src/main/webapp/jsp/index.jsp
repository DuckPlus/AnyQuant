<%@ page contentType="text/html;charset=UTF-8" language="java" %><!Doctype = html>
<html>

<head>
    <style>

    </style>

    <link rel="stylesheet" type="text/css" href="css/buttons.css">
    <%--tab上的小图标--%>
    <link rel="shortcut icon" href="/image/shoot_cut.png">

    <script src="dist/js/flat-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/flat-ui.css">

    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>

<body>
<div id="huge_front">
    <h1 style="font-family: 'Kozuka Gothic Pro'">
        Duck quantitative transaction
    </h1>
    <%--<a href="index" >asdsad</a>--%>
    <%--<img id="login_back" src="/image/frontPage.jpg">--%>
    <form action="/login" method="post" >
         <br><br><br><br>
        <fieldset id="loginModule" style="margin-left: auto;margin-right: auto;">
            <%--<label >Username:</label>--%>
            <input id="username" name="username" type="text" placeholder="username" class="form-control" required><br>
            <%--<label >Password:</label>--%>
            <input id="password" name="password" type="password" placeholder="password" class="form-control" required><br>
            <label>添加账户</label>
            <input id="addNewUser" name="addNewUser" type="checkbox" class="checkbox"><br>

            <input type="submit" value="登录" class="button button-raised button-primary button-pill">
            <a href="/json">所有账户</a>
        </fieldset>

        <script src="../js/login.js"></script>


    </form>
    <!--<button class="button button-raised button-royal" onclick="location.href='/jsp/StockList_d.jsp'">股票列表</button>-->

    <br>
    <button class="button button-action button-rounded" onclick="location.href='../html/StockList.html'"style="text-align: center">主界面~</button>
    <button class="button button-action button-rounded" onclick="location.href='../html/BoardDetail.html'"style="margin-left: auto;margin-right: auto">板块详情</button>


</div>

<button class="button button-raised button-primary button-pill" onclick="location.href='/html/duck_main.html'">test</button>
</body>

</html>