/**
 * Created by Adsn on 2016/5/27.
 */
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

function doAnalyse_diy(){
    var startT=document.getElementById("start").value;
    var endT=document.getElementById("end").value;
    alert("DIY:"+
        "\n调仓间隔："+document.getElementById("interval").value+
        "\n起止时间："+startT+"~"+endT);
    location.href="Analyse_charts.html";
}


function doAnalyse(){
    var startT=document.getElementById("start").value;
    var endT=document.getElementById("end").value;
    alert("推荐策略：选择的大盘代号："+document.getElementById("basecode").value+
        "\n起始资金："+document.getElementById("capital").value+
        "\n交易费率："+document.getElementById("taxRate").value+
        "\n股票数量："+document.getElementById("numOfStock").value+
        "\n调仓间隔："+document.getElementById("interval").value+
        "\n起止时间："+startT+"~"+endT);
    location.href="Analyse_charts.html";
}


function initBaseCode(data){
    var list=document.getElementById("basecode");
    for(var i=0;i<data.length;i++){
        list.options.add(new Option(data[i].name,data[i].code));
    }
}
function chooseStrategy(){
    var strategy=document.getElementById("strategy").value;
    if(strategy=="Strategy_PE"){//TODO 这两个策略的参数差别的fake哒~
        $("#basecode_label").hide();
        $("#basecode").hide();
    }
    
    if(strategy=="Strategy_VOL"){//TODO 这两个策略的参数差别的fake哒~
        $("#basecode_label").show();
        $("#basecode").show();
    }
}
$(document).ready(function () {
    $("ul").idTabs();
    myBoardName="氮肥";
    $.getJSON('/Board/getCompareData?boardName='+myBoardName, function (data) {
        initLine(data);
    });
    $.getJSON('/BenchMark/getBenchList', function (data) {
        initBaseCode(data);
    });
});
