/**
 * Created by dsn on 2016/5/26.
 */
var boards=[];
var globalData;
var ifshowDataLabel=true;
var myRed="#EE2C2C",myGreen="#00CD66",myGrey="#8B7E66"
$.getJSON('/Board/getAllBoardsAndStockData', function (data) {
    globalData=data;
    alert()
    initBubble();
});

function dosearch() {//TODO 板块不存在 即在当前页面警告
    var boardName=document.getElementById("searchCode").value;
    $.getJSON('/Board/checkBoard?'+'board='+boardName, function (data) {
     if(data==true)  location.href="BoardDetail.html"+"?name="+boardName;
     else alert("对不起,不存在该板块");
     });

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
    alert("initBubble");
    var index=[],length=globalData.length,m=0,i=0;
    while(i<9){
        var newNum=Math.floor(Math.random()*(length+1));
        if(ifExist(index,newNum)==0){
            index[i]=newNum;
            i++;
        }
    }

    for(var i=0;i<index.length;i++){
        boards[m]=({ //把板块的泡泡放进去
            x: 5*i+Math.random(),
            y: 50+Math.random()*10,
            z: 50,//大小
            ifStock:0,
            name: globalData[index[i]].boardName,
            rate:(globalData[index[i]].boardChangeRate).toFixed(4),
            color:myRed
        });
        if(boards[m].rate<0) boards[m].color=myGreen;
        if(i<index.length/3){
            boards[m].x= 5*i+Math.random();
            boards[m].y=Math.random()*10;
        }else if(i<2*index.length/3){
            boards[m].x= (i-index.length/3)*5+Math.random();
            boards[m].y=70+Math.random()*10;
        }else{
            boards[m].x= (i-2*index.length/3)*5+Math.random();
            boards[m].y=150+Math.random()*10;
        }
        m++;
        var parBoard=m-1;//记录这一圈股票所属板块的board序号
        for(var j=0;j<4&&j<globalData[index[i]].stocks.length;j++){
            //ifshowDataLabel=false;
            boards[m]=({
                x: boards[parBoard].x-1-Math.random(),
                y: boards[parBoard].y,
                z: 30,
                ifStock:1,
                name: globalData[index[i]].stocks[j].name,
                code:globalData[index[i]].stocks[j].code,
                rate:(globalData[index[i]].stocks[j].changeRate).toFixed(6),
                color:myRed,
                volume:globalData[index[i]].stocks[j].turnoverVol,
                board:boards[parBoard].name
            });
            if(j==1){
                boards[m].x=boards[parBoard].x+1+Math.random();
            }else if(j==2){
                boards[m].x=boards[parBoard].x;
                boards[m].y=boards[parBoard].y-40-Math.random();
            }else if(j==3){
                boards[m].x=boards[parBoard].x;
                boards[m].y=boards[parBoard].y+40+Math.random();
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
            '<tr><th colspan="2"><h3>{point.name}</h3></th></tr>'+
            '<tr><th>板块涨跌：</th><td>{point.rate}</td></tr>'+
            '<tr><th>x:</th><td>{point.x}</td></tr>' +
            '<tr><th>y:</th><td>{point.y}</td></tr>' +
             '<tr><th>z:</th><td>{point.z}</td></tr>'+
                '<tr><th>成交量:</th><td>{point.volume}</td></tr>'+
            '<tr><th>所属板块:</th><td>{point.board}</td></tr>'
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
        xAxis: {
            visible:false
        },
        yAxis: {
            visible:false
        },
        plotOptions: {
            bubble:{
              minSize:30  
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
                    enabled: true,//TODO 先暂时改成false
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
