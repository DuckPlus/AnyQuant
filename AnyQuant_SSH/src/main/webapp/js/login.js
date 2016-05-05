/**
 * Created by Qiang on 16/5/4.
 */
$("#signup")
    .click(
        function() {
            var username = $("#username").val();
            var password = $("#password").val();
            var passwordTwice = $("#password-second").val();
            if (username == "") {
                $("#message")
                    .html(
                        "<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>用户名不能为空</div>");
                return;
            }
            if (password == "") {
                $("#message")
                    .html(
                        "<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>密码不能为空</div>");
                return;
            }
            if (passwordTwice == "") {
                $("#message")
                    .html(
                        "<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>请再次输入密码</div>");
                return;
            }
            if (password != passwordTwice) {
                $("#message")
                    .html(
                        "<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>输入密码不一致，请再次确认</div>");
                return;
            }
            $("#signup-name").val(username);
            $("#signup-password").val(password);
            $("#user-signup").submit();
        });