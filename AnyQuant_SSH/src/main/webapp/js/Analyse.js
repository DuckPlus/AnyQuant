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
function doAnalyse(){
    alert("选择的大盘代号："+document.getElementById("basecode").value);
    location.href="Analyse_charts.html";
}


function initBaseCode(data){
    var list=document.getElementById("basecode");
    for(var i=0;i<data.length;i++){
        list.options.add(new Option(data[i].name,data[i].code));
    }
}

$(document).ready(function () {
    myBoardName="氮肥";
    $.getJSON('/Board/getCompareData?boardName='+myBoardName, function (data) {
        initLine(data);
    });
    $.getJSON('/BenchMark/getBenchList', function (data) {
        initBaseCode(data);
    });
});
