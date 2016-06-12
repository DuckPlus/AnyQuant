<%@ page contentType="text/html;charset=UTF-8" language="java" %><!Doctype = html>
<html>

<head>
    <style>

    </style>

    <%--tab上的小图标--%>
    <link rel="shortcut icon" href="/image/short_cut.png">
    <link rel="stylesheet" type="text/css" href="/css/login_new.css">
    <script src="../js/login/login.js"></script>
</head>

<body>
    <h1 style="font-family: 'Kozuka Gothic Pro'" class="home_title">
        Duck Quantitative Transaction
    </h1>
    <div id="login_frame">
        <fieldset id="loginModule" style="margin-left: auto;margin-right: auto;">

            <form action="/user/login" method="post" autocomplete="off">
                <div id="user_icon"></div>
            <div id="account_with_icon">
                <img src="/image/login_white.png" alt="ACC" id="account_icon">
                <input id="username" name="username" type="text" placeholder="username" class="flat_input_text" required>
            </div>
            <div id="password_with_icon">
                <img src="/image/password_white.png" alt="PAS" id="password_icon">
                <input id="password" name="password" type="password" placeholder="password" class="flat_input_text" required>
            </div>
                <div id="login_regist">
                    <input type="submit" value="登录" class="flat_button_future flat_button_future_rounded" id="login_btn">
                    <button type="button" id="regist_btn">注册</button>
                </div>

            </form>
        </fieldset>
    </div>

    <%--<div id="start_btn" class="flat_button_future" onclick="location.href='/html/duck_main.html'"><label id="start_btn_text">GET START</label></div>--%>

</body>

</html>