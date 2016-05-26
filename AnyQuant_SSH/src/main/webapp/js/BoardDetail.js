/**
 * Created by dsn on 2016/5/26.
 */

    function getCode() {
        var result = window.location.search;
        result = result.split('?')[1].split('=')[1];
    alert("in function getcode:"+result);
        return result;
    }
var myRed="#EE2C2C",myGreen="#00CD66",myGrey="#8B7E66"
var myBoardName="汽车零部件";//板块的名称
function dosearch() {
    alert(document.getElementById("searchCode").value);
}
function initTree(data){
    var myData=[],length=data.length;

    if(length>=10){
        for(var i=0;i<5;i++){
            var tt=i+'';
            myData[i]=({
                id: tt,
                name: ' ',
            });
        }
        for(var j=5;j<length+5;j++){
            var par=j%5+'';
            if(data[j-5].changeRate>0){
                myData[j]= ({
                    parent: par,
                    code: data[j-5].code,
                    name: data[j-5].stockName,
                    price:((data[j-5].open+data[j-5].close)/2).toFixed(2),
                    value:data[j-5].turnoverVol,
                    rate:data[j-5].changeRate,
                    volume:(data[j-5].turnoverVol/10000).toFixed(2),
                    color:myRed
                });
            }else if(data[j-5].changeRate<0){
                myData[j]= ({
                    parent: par,
                    code: data[j-5].code,
                    name: data[j-5].stockName,
                    price:((data[j-5].open+data[j-5].close)/2).toFixed(2),
                    value:data[j-5].turnoverVol,
                    rate:data[j-5].changeRate,
                    volume:(data[j-5].turnoverVol/10000).toFixed(2),
                    color:myGreen
                });
            }else{
                myData[j]= ({
                    parent: par,
                    code: data[j-5].code,
                    name: data[j-5].stockName,
                    price:((data[j-5].open+data[j-5].close)/2).toFixed(2),
                    value:data[j-5].turnoverVol,
                    rate:data[j-5].changeRate,
                    volume:(data[j-5].turnoverVol/10000).toFixed(2),
                    color:myGrey
                });
            }

            if(myData[j].value==0) myData[j].value=100000;

        }
    }else{
        for(var j=0;j<length;j++){
            if(data[j].changeRate>0){
                myData[j]= ({
                    code: data[j].code,
                    name: data[j].stockName,
                    price:((data[j].open+data[j].close)/2).toFixed(2),
                    value:data[j].turnoverVol,
                    rate:data[j].changeRate,
                    volume:(data[j].turnoverVol/10000).toFixed(2),
                    color:myRed
                });
            }else if(data[j].changeRate<0){
                myData[j]= ({
                    code: data[j].code,
                    name: data[j].stockName,
                    price:((data[j].open+data[j].close)/2).toFixed(2),
                    value:data[j].turnoverVol,
                    rate:data[j].changeRate,
                    volume:(data[j].turnoverVol/10000).toFixed(2),
                    color:myGreen
                });
            }else{
                myData[j]= ({
                    parent: par,
                    code: data[j-5].code,
                    name: data[j-5].stockName,
                    price:((data[j-5].open+data[j-5].close)/2).toFixed(2),
                    value:data[j-5].turnoverVol,
                    rate:data[j-5].changeRate,
                    volume:(data[j-5].turnoverVol/10000).toFixed(2),
                    color:myGrey
                });
            }

            if(myData[j].value==0) myData[j].value=100000;

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
            '<br/>成交量：<b>{point.volume}万</b><br>parent:' +
            //'<b>{point.parent}</b>' +
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
    var upNum=0,downNum=0;
    for(var i=0;i<stockNum;i++){
        if(data[i].close>=data[i].open){//涨
            upNum++;
        }
    }
    downNum=stockNum-upNum;
    var rate=0.5;//TODO 板块的涨跌幅怎么算


    if(rate>=0){
        document.getElementById('rate').style.color=myRed;
        document.getElementById('rate').innerHTML="+"+rate;
    }
    else {
        document.getElementById('rate').innerHTML=rate;
        document.getElementById('rate').style.color=myGreen;
    }

    document.getElementById('upNum').innerHTML="+"+upNum;
    document.getElementById('downNum').innerHTML="-"+downNum;
}
function initLine(data){
    var boardData=[],benchData=[],length=data.length;

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
            selected: 0
        },
        title: {
            text: '与大盘走势比较'
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
    myBoardName=getCode();
    alert(myBoardName);
    document.getElementById('stockName').innerHTML=myBoardName;
    $.getJSON('/Board/getBoardDistribution?boardName='+myBoardName, function (data) {
        initNumbers(data);
        initTree(data);

    });
    $.getJSON('/Board/getCompareData?boardName='+myBoardName, function (data) {

        initLine(data);
    });

});
