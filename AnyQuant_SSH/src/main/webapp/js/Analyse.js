/**
 * Created by dsn on 2016/5/27.
 */
var chart1;
var factorPart1;
var table_chosen;
var strategy,basecode,capital;//起始资金
var taxRate,numOfStock,interval,startT,endT;
var factorSum=0;
var codes=[];//股票池
var peNum=0,pbNum=0,vol5Num=0,vol10Num=0,vol60Num=0,vol120Num=0,psNum=0,pcfNum=0;//各因子权重
var content_of_analyse_factor_info;
function doAnalyse_diy(){
    var code_raw=table_chosen.data();

    for(var i=0;i<code_raw.length;i++){
        codes[i]=code_raw[i][1];
    }
    startT=document.getElementById("start").value;
    endT=document.getElementById("end").value;
    basecode=document.getElementById("basecode").value;
    capital=document.getElementById("capital").value;
    taxRate=document.getElementById("taxRate").value;
    interval=document.getElementById("interval").value;
    
    peNum=parseInt(document.getElementById("pe_text").value);
    pbNum=parseInt(document.getElementById("pb_text").value);
    vol5Num=parseInt(document.getElementById("vol5_text").value);
    vol10Num=parseInt(document.getElementById("vol10_text").value);
    vol60Num=parseInt(document.getElementById("vol60_text").value);
    vol120Num=parseInt(document.getElementById("vol120_text").value);
    psNum=parseInt(document.getElementById("ps_text").value);
    pcfNum=parseInt(document.getElementById("pcf_text").value);
    factorSum=peNum+pbNum+vol5Num+vol10Num+vol60Num+vol120Num+psNum+pcfNum;
    peNum/=factorSum;
    pbNum/=factorSum;
    vol5Num/=factorSum;
    vol10Num/=factorSum;
    vol60Num/=factorSum;
    vol120Num/=factorSum;
    psNum/=factorSum;
    pcfNum/=factorSum;

    alert("DIY:"+
        "\n选择的大盘代号："+basecode+
        "\n起始资金："+capital+
        "\n交易费率："+taxRate+
        "\n调仓间隔："+document.getElementById("interval").value+
        "\n起止时间："+startT+"~"+endT+
        "\n因子们："+
        "\nPE:"+peNum+
        "\nPB:"+pbNum+
        "\nVOL5:"+vol5Num+
        "\nVOL10:"+vol10Num+
        "\nVOL60:"+vol60Num+
        "\nVOL120:"+vol120Num+
        "\nPS:"+psNum+
        "\nPCF:"+pcfNum+
        "\n股票池："+codes       
    );
    var params = "strategy="+strategy+"&baseCode="+basecode+"&capital="+capital+
        "&taxRate="+taxRate+"&numOfStock="+numOfStock+"&interval="+interval+
        "&start="+startT+"&end="+endT;

    location.href="Analyse_result.html?"+params;
}

function doAnalyse(){
    startT=document.getElementById("start").value;
    endT=document.getElementById("end").value;
    strategy=document.getElementById("strategy").value;
    basecode=document.getElementById("basecode").value;
    capital=document.getElementById("capital").value;
    taxRate=document.getElementById("taxRate").value;
    numOfStock=document.getElementById("numOfStock").value;
    interval=document.getElementById("interval").value;
    alert("推荐策略："+strategy+
        "\n选择的大盘代号："+basecode+
        "\n起始资金："+capital+
        "\n交易费率："+taxRate+
        "\n股票数量："+numOfStock+
        "\n调仓间隔："+interval+
        "\n起止时间："+startT+"~"+endT);
   
    var params = "strategy="+strategy+"&baseCode="+basecode+"&capital="+capital+
        "&taxRate="+taxRate+"&numOfStock="+numOfStock+"&interval="+interval+
        "&start="+startT+"&end="+endT;

    location.href="Analyse_result.html?"+params;
}



