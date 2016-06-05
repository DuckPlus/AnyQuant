/**
 * Created by duanzhengmou on 6/1/16.
 */

/**
 * 检查是否登录,并依结果做出相应跳转
 */
function handle_Login(){
    $.ajax({
       url:'/user/checkIfLogin',
       type:'get',
        success:function (data) {
            if(data){
                //do nothing
            }else{
                window.location.href = '/jsp/index.jsp';
            }
        }        
    });
    
}