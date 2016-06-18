/**
 * Created by duanzhengmou on 6/16/16.
 */

$.ajax({
   url:'/user/getUserName', 
   type:'get',
   success:function (data) {
       if(data==""){
           document.getElementById("navigator_user_name").innerHTML = "登录";
       }
       else {
           document.getElementById("navigator_user_name").innerHTML = data;
       }
   },
   error:function () {
       alert("网络不畅通");
   }
});

function handle_user() {
    $.ajax({
        url:'/user/getUserName',
        type:'get',
        success:function (data) {
            if(data==""){
                window.location.href = "/jsp/index.jsp";
            }
            else {
                alert("别瞎点");
            }
        }
    });
}