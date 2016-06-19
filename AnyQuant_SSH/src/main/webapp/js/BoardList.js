/**
 * Created by dsn on 2016/5/26.
 */
var boards=[];
var globalData;
var ifshowDataLabel=true;
var myRed="#EE2C2C",myGreen="#00CD66",myGrey="#8B7E66"
$.getJSON('/Board/getAllBoardsAndStockData', function (data) {
    globalData=data;
    initBubble();
    $("#loading").remove();
});

function dosearch() {//TODO 板块不存在 即在当前页面警告
    var boardName=document.getElementById("searchCode").value;
    $.getJSON('/Board/checkBoard?'+'board='+boardName, function (data) {
     if(data==true){
         location.href="BoardDetail.html"+"?name="+boardName;
     }
     else {
         document.getElementById('tips_info').innerHTML = "对不起,没有该板块";
         document.getElementById('tips_info').style.color = "red";
         setTimeout("clear_tips()",2000);
     }
     });

}
function clear_tips() {
    document.getElementById('tips_info').innerHTML = "";
    document.getElementById('duck_gear_board_list').style.visibility = "hidden";
}
//防止重复
function ifExist(index,num){
    for(var i=0;i<index.length;i++){
        if(index[i]==num){
            return 1;
        }
    }
    return 0;
}
function  initBubble() {
    var index=[],length=globalData.length,m=0,i=0;
    while(i<12){
        var newNum=Math.floor(Math.random()*(length+1));
        if(ifExist(index,newNum)==0){
            index[i]=newNum;
            i++;
        }
    }

    for(var i=0;i<index.length;i++){
        boards[m]=({ //把板块的泡泡放进去
            x: 20*i+Math.random()*10,
            y: 40+Math.random()*10,
           z:20,
            ifStock:0,
            name: globalData[index[i]].boardName,
            rate:((globalData[index[i]].boardChangeRate)*100).toFixed(2),
            color:myRed
        });
        if(boards[m].rate<0) boards[m].color=myGreen;
        if(i<index.length/3){}
        else if(i>=index.length/3&&i<2*index.length/3){
            boards[m].x= (i-index.length/3)*20+Math.random()*10;
            boards[m].y=20+Math.random()*10;
        }else{
            boards[m].x= (i-2*index.length/3)*20+Math.random()*10;
            boards[m].y=Math.random()*10;
        }
        m++;
        var parBoard=m-1;//记录这一圈股票所属板块的board序号
        var j=0;
        
       for(var j=0;j<4&&j<globalData[index[i]].stocks.length;j++){
            //ifshowDataLabel=false;
            boards[m]=({
                x: boards[parBoard].x-10,
                y: boards[parBoard].y,
                z: 15,
                ifStock:1,
                name: globalData[index[i]].stocks[j].name,
                code:globalData[index[i]].stocks[j].code,
                rate:((globalData[index[i]].stocks[j].changeRate)*100).toFixed(2),
                color:myRed,
                volume:globalData[index[i]].stocks[j].turnoverVol,
                board:boards[parBoard].name
            });
            if(j==1){
                boards[m].x=boards[parBoard].x+10;
            }else if(j==2){
                boards[m].x=boards[parBoard].x;
                boards[m].y=boards[parBoard].y-10;
            }else if(j==3){
                boards[m].x=boards[parBoard].x;
                boards[m].y=boards[parBoard].y+10;
            }
            if(boards[m].rate<0) boards[m].color=myGreen;
            else if(boards[m].rate==0) boards[m].color=myGrey;            
            m++;
            
        }

    }
    draw();
}


function draw(){
    $('#bubble').highcharts({
        chart: {
            type: 'bubble',
            plotBorderWidth: 0,
           // zoomType: 'xy'
        },
        tooltip: {
            useHTML: true,
            headerFormat: '<table>',
            pointFormat: 
            '<tr><th colspan="2"><h3>{point.name}</h3></th><td>{point.code}</td></tr>'+
            '<tr><th>涨跌幅：</th><td>{point.rate}%</td></tr>'
           /* '<tr><th>成交量:</th><td>{point.volume}</td></tr>'
            '<tr><th>所属板块:</th><td>{point.board}</td></tr>'*/
            ,

            footerFormat: '</table>',
            followPointer: true
        },
        legend: {
            enabled: false
        },
        title: {
            text: ''
        },
        credits: {
            enabled:false
        },
        exporting:{
            enabled:false
        },
        xAxis: {
            visible:false
        },
        yAxis: {
            visible:false
        },
        plotOptions: {
            bubble:{
              zMin:15
            },
            series: {
                //color:"#FFFFFF",
                negativeColor:"",
                cursor:'pointer',
                events: {
                    click: function (data) {
                        var name=data.point.name;
                        var code=data.point.code;
                        if(data.point.ifStock==0){
                            location.href="BoardDetail.html"+"?name="+name;
                        }else{
                            location.href="duck_stockDetail.html"+"?code="+code;
                        }

                    }
                },
                dataLabels: {
                    enabled: true,
                    format: '{point.name}',
                    allowOverlap:true
                }
            }
        },
        series: [{
            data: boards
        }]
    });
 
}
