/**
 * Created by duanzhengmou on 6/16/16.
 */

$.ajax({
   url:'/user/getUserName', 
   type:'get',
   success:function (data) {
       document.getElementById("navigator_user_name").innerHTML = data;
   },
   error:function () {
       alert("网络不畅通");
   }
});