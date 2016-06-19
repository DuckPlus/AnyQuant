/**
 * Created by duanzhengmou on 6/8/16.
 */


var trasaction_data_all;
var trasaction_detail_data_all;
var total_capital;
var is_diy = false;

var c=0;
var t;
var speed = 250;
function up(){
    speed = 1;
}
function load_finish(){
    up();
    
}

function timedCount(){
    var bar = document.getElementById('process');
    document.getElementById('process').style.width = c*0.1+"%";
    document.getElementById('info').innerHTML = Math.floor(c*0.1)+"%";
    c=c+1;
    if(c>2)bar.style.backgroundColor = "rgb(218,95,95)";
    // if(c>140)bar.style.backgroundColor = "rgb(238,188,115)";
    if(c>280)bar.style.backgroundColor = "rgb(230,187,72)";
    // if(c>520)bar.style.backgroundColor = "rgb(221,20,41)";
    if(c>460)bar.style.backgroundColor = "rgb(182,218,96)";
    // if(c>800)bar.style.backgroundColor = "rgb(110,220,236)";
    if(c>750)bar.style.backgroundColor = "rgb(33,186,69)";

    if(c==1001){ //progress finish
        $("#process_bar").remove();
        document.getElementById('main_content').style.visibility = "visible";
        if(!is_diy){
            document.getElementById("test_back_pic").style.visibility = "visible";
            document.getElementById("factor_weight").style.visibility = "hidden";
            document.getElementById("stock_pool").style.visibility = "hidden";
        }
    }
    if(c<1001){
        t=setTimeout("timedCount()",speed);
    }
}
$(document).ready(function () {
    timedCount();
    var url = location.href.split("?")[1];
    if(url.split("&")[0].split("=")[1]!="diy") {
        is_diy = false;
        draw_compare_chart(url);
        
        init_factor_table();
        init_stock_pool_table();
        // init_transaction_table();
        init_transaction_detail_table();
    }else{
        // alert("diy");
        init_factor_table();
        init_stock_pool_table();
        //diy part
        is_diy = true;
        draw_compare_chart_diy(url);
        
        init_transaction_detail_table();
        
    }
});
function draw_compare_chart_diy(url) {
    var params = url.split("&");
    var data_obj = new Object();
    var factor_map = ["PE","PB","VOL5","VOL10","VOL60","VOL120","PS","PCF"];
    //init table

    data_obj.baseCode = params[1].split("=")[1];
    data_obj.capital = params[2].split("=")[1];
    data_obj.taxRate = params[3].split("=")[1];
    data_obj.codes = params[4].split("=")[1];
    data_obj.names = params[5].split("=")[1];
    var code_data = data_obj.codes.split(",");
    var code_name = data_obj.names.split(",");
    var code_table_data = [];
    var code_table_data_item;
    // alert("push begin");
    for(var i=0;i<code_data.length;i++){
        code_table_data_item = new Object();
        code_table_data_item.code = code_data[i];
        code_table_data_item.name = decodeURI(code_name[i]);
        code_table_data.push(code_table_data_item);
    }
    // alert("push done");
    $('#stock_pool_table').DataTable().rows.add(JSON.parse(JSON.stringify(code_table_data))).draw();
    data_obj.interval = params[6].split("=")[1];
    data_obj.start = params[7].split("=")[1];
    data_obj.end = params[8].split("=")[1];
    total_capital = data_obj.capital;
    var factor_weight = {};
    var factor_table_data=[];
    var factor_table_data_item;
    var factors = params[9].split("=")[1].split(",");
    for (var i=0;i<factors.length;i++){
        if(factors[i]!=0){
            factor_table_data_item = new Object();
            factor_table_data_item.factor = factor_map[i];
            factor_table_data_item.weight = factors[i];
            factor_table_data.push(factor_table_data_item);
            factor_weight[factor_map[i]] = parseFloat(factors[i]);
            // factor_table_data.push([factor_map[i],factors[i]]);
        }
    }
    $('#factor_weight_table').DataTable().rows.add(JSON.parse(JSON.stringify(factor_table_data))).draw();
    data_obj.factorWeight = factor_weight;
    data_obj.investWeight = params[10].split("=")[1];
    document.getElementById('start_fund').innerHTML = data_obj.capital;
    document.getElementById('begin_time').innerHTML = data_obj.start;
    document.getElementById('end_time').innerHTML = data_obj.end;
    document.getElementById('trade_rate').innerHTML = data_obj.taxRate;
    document.getElementById('base_bench').innerHTML = data_obj.baseCode;
    document.getElementById('interval').innerHTML = data_obj.interval;
    
    test_strategy_with_factor(JSON.stringify(data_obj));

}
function draw_compare_chart(url_params) {
    var params = url_params.split("&");
    var data_obj = new Object();

    data_obj.name=params[0].split("=")[1];//strategy name
    data_obj.baseCode=params[1].split("=")[1];
    data_obj.capital=params[2].split("=")[1];
    data_obj.taxRate=params[3].split("=")[1];
    data_obj.vol=params[4].split("=")[1];//num of stock
    data_obj.interval=params[5].split("=")[1];
    data_obj.start=params[6].split("=")[1];
    data_obj.end=params[7].split("=")[1];
    total_capital =data_obj.capital;

    document.getElementById('start_fund').innerHTML = data_obj.capital;
    document.getElementById('begin_time').innerHTML = data_obj.start;
    document.getElementById('end_time').innerHTML = data_obj.end;
    document.getElementById('trade_rate').innerHTML = data_obj.taxRate;
    document.getElementById('base_bench').innerHTML = data_obj.baseCode;
    document.getElementById('interval').innerHTML = data_obj.interval;
    var json_data = JSON.stringify(data_obj);
    // alert(json_data);
    test_specific_strategy(json_data);
    // for (var i=1;i<params.length;i++){
    //     alert(params[i]);
    //    
    // }
}
function test_specific_strategy(json_data) {
    $.ajax({
        type:'post',
        url:'/Strategy/analyseWithSpecificStrategy',
        // data:{name:"Strategy_Vol",capital:1000000,
        //     taxRate:0.001,baseCode:"000010",interval:7,start:"2015/01/01",end:"2015/06/01",vol:100},
        
        data:{arguments:json_data},
        success:function (data) {
            load_finish();
            trasaction_data_all = data;
            // alert("seccess !");
            // alert("->"+data.cumRtnVOList[0].baseValue);
            // alert("length->"+data.cumRtnVOList.length);
            var cumRtnVOList = data.cumRtnVOList;
            var trade_data = data.tradeDataVOList;
            var compare_datas = [];
            var compare_base = [];
            var compare_test = [];
            for(var i=0;i<cumRtnVOList.length;i++){
                // alert(cumRtnVOList[i].baseValue);
                var str_date = cumRtnVOList[i].date.year+"-"+cumRtnVOList[i].date.month+"-"+cumRtnVOList[i].date.day;
                // alert(str_date);
                var date = new Date(str_date).getTime();
                compare_base.push([date,cumRtnVOList[i].baseValue]);
                compare_test.push([date,cumRtnVOList[i].testValue]);
                // alert("base "+compare_base+"test "+compare_test);
            }
            compare_datas.push(compare_base);
            compare_datas.push(compare_test);
            init_compare_chart(compare_datas);
            var trade_table_data = [];
            var trade_table_data_item;
            // document.getElementById('summary').innerHTML= JSON.stringify(trasaction_data_all);
            for (var i=0;i<trade_data.length;i++){
                trade_table_data_item = new Object();
                var trade_total=0,trade_num=0;
                for(var j=0;j<trade_data[i].tradeDetailVOs.length;j++){
                    // alert("trade_num:"+trade_num+"  trade_total:"+trade_total);
                    trade_num += trade_data[i].tradeDetailVOs[j].numofTrade;
                    trade_total += (trade_data[i].tradeDetailVOs[j].tradePrice * trade_data[i].tradeDetailVOs[j].numofTrade);
                }
                trade_table_data_item.date=trade_data[i].tradeDate.year+"-"+trade_data[i].tradeDate.month+"-"+trade_data[i].tradeDate.day;
                // alert("date: "+trade_table_data_item.date);
                trade_table_data_item.trade_amount = (trade_total).toFixed(2);
                trade_table_data_item.trade_num = trade_num;
                trade_table_data_item.profit = (trade_data[i].profit).toFixed(2);
                trade_table_data_item.profit_rate = ((trade_data[i].profit/total_capital)*100).toFixed(2) + "%";
                trade_table_data.push(trade_table_data_item);
                // alert("date"+trade_table_data_item.date+"total"+trade_table_data_item.trade_amount+" num:"+trade_table_data_item.trade_num);
            }
            // alert("object: "+JSON.stringify(trade_table_data));
            init_transaction_table(JSON.parse(JSON.stringify(trade_table_data)));
        },
        error:function (data) {
            alert("error:");
        }
    });
}
function test_strategy_with_factor(json_data) {
    // alert("params-->"+json_data);
    $.ajax({
        type:'post',
        url:'/Strategy/analyseWithFactor',
        // data:{name:"Strategy_Vol",capital:1000000,
        //     taxRate:0.001,baseCode:"000010",interval:7,start:"2015/01/01",end:"2015/06/01",vol:100},
        data:{arguments:json_data},
        success:function (data) {
            load_finish();
            trasaction_detail_data_all = data;
            // alert("seccess !");
            var cumRtnVOList = data.cumRtnVOList;
            var trade_data = data.tradeDataVOList;
            var compare_datas = [];
            var compare_base = [];
            var compare_test = [];
            for(var i=0;i<cumRtnVOList.length;i++){
                // alert(cumRtnVOList[i].baseValue);
                var str_date = cumRtnVOList[i].date.year+"-"+cumRtnVOList[i].date.month+"-"+cumRtnVOList[i].date.day;
                // alert(str_date);
                var date = new Date(str_date).getTime();
                compare_base.push([date,cumRtnVOList[i].baseValue]);
                compare_test.push([date,cumRtnVOList[i].testValue]);
                // alert("base "+compare_base+"test "+compare_test);
            }
            compare_datas.push(compare_base);
            compare_datas.push(compare_test);
            init_compare_chart(compare_datas);
            var trade_table_data = [];
            var trade_table_data_item;
            // document.getElementById('summary').innerHTML= JSON.stringify(trasaction_detail_data_all);
            for (var i=0;i<trade_data.length;i++){
                trade_table_data_item = new Object();
                var trade_total=0,trade_num=0;
                for(var j=0;j<trade_data[i].tradeDetailVOs.length;j++){
                    // alert("trade_num:"+trade_num+"  trade_total:"+trade_total);
                    trade_num += trade_data[i].tradeDetailVOs[j].numofTrade;
                    trade_total += (trade_data[i].tradeDetailVOs[j].tradePrice * trade_data[i].tradeDetailVOs[j].numofTrade);
                }
                trade_table_data_item.date=trade_data[i].tradeDate.year+"-"+trade_data[i].tradeDate.month+"-"+trade_data[i].tradeDate.day;
                // alert("date: "+trade_table_data_item.date);
                trade_table_data_item.trade_amount = (trade_total).toFixed(2);
                trade_table_data_item.trade_num = trade_num;
                trade_table_data_item.profit = (trade_data[i].profit).toFixed(2);
                trade_table_data_item.profit_rate = ((trade_data[i].profit/total_capital)*100).toFixed(2) + "%";
                trade_table_data.push(trade_table_data_item);
                // alert("date"+trade_table_data_item.date+"total"+trade_table_data_item.trade_amount+" num:"+trade_table_data_item.trade_num);
            }
            // alert("object: "+JSON.stringify(trade_table_data));
            init_transaction_diy_table(JSON.parse(JSON.stringify(trade_table_data)));
        },
        error:function (data) {
            alert("error:");
        }
    });
}
function init_factor_table(allStock) {
    var table = $('#factor_weight_table').DataTable( {

        data:allStock,
        lengthChange:false,
        pageLength:10,
        dom: 'lrtp',
        // "order":[[1,"asc"]],
        columns:[
            {data:'factor'},
            {data:'weight'}
        ],
        /*
         *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
         */
        // "autoWidth":false,
        //国际化
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "抱歉， 没有找到",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        }
    } );
}
function init_stock_pool_table(allStock) {
    var table = $('#stock_pool_table').DataTable( {

        data:allStock,
        lengthChange:false,
        pageLength:10,
        dom: 'lrtp',
        // "order":[[1,"asc"]],
        columns:[
            {data:'name'},
            {data:'code'}
        ],
        /*
         *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
         */
        // "autoWidth":false,
        //国际化
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "抱歉， 没有找到",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        }
    } );
}
function init_compare_chart(data) {
    // alert("[0]-->"+data[0]);
    // alert("[1]-->"+data[1]);
            // Create the chart
            $('#compare_chart').highcharts('StockChart', {


                rangeSelector : {
                    selected : 1
                },

                title : {
                    text : '基准/大盘回测对比'
                },

                series : [
                    {   color:'#666666',
                        name : '基准',
                        data : data[0],
                        tooltip: {
                        valueDecimals: 8
                        }
                    },
                    {
                        color:'#CC0000',
                        name : '回测',
                        data : data[1],
                        tooltip: {
                            valueDecimals: 8
                        }
                    }
                ]
            });
}
function init_transaction_table(data) {
    // alert("init_table"+data);
    // alert("new_data:"+new_data);
    var table = $('#transaction_table').DataTable( {
    
        // data:data,
        data:data,
        lengthChange:false,
        pageLength:10,
        dom: 'lrtp',
        // "order":[[1,"asc"]],
        columns:[
            {data:'date'},
            {data:'trade_amount'},
            {data:'trade_num'},
            {data:'profit'},
            {data:'profit_rate'}
        ],
        /*
         *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
         */
        // "autoWidth":false,
        //国际化
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "抱歉， 没有找到",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        }
    } );

    //下面是选择行
    $('#transaction_table tbody').on( 'click', 'tr', function () {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        var selected_row = table.row('.selected').index();
        var detail_data = trasaction_data_all.tradeDataVOList[selected_row].tradeDetailVOs;
        // alert(detail_data[0].code);
        // var current_detail_data = all_detail_data[selected_row]
        var detail_data_item;
        var detail_table_data = [];
        for(var i=0;i<detail_data.length;i++){
            detail_data_item = new Object();
            var date_data = trasaction_data_all.tradeDataVOList[selected_row].tradeDate;
            // alert(date_data.year);
            detail_data_item.date = date_data.year+"-"+date_data.month+"-"+date_data.day;
            detail_data_item.name = detail_data[i].codeName;
            if(detail_data[i].buyOrSell==false){
                detail_data_item.statu = "卖出";
            }else{
                detail_data_item.statu = "买入";
            }
            detail_data_item.trade_num = detail_data[i].numofTrade;
            detail_data_item.trade_amount = detail_data[i].tradePrice;
            detail_table_data.push(detail_data_item);
        }
        $('#transaction_detail_table').DataTable().clear().draw();
        $('#transaction_detail_table').DataTable().rows.add(JSON.parse(JSON.stringify(detail_table_data))).draw();
    } );
}
function init_transaction_diy_table(data) {
    // alert("init_table"+data);
    // alert("new_data:"+new_data);
    var table = $('#transaction_table').DataTable( {

        // data:data,
        data:data,
        lengthChange:false,
        pageLength:10,
        dom: 'lrtp',
        // "order":[[1,"asc"]],
        columns:[
            {data:'date'},
            {data:'trade_amount'},
            {data:'trade_num'},
            {data:'profit'},
            {data:'profit_rate'}
        ],
        /*
         *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
         */
        // "autoWidth":false,
        //国际化
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "抱歉， 没有找到",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        }
    } );

    //下面是选择行
    $('#transaction_table tbody').on( 'click', 'tr', function () {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        var selected_row = table.row('.selected').index();
        var detail_data = trasaction_detail_data_all.tradeDataVOList[selected_row].tradeDetailVOs;
        // alert(detail_data[0].code);
        // var current_detail_data = all_detail_data[selected_row]
        var detail_data_item;
        var detail_table_data = [];
        for(var i=0;i<detail_data.length;i++){
            detail_data_item = new Object();
            var date_data = trasaction_detail_data_all.tradeDataVOList[selected_row].tradeDate;
            // alert(date_data.year);
            detail_data_item.date = date_data.year+"-"+date_data.month+"-"+date_data.day;
            detail_data_item.name = detail_data[i].codeName;
            if(detail_data[i].buyOrSell==false){
                detail_data_item.statu = "卖出";
            }else{
                detail_data_item.statu = "买入";
            }
            detail_data_item.trade_num = detail_data[i].numofTrade;
            detail_data_item.trade_amount = (detail_data[i].tradePrice).toFixed(2);
            detail_table_data.push(detail_data_item);
        }
        $('#transaction_detail_table').DataTable().clear().draw();
        $('#transaction_detail_table').DataTable().rows.add(JSON.parse(JSON.stringify(detail_table_data))).draw();
    } );
}
function init_transaction_detail_table(allStock) {
    var table = $('#transaction_detail_table').DataTable( {

        data:allStock,
        lengthChange:false,
        pageLength:10,
        dom: 'lrtp',
        // "order":[[1,"asc"]],
        columns:[
            {data:'date'},
            {data:'statu'},
            {data:'trade_amount'},
            {data:'name'},
            {data:'trade_num'}
        ],
        /*
         *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
         */
        // "autoWidth":false,
        //国际化
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "抱歉， 没有找到",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        }
    } );
   
}