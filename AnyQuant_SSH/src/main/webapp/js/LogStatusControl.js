/**
 * Created by duanzhengmou on 6/1/16.
 */
function isLogin(){
    $.ajax({
       url:'',
       type:'get',
        success:function (data) {
            // alert("data:"+data);
            if(data){
                alert("logged in!");
            }else{
                alert('get the hell log in');
            }
        }        
    });
    
}