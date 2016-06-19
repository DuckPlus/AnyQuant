/**
 * Created by dsn on 2016/5/26.
 */
var myRed="#EE2C2C",myGreen="#00CD66",myGrey="#8B7E66";
var myBoardName="";//板块的名称
    function getCode() {
        var loc = window.location.search;
        var result = loc.split('?')[1].split('&')[0].split("=")[1];
        return result;
    }


function dosearch() {
    var boardName=document.getElementById("searchCode").value;
    location.href="BoardDetail.html"+"?name="+boardName;
}
function initTree(data){
    var myData=[],length=data.length;
    if(length>=6){
        for(var i=0;i<6;i++){
            var tt=i+'';
            myData[i]=({
                id: tt,
                name: ' ',
            });
        }
        for(var j=6;j<length+6;j++){
            var par=j%6+'';
            if(data[j-6].changeRate>0){
                myData[j]= ({
                    parent: par,
                    code: data[j-6].code,
                    name: data[j-6].stockName,
                    price:((data[j-6].open+data[j-6].close)/2).toFixed(2),
                    value:data[j-6].turnoverVol,
                    rate:data[j-6].changeRate,
                    volume:(data[j-6].turnoverVol/10000).toFixed(2),
                    color:myRed
                });
            }else if(data[j-6].changeRate<0){
                myData[j]= ({
                    parent: par,
                    code: data[j-6].code,
                    name: data[j-6].stockName,
                    price:((data[j-6].open+data[j-6].close)/2).toFixed(2),
                    value:data[j-6].turnoverVol,
                    rate:data[j-6].changeRate,
                    volume:(data[j-6].turnoverVol/10000).toFixed(2),
                    color:myGreen
                });
            }else{
                myData[j]= ({
                    parent: par,
                    code: data[j-6].code,
                    name: data[j-6].stockName,
                    price:((data[j-6].open+data[j-6].close)/2).toFixed(2),
                    value:data[j-6].turnoverVol,
                    rate:data[j-6].changeRate,
                    volume:(data[j-6].turnoverVol/10000).toFixed(2),
                    color:myGrey
                });
            }

            if(myData[j].value==0) myData[j].value=100000;

        }
    }else{
        for(var j=0;j<length;j++){           
            myData[j]= ({
                code: data[j].code,
                name: data[j].stockName,
                price:((data[j].open+data[j].close)/2).toFixed(2),
                value:data[j].turnoverVol,
                rate:data[j].changeRate,
                volume:(data[j].turnoverVol/10000).toFixed(2),
                color:myGreen
            });
            if(myData[j].value==0) myData[j].value=100000;
            if(data[j].changeRate>0){
                myData[j].color=myRed;
            }else if(data[j].changeRate<0){
                myData[j].color=myGreen;
            }else{
                myData[j].color=myGrey;
            }

            

        }
    }


    $('#treechart').highcharts({
        plotOptions: {
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                            location.href="duck_stockDetail.html"+"?code="+this.code;
                        }
                    }
                }
            }
        },
        tooltip: {
            pointFormat:'<b>{point.name}  </b>{point.code}<br>市值：<b>{point.price}</b><br/>涨跌幅：<b>{point.rate}</b>' +
            '<br/>成交量：<b>{point.volume}万</b>' +
            //'<br>parent:' +<b>{point.parent}</b>' +
            '',
            //valueDecimals: 2
        },

        series: [{
            type: "treemap",
            // layoutAlgorithm: 'squarified',
            alternateStartingDirection: false,
            levels: [{
                level: 1,
                layoutAlgorithm: 'sliceAndDice',
                dataLabels: {
                    enabled: true,
                    align: 'center',
                    //verticalAlign: 'center',
                    style: {
                        fontSize: '15px',
                        fontWeight: 'bold'
                    }
                }
            }],
            data: myData
        }],
        title: {
            text: '成分股'
        }
    });
}
function initNumbers(data){
    var stockNum=data.length;
    var upNum=0,downNum=0,stopNum=0,rate=0;
    for(var i=0;i<stockNum;i++){
        rate+=data[i].changeRate*data[i].weight;
        if(data[i].changeRate>0){//涨
            upNum++;
        }else if(data[i].changeRate==0){
            stopNum++;
        }
    }
    downNum=stockNum-stopNum-upNum;
    rate*=100;
    rate=rate.toFixed(2);
    if(rate>=0){
        document.getElementById('rate').style.color=myRed;
        document.getElementById('rate').innerHTML="+"+rate+"%";
    }
    else {
        document.getElementById('rate').innerHTML=rate+"%";
        document.getElementById('rate').style.color=myGreen;
    }

    document.getElementById('upNum').innerHTML="+"+upNum;
    document.getElementById('downNum').innerHTML="-"+downNum;
    document.getElementById('stopNum').innerHTML=stopNum;
    
    document.getElementById('upNum').style.color=myRed;
    document.getElementById('downNum').style.color=myGreen;
}
function initLine(data){
    var boardData=[],benchData=[],length=data.length;
    if(length==0){
        alert("对不起不存在该板块");
        if(parentName=="list") location.href="BoardList.html";
        else  location.href="BoardDetail.html"+"?name="+parentName;
    }
    for(var i=0;i<length;i++){
        var date=data[i].date;
        boardData[i]=[
            new Date(date.year,date.month-1,date.day+1).getTime(),
            data[i].boardData
        ];
        benchData[i]=[
            new Date(date.year,date.month-1,date.day+1).getTime(),
            data[i].huShen300Data
        ];
    }
    $('#linechart').highcharts('StockChart', {
        legend: {
            enabled: true,
            shadow: true
        },
        tooltip:{
            xDateFormat:'%Y-%m-%d'
        },
        rangeSelector: {
            selected: 5
        },
        title: {
            text: '与大盘走势比较'
        },
        yAxis:{
            labels: {
                align: 'left',
                x: 0,
                y: -2
            },
          title:{
              text:"收益率"
          }
        },
        series: [
            {
                name: myBoardName,
                data: boardData,
                type: 'spline',
                tooltip: {
                    valueDecimals: 2
                }
            },
            {
                name: '沪深300',
                data: benchData,
                type: 'spline',
                tooltip: {
                    valueDecimals: 2
                }
            }
        ]
    });


}
$(document).ready(function () {
    myBoardName=decodeURI(getCode());
    document.title="Duck "+myBoardName;
    document.getElementById('stockName').innerHTML=myBoardName;
    $.getJSON('/Board/getBoardDistribution?boardName='+myBoardName, function (data) {
        initNumbers(data);
        initTree(data);
        $("#duck_gear_board_treechart").remove();
    });
    $.getJSON('/Board/getCompareData?boardName='+myBoardName, function (data) {

        initLine(data);
        $("#duck_gear_board_compare").remove();
    });

});
