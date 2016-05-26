<%@ page contentType="text/html;charset=UTF-8" language="java" %><!Doctype = html>
<html>

<head>
    <style>

    </style>

    <%--<link rel="stylesheet" type="text/css" href="css/buttons.css">--%>
    <%--tab上的小图标--%>
    <link rel="shortcut icon" href="/image/short_cut.png">

    <%--<script src="dist/js/flat-ui.min.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="/css/flat-ui.css">--%>
    <link rel="stylesheet" href="/css/flat_button.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
</head>

<body>
    <h1 style="font-family: 'Kozuka Gothic Pro'" class="home_title">
        Duck Quantitative Transaction
    </h1>
    <%--<a href="index" >asdsad</a>--%>
    <%--<img id="login_back" src="/image/frontPage.jpg">--%>
    <form action="/login" method="post" autocomplete="off">
         <br><br><br><br>
        <fieldset id="loginModule" style="margin-left: auto;margin-right: auto;">
            <%--<label >Username:</label>--%>
            <div id="account_with_icon">
                <img src="/image/login_white.png" alt="ACC" id="account_icon">
                <input id="username" name="username" type="text" placeholder="username" class="flat_input_text" required>
            </div>
            <%--<label >Password:</label>--%>
            <div id="password_with_icon">
                <img src="/image/password_white.png" alt="PAS" id="password_icon">
                <input id="password" name="password" type="password" placeholder="password" class="flat_input_text" required>
            </div>

            <%--<label>添加账户</label>--%>
            <%--<input id="addNewUser" name="addNewUser" type="checkbox" class="checkbox"><br>--%>
            <div id="login_getUser">
                <input type="submit" value="登录" class="flat_button_future flat_button_future_rounded">
                <button class="flat_button_future flat_button_future_rounded" location.href="/json">所有账户(test)</button>
            </div>
            <%--<a href="/json">所有账户</a>--%>
        </fieldset>

        <script src="../js/login.js"></script>


    </form>
    <!--<button class="button button-raised button-royal" onclick="location.href='/jsp/StockList_d.jsp'">股票列表</button>-->
    <%--<div id="start_button_div">--%>
        <%--<button class="flat_button_future" id="start_btn" onclick="location.href='/html/duck_main.html'" style="text-align: center">GET START</button>--%>
    <%--</div>--%>
    <div id="start_btn" class="flat_button_future" onclick="location.href='/html/duck_main.html'"><label id="start_btn_text">GET START</label></div>

</body>

</html>