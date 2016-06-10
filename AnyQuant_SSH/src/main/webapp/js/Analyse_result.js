/**
 * Created by duanzhengmou on 6/8/16.
 */

$(document).ready(function () {

    var data1 = '{"name":"Strategy_Vol","capital":1000000,"taxRate":0.01,"baseCode":"000010","interval":7,"start":"2015-01-01","end":"2015-06-01","vol":30}';
    var json_data = JSON.parse(data1);
    var data2 = '{"codes":"sh6000010,sh6000011,sh6000012,sh6000013,sh6000014","capital":1000000,"taxRate":0.01,"baseCode":"000010","interval":7,"start":"2015-01-01","end":"2015-06-01","factorWeight":"?"}';
    var json_data2 = JSON.parse(data2);
    var url = location.href.split("?")[1];
    draw_compare_chart(url);
    // alert(url);
    // alert(json_data.name);
    // test_specific_strategy(data1);
    // test_strategy_with_factor(json_data2);
    // alert("ready");
    var data=
        [
        {
            "factor":"FACTOR2",
            "weight":0.2
        },
        {
           "factor":"FACTOR1",
           "weight":0.5
        }, 
            {
                "factor":"FACTOR3",
                "weight":0.3
            }
    ];
    var data2 = 
    [
        {"stock_name":"stock 1","stock_code":"sh600001"},
        {"stock_name":"stock 2","stock_code":"sh600002"},
        {"stock_name":"stock 1","stock_code":"sh600001"},
        {"stock_name":"stock 2","stock_code":"sh600002"},
        {"stock_name":"stock 1","stock_code":"sh600001"},
        {"stock_name":"stock 2","stock_code":"sh600002"},
        {"stock_name":"stock 1","stock_code":"sh600001"},
        {"stock_name":"stock 2","stock_code":"sh600002"},
        {"stock_name":"stock 1","stock_code":"sh600001"},
        {"stock_name":"stock 2","stock_code":"sh600002"},
        {"stock_name":"stock 3","stock_code":"sh600003"}
    ];
    
    init_factor_table(data);
    init_stock_pool_table(data2);
    init_transaction_table();
    init_transaction_detail_table();
    // $.getJSON('https://www.highcharts.com/samples/data/jsonp.php?filename=aapl-c.json&callback=?', function (data) {
    //     init_compare_chart(data);
    // });//
});

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
    document.getElementById('start_fund').innerHTML = data_obj.capital;
    document.getElementById('begin_time').innerHTML = data_obj.start;
    document.getElementById('end_time').innerHTML = data_obj.end;
    document.getElementById('trade_rate').innerHTML = data_obj.taxRate;
    document.getElementById('base_bench').innerHTML = data_obj.baseCode;
    document.getElementById('interval').innerHTML = data_obj.interval;
    var json_data = JSON.stringify(data_obj);
    alert(json_data);
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
            alert("seccess !");
            // alert("->"+data.cumRtnVOList[0].baseValue);
            alert("length->"+data.cumRtnVOList.length);
            var cumRtnVOList = data.cumRtnVOList;
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
                alert("base "+compare_base+"test "+compare_test);
            }
            compare_datas.push(compare_base);
            compare_datas.push(compare_test);
            // alert("[test] "+compare_test);
            // alert("total "+compare_datas);
            init_compare_chart(compare_datas);
        },
        error:function (data) {
            alert("error:");
            // var result = [];
            // for(var x in data){
            //     result.push([x,data[x]]);
            // }
        }
    });
}
function test_strategy_with_factor(json_data) {
    $.ajax({
        type:'post',
        url:'/Strategy/analyseWithFactor',
        // data:{name:"Strategy_Vol",capital:1000000,
        //     taxRate:0.001,baseCode:"000010",interval:7,start:"2015/01/01",end:"2015/06/01",vol:100},
        data:json_data,
        success:function (data) {
            alert("seccess !");
        },
        error:function (data) {
            alert("error:");
            var result = [];
            for(var x in data){
                result.push([x,data[x]]);
            }
            // for(var i=18;i<30;i++) {
            //     alert(result[i]);
            // }
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
            {data:'stock_name'},
            {data:'stock_code'}
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
    alert("[0]-->"+data[0]);
    alert("[1]-->"+data[1]);
            // Create the chart
            $('#compare_chart').highcharts('StockChart', {


                rangeSelector : {
                    selected : 1
                },

                title : {
                    text : 'AAPL Stock Price'
                },

                series : [
                    {
                        name : '基准',
                        data : data[0],
                        tooltip: {
                        valueDecimals: 8
                        }
                    },
                    {
                        name : '回测',
                        data : data[1],
                        tooltip: {
                            valueDecimals: 8
                        }
                    }
                ]
            });
}
function init_transaction_table(allStock) {
    var table = $('#transaction_table').DataTable( {

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
function init_transaction_detail_table(allStock) {
    var table = $('#transaction_detail_table').DataTable( {

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