function factorAnalyse(){//分析因子
    startT=document.getElementById("start_diy").value;
    endT=document.getElementById("end_diy").value;
    basecode=document.getElementById("basecode_diy").value;

    var code_raw=table_chosen.data();
    
    for(var i=0;i<code_raw.length;i++){
        codes[i]=code_raw[i][1];
    }
    content_of_analyse_factor_info=document.getElementById("factor_analyse_info_label").innerHTML;
    document.getElementById("factor_analyse_info_label").innerHTML="计算中。。";
    $.getJSON('/Strategy/getStocksFactorJudgment?'+'codes='+codes+'&start='+startT+'&end='+endT
        +'&baseBench='+basecode,
        function (data) {
            $("#allStocksDiv").remove();
            init_bar(data);
        });

    

}
function initTable_all(allStock) {
    var table = $('#allstock_list').DataTable( {
        data:allStock,
        "order":[[1,"asc"]],
        lengthChange:false,
        pageLength:9,
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
    table_chosen = $('#chosenStocks').DataTable( {
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
        table_chosen.search(this.value).draw();
    });
    //下面是选择行
    $('#chosenStocks tbody').on( 'click', 'tr', function () {
        table_chosen.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        var selected_row = table_chosen.row('.selected').index();
        var code= table_chosen.cell(selected_row,1).data();
        var name=table_chosen.cell(selected_row,0).data();
        var arg=name+"&"+code;
        refreshAllList(arg);
        table_chosen.row('.selected').remove().draw(false);
    } );

}
function init_bar(data) {    
    var ICdata=[],IRdata=[],WinRatedata=[],AvgProfitdata=[];
    for(var x in data.sortRankIC){        
        ICdata.push([x,data.sortRankIC[x]]);
    }
    for(var x in data.sortRankWinRate){
        WinRatedata.push([x,data.sortRankWinRate[x]]);
    }
    for(var x in data.sortRankIR){
        IRdata.push([x,data.sortRankIR[x]]);
    }
    for(var x in data.sortAvgProfit){
        AvgProfitdata.push([x,data.sortAvgProfit[x]]);
    }
    $('#chart1').highcharts({
        chart: {
            type: 'column',
            width:400,
            height:200
        },
        title: {
            text: 'sortRankIC'
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
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.4f}</b>'
        },
        series: [{
            name: '',
            data: ICdata,
            dataLabels: {
                enabled: false
            }
        }],
        credits: {
            enabled:false
        }
    });
    $('#chart2').highcharts({
        chart: {
            type: 'column',
            width:400,
            height:200,
        },
        title: {
            text: 'sortRankIR'
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
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.4f}</b>'
        },
        series: [{
            name: '',
            data: IRdata,
            dataLabels: {
                enabled:false
            }
        }],
        credits: {
            enabled:false
        }
    });
    $('#chart3').highcharts({
        chart: {
            type: 'column',
            width:400,
            height:200
        },
        title: {
            text: 'sortRankWinRate'
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
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.4f}</b>'
        },
        credits: {
            enabled:false
        },
        series: [{
            name: '',
            data: WinRatedata,
            dataLabels: {
                enabled:false
                /* true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.4f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }*/
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
            text: 'sortAvgProfit'
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
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '包含股票数量: <b>{point.y:.4f}</b>'
        },
        series: [{
            name: '',
            data: AvgProfitdata,
            dataLabels: {
                enabled: false
            }
        }],
        credits: {
            enabled:false
        }
    });
    document.getElementById("factor_analyse_info_label").innerHTML="~现在你可以参考着配置因子啦^o^";
}
function chooseStrategy(){
    var strategy=document.getElementById("strategy").value;
    if(strategy=="Strategy_PE"){//TODO 这两个策略的参数差别 
        // $("#basecode_label").hide();
    }

    if(strategy=="Strategy_VOL"){//TODO 这两个策略的参数
        // $("#basecode_label").show();
    }
}
function initpie(){
    var dataArrayF= [
        ['市盈率',5],
        [ '市净率',5],
        [ '5日换手率',5],
        [ '10日换手率',5],
        [ '60日换手率',5],
        [ '120日换手率',5],
        [ '市销率',5],
        [ '市现率',5]
    ];
    var dataArrayM= [
        [ '1档位',5],
        [ '2档位',5],
        [ '3档位',5],
        [ '4档位',5],
        [ '5档位',5]
    ];
    chart1 = new Highcharts.Chart({
        colors:['#54FF9F','#46cbee', '#fec157','#CD96CD', '#cfd17d', '#4F94CD', '#FF9655', '#FFF263', '#FF6A6A'] ,//不同组数据的显示背景色，循环引用
        chart: {
            width:450,
            height:450,
            renderTo: 'pie_factors',//画布所在的div id
            // plotBackgroundColor: '#f5f2ec',//画布背景色
            plotBorderWidth: null,//画布边框
            plotShadow: false,
            margin:[0,120,0,120]//画布外边框
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
            data: dataArrayF
        }]
    });
    chart2 = new Highcharts.Chart({
        colors:['#54FF9F','#46cbee', '#fec157','#CD96CD', '#cfd17d', '#4F94CD', '#FF9655', '#FFF263', '#FF6A6A'] ,//不同组数据的显示背景色，循环引用
        chart: {
            width:450,
            height:450,
            renderTo: 'pie_money',//画布所在的div id
            // plotBackgroundColor: '#f5f2ec',//画布背景色
            plotBorderWidth: null,//画布边框
            plotShadow: false,
            margin:[0,120,0,120]//画布外边框
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
            data: dataArrayM
        }]
    });
}
function redrawpie_factor(){
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
function redrawpie_money(){
    dataArray=[
        ['1档位',parseInt($('#level1').val())],
        [ '2档位',parseInt($('#level2').val())],
        [ '3档位',parseInt($('#level3').val())],
        [ '4档位',parseInt($('#level4').val())],
        [ '5档位',parseInt($('#level5').val())]
    ];
    this.chart2.series[0].setData(dataArray);
    this.chart2.series[0].redraw();
}
function initBaseCode(data){
    var listA=document.getElementById("basecode");
    var listB=document.getElementById("basecode_diy")
    for(var i=0;i<data.length;i++){
        listA.options.add(new Option(data[i].name,data[i].code));
        listB.options.add(new Option(data[i].name,data[i].code));
    }
}

$(document).ready(function () {
    $("ul").idTabs();
    
    $.getJSON('/BenchMark/getBenchList', function (data) {
        initBaseCode(data);
    });
    initChosenList();
    initpie();
    $.getJSON('/Stock/getStockDataList',function (data) {
        initTable_all(data);
        $("#loading").hide();
    })

    
});
