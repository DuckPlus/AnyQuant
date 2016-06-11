<%@ page contentType="text/html;charset=UTF-8" language="java" %><!Doctype = html>
<html>

<head>
       <%--<link rel="stylesheet" type="text/css" href="css/buttons.css">--%>
    <%--tab上的小图标--%>
    <link rel="shortcut icon" href="/image/short_cut.png">
    <%--<script src="dist/js/flat-ui.min.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="/css/flat-ui.css">--%>
    <link rel="stylesheet" href="/css/flat_button.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="/css/login/login.css">
           <script src="../js/login/login.js"></script>
</head>

<body>
    <div id="top_div">

        <img src="/image/login/screen.png" id="screen_big">
        <img src="/image/login/duckhead.png" id="screen_small">
        <img src="/image/login/pots.png" id="inside">
        <h1 id="title" style="font-family: 'Kozuka Gothic Pro'">
            <div id="title_top">Duck</div>
             <div>Quantitative Transaction</div>
        </h1>
    </div>
    </div>
    <div id="down_div">
        <div id="loginModule">
            <%--<label >Username:</label>--%>
            <form action="/user/login" method="post" autocomplete="off">
                <div id="account_with_icon">
                    <img src="/image/login_white.png" alt="ACC" id="account_icon">
                    <input id="username" name="username" type="text" placeholder="username" class="flat_input_text" required>
                </div>
                <%--<label >Password:</label>--%>
                <div id="password_with_icon">
                    <img src="/image/password_white.png" alt="PAS" id="password_icon">
                    <input id="password" name="password" type="password" placeholder="password" class="flat_input_text" required>
                </div>

                <input type="submit" value="登录" class="flat_button_future flat_button_future_rounded" id="login_btn">
            <%--<a href="/user/json">所有账户</a>--%>
            </form>
                <input type="submit" value="注册" class="flat_button_future flat_button_future_rounded" id="signup_btn">
                <button class="flat_button_future flat_button_future_rounded" id="start_btn"onclick="location.href='/html/duck_main.html'">快速进入</button>

        </div>

    </div>


</body>

</html>