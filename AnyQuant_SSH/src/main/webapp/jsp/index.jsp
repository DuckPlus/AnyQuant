<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
           <script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
</head>

<body>
    <div id="top_div">

        <img src="/image/login/screen.png" id="screen_big">
        <img src="/image/login/duckhead.png" id="screen_small">
        <img src="/image/login/pots.png" id="inside">
        <h1 id="title" style="font-family: 'PingFang SC';font-weight: 100;font-size: 45px">
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
                <button class="flat_button_future flat_button_future_rounded" id="signup_btn" onclick={hide_login('loginModule');show_login('signupModule');}>注册</button>
                <button class="flat_button_future flat_button_future_rounded" id="start_btn"onclick="location.href='/html/duck_main.html'">快速进入</button>

        </div>
        <div id="signupModule">
            <%--<form action="">--%>
                <input type="text" name = "username" placeholder="请输入用户名" required class="sign_input" id="input_user_name">
                <input type="password" name = "password" placeholder="请设置密码" required class="sign_input" id="input_pass_word">
                <input type="password" name = "password_again" placeholder="请再次输入密码" required class="sign_input" id="input_pass_word_again">
                <button id="signup_confirm_btn" onclick=sign_up();>注册</button>
                <button id="back_btn" onclick={hide_login('signupModule');show_login('loginModule');}>返回</button>
                <div id="tip"></div>
            <%--</form>--%>

        </div>

    </div>
        <%--<button onclick=hide_login('loginModule')>hide login</button>--%>
        <%--<button onclick=show_login('loginModule')>show login</button>--%>
        <%--<button onclick=show_login('signupModule')>show signup</button>--%>
        <%--<button onclick=hide_login('signupModule')>hide signup</button>--%>
    <script>
        function hide_login(id) {
            document.getElementById(id).style.opacity = 0;
            setTimeout(function () {
                document.getElementById(id).style.visibility = "hidden";
            },500);
        }
        function show_login(id) {
            document.getElementById(id).style.opacity = 1;
                document.getElementById(id).style.visibility = "visible";
        }
        function sign_up() {
            var user_name = document.getElementById('input_user_name').value;
            var pass_word = document.getElementById('input_pass_word').value;
            var pass_word_again = document.getElementById('input_pass_word_again').value;

            if(pass_word==""||user_name==""||pass_word_again==""){
                document.getElementById("tip").style.color = "#D0021B";
                document.getElementById("tip").innerHTML = "就这么点信息你都不填满?╮(╯▽╰)╭";
            }
            else if (pass_word != pass_word_again) {
                document.getElementById("tip").style.color = "#D0021B";
                document.getElementById("tip").innerHTML = "注册失败:两次输入密码不一致";
            } else {
                $.ajax({
                    type: 'post',
                    url: '/user/register',
                    data: {userName: user_name, password: pass_word},
                    success: function (statu) {
                        if (statu) {
                            document.getElementById("tip").style.color = "#7ED321";
                            document.getElementById("tip").innerHTML = "╰(￣▽￣)╮注册成功";
                            document.getElementById('input_user_name').value = "";
                            document.getElementById('input_pass_word').value = "";
                            document.getElementById('input_pass_word_again').value = "";
                        } else {
                            document.getElementById("tip").style.color = "#D0021B";
                            document.getElementById("tip").innerHTML = "┏ (゜ω゜)=☞用户已存在";
                        }
                    },
                    error: function (statu) {
                        document.getElementById("tip").style.color = "#D0021B";
                        document.getElementById("tip").innerHTML = "未知错误";
                    }
                });
            }
        }
    </script>
</body>

</html>