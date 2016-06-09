/**
 * Created by Adsn on 2016/5/27.
 */
var chart1;
function initpie_factor(){
    var dataArray= [
        ['市盈率',5],
        [ '市净率',5],
        [ '5日换手率',5],
        [ '10日换手率',5],
        [ '60日换手率',5],
        [ '120日换手率',5],
        [ '市销率',5],
        [ '市现率',5]
    ];
    chart1 = new Highcharts.Chart({
        colors:['#54FF9F','#46cbee', '#fec157','#CD96CD', '#cfd17d', '#4F94CD', '#FF9655', '#FFF263', '#FF6A6A'] ,//不同组数据的显示背景色，循环引用
        chart: {
            width:500,
            height:300,
            renderTo: 'pie_factors',//画布所在的div id
            // plotBackgroundColor: '#f5f2ec',//画布背景色
            plotBorderWidth: null,//画布边框
            plotShadow: false,
            margin:[0,0,0,0]//画布外边框
        },
        title: {
            text: ''//画布题目，此处置空
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                size:'90%',
                dataLabels: {
                    enabled: true,
                    color: '#666666',
                    connectorWidth: 1,
                    //distance: 3,
                    connectorColor: '#666666',
                    style:{fontSize:'12px',fontWeight:'normal'},
                    formatter: function() {
                        return  this.point.name+ Math.round(this.percentage,2) +' %';
                    }
                },
            }
        },
        exporting: {
            buttons: {
                exportButton: {
                    enabled:false //不显示导出icon
                },
                printButton: {
                    enabled:false //不显示打印icon
                }
            }
        },
        credits:{
            enabled:false//不显示highcharts网址
        },
        tooltip:{
            enabled:false
        },
        series: [{
            type: 'pie',
            name: '',
            data: dataArray
        }]
    });
    /*实时监听因子权重改变*/
    if(/msie/i.test(navigator.userAgent)){//ie浏览器
        document.getElementById('pe_text').onpropertychange=redrawpie;
    }else {//非ie浏览器，比如Firefox
        document.getElementById('pe_text').addEventListener("onpropertychange",redrawpie, true);
    }
}
function redrawpie(){
    dataArray=[
        ['市盈率',parseInt($('#pe_text').val())],
        [ '市净率',parseInt($('#pb_text').val())],
        [ '5日换手率',parseInt($('#vol5_text').val())],
        [ '10日换手率',parseInt($('#vol10_text').val())],
        [ '60日换手率',parseInt($('#vol60_text').val())],
        [ '120日换手率',parseInt($('#vol120_text').val())],
        [ '市销率',parseInt($('#ps_text').val())],
        [ '市现率',parseInt($('#pcf_text').val())]
    ];
    this.chart1.series[0].setData(dataArray);
    this.chart1.series[0].redraw();
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
    alert("推荐策略："+document.getElementById("strategy").value+
        "\n选择的大盘代号："+document.getElementById("basecode").value+
        "\n起始资金："+document.getElementById("capital").value+
        "\n交易费率："+document.getElementById("taxRate").value+
        "\n股票数量："+document.getElementById("numOfStock").value+
        "\n调仓间隔："+document.getElementById("interval").value+
        "\n起止时间："+startT+"~"+endT);
    location.href="Analyse_charts.html";
}


