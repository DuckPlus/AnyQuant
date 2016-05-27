/**
 * Created by dsn on 2016/5/26.
 */
var globalData=[];
var boards=[];
var startDraw=0;
var myRed="#EE2C2C",myGreen="#00CD66",myGrey="#8B7E66"
$.getJSON('/Board/getAllBoardsAndStockData', function (data) {
    globalData=data;
    initBubble(data);
});
//防止重复
function ifExist(index,num){
    for(var i=0;i<index.length;i++){
        if(index[i]==num){
            return 1;
        }
    }
    return 0;
}
function  initBubble(data) {
    var index=[],length=globalData.length,i=0;

    
    while(i<9){
        var newNum=Math.floor(Math.random()*(length+1));
        if(ifExist(index,newNum)==0){
            index[i]=newNum;
            i++;
        }
    }
    //把板块的泡泡放进去
    for(var i=0;i<index.length;i++){
        boards[i]=({
            x: 5*i+Math.random(),
            y: 50+Math.random()*10,
            z: 70,//大小
            name: data[index[i]].boardName,
            rate:(data[index[i]].boardChangeRate).toFixed(4),
            color:myRed
        });
        if(boards[i].rate<0) boards[i].color=myGreen;
        if(i<index.length/3){
            boards[i].x= 5*i+Math.random();
            boards[i].y=50+Math.random()*10;
        }else if(i<2*index.length/3){
            boards[i].x= (i-index.length/3)*3+Math.random();
            boards[i].y=100+Math.random()*10;
        }else{
            boards[i].x= (i-2*index.length/3)*3+Math.random();
            boards[i].y=150+Math.random()*10;
        }
        
    }
    var l=boards.length,j=boards.length;
    startDraw=1;
    draw();
   /* for(var i=0;i<l;i++){
         alert(boards[i].name);
        $.getJSON('/Board/getBoardDistribution'+boards[i].name, function (data) {
            alert("success");
           for(var m=0;m<data.length;m++){
               alert("hello!!");
               alert(data[m].stockName);
               boards[j]=({
                   x: 700,
                   y: 50,
                   z: -20,//大小
                   name: data[m].stockName
               });
               j++;
           }
            
            if(i==l-1){
                startDraw=1;
            }
            draw();
        });
    }*/
    
}


function draw(){
if(startDraw==1){
    $('#bubble').highcharts({
        chart: {
            type: 'bubble',
            plotBorderWidth: 0,
            zoomType: 'xy'
        },
        tooltip: {
            useHTML: true,
            headerFormat: '<table>',
            pointFormat: '<tr><th colspan="2"><h3>{point.name}</h3></th></tr>'
            +'<tr><th>板块涨跌：</th><td>{point.rate}</td></tr>'
            /* +'<tr><th>Sugar intake:</th><td>{point.y}g</td></tr>' +
             '<tr><th>Obesity (adults):</th><td>{point.z}%</td></tr>'*/
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
            series: {
                //color:"#FFFFFF",
                negativeColor:"",
                cursor:'pointer',
                events: {
                    click: function (data) {
                        var name=data.point.name;
                        if(data.point.z>0){
                            location.href="BoardDetail.html"+"?name="+name;
                        }else{
                            location.href="duck_stockDetail.html"+"?name="+name;
                        }

                    }
                },
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            data: boards
        }]
    });
}
 
}