function initBaseCode(data){
    var listA=document.getElementById("basecode");
    var listB=document.getElementById("basecode_diy")
    for(var i=0;i<data.length;i++){
        listA.options.add(new Option(data[i].name,data[i].code));
        listB.options.add(new Option(data[i].name,data[i].code));
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
    initChosenList();
    initpie_factor();
    $.ajax({
        type:'post',
        url:'/Stock/getStockDataList',
        contentType:'application/json;charset=utf-8',
        success:function (data){
            initTable_all(data);
            $("#loading").remove();
        },
        error:function () {
            alert("请求失败");
        }
    });

});
function factorAnalyse(){//分析因子
    $.ajax({
        type:'post',
        url :'/Optional/getRegionDistribution',
        success:function (data) {
            var result=[];
            for(var x in data){
                result.push([x,data[x]]);
            }
            $("#allStocksDiv").remove();
            init_region_bar(result);
        }
    });
}
function initTable_all(allStock) {
    var table = $('#allstock_list').DataTable( {
        data:allStock,
        "order":[[1,"asc"]],
        lengthChange:false,
        pageLength:10,
        dom: 'lrtp',
        columns:[
            {data:'name'},
            {data:'code'}
        ],
        "autoWidth":false,
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 支股票",
            "sInfo": "共 _TOTAL_ 支股票",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sZeroRecords": "没有检索到股票",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        },

    } );
    //以下关于自定义搜索框
    $('#search_text').on('keyup',function () {
        table.search(this.value).draw();
    });
    //下面是选择行
    $('#allstock_list tbody').on( 'click', 'tr', function () {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        var selected_row = table.row('.selected').index();
        var code= table.cell(selected_row,1).data();
        var name=table.cell(selected_row,0).data();
        var arg=name+"&"+code;
        refreshChosenList(arg);
        table.row('.selected').remove().draw(false);
    } );
}
function refreshChosenList(data) {
    var code=data.split("&")[1];
    var name=data.split("&")[0];
    var t_chosen=$("#chosenStocks").DataTable();
    if(name!="undefined"){
        t_chosen.row.add([
            name,
            code
        ]).draw();
    }

}
function refreshAllList(data){
    var args=[];
    var code=data.split("&")[1];
    var name=data.split("&")[0];
    var arg={};
    if(name!="undefined"){
        var item = new Object();
        item.name = name;
        item.code = code;
        var json_item = JSON.stringify(item);
        // alert("--->"+json_item);
        arg["name"]=name;
        arg["code"]=code;
        args.push(arg);
        var jsonString =JSON.stringify(args);
        var t_all=$("#allstock_list").DataTable();
        var temp = eval("("+json_item+")");
        t_all.row.add(temp).draw();
    }

}
function initChosenList(){
    var table = $('#chosenStocks').DataTable( {
        order:[[1,"asc"]],
        lengthChange:false,
        pageLength:5,
        dom: 'lrtip',

        "autoWidth":false,
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 支股票",
            "sInfo": "共 _TOTAL_ 支股票",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sZeroRecords": "没有检索到股票",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        },
    } );
    //以下关于自定义搜索框
    $('#search_text_chosen').on('keyup',function () {
        table.search(this.value).draw();
    });
    //下面是选择行
    $('#chosenStocks tbody').on( 'click', 'tr', function () {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        var selected_row = table.row('.selected').index();
        var code= table.cell(selected_row,1).data();
        var name=table.cell(selected_row,0).data();
        var arg=name+"&"+code;
        refreshAllList(arg);
        table.row('.selected').remove().draw(false);
    } );

}
function init_region_bar(data) {
    $('#chart1').highcharts({
        chart: {
            type: 'column',
            width:400,
            height:200
        },
        title: {
            text: 'World\'s largest cities per 2014'
        },
        subtitle: {
            text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Population (millions)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.0f}</b>'
        },
        series: [{
            name: 'Population',
            data: data,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.0f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
    $('#chart2').highcharts({
        chart: {
            type: 'column',
            width:400,
            height:200,
            x:100
        },
        title: {
            text: 'World\'s largest cities per 2014'
        },
        subtitle: {
            text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Population (millions)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.0f}</b>'
        },
        series: [{
            name: 'Population',
            data: data,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.0f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
    $('#chart3').highcharts({
        chart: {
            type: 'column',
            width:400,
            height:200
        },
        title: {
            text: 'World\'s largest cities per 2014'
        },
        subtitle: {
            text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Population (millions)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.0f}</b>'
        },
        series: [{
            name: 'Population',
            data: data,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.0f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
    $('#chart4').highcharts({
        chart: {
            type: 'column',
            width:400,
            height:200
        },
        title: {
            text: 'World\'s largest cities per 2014'
        },
        subtitle: {
            text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Population (millions)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.0f}</b>'
        },
        series: [{
            name: 'Population',
            data: data,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.0f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
}