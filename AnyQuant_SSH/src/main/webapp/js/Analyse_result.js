/**
 * Created by duanzhengmou on 6/8/16.
 */

$(document).ready(function () {

    var data1 = '{"name":"Strategy_Vol","capital":1000000,"taxRate":0.01,"baseCode":"000010","interval":7,"start":"2015-01-01","end":"2015-06-01","vol":30}';
    var json_data = JSON.parse(data1);
    var data2 = '{"codes":"sh6000010,sh6000011,sh6000012,sh6000013,sh6000014","capital":1000000,"taxRate":0.01,"baseCode":"000010","interval":7,"start":"2015-01-01","end":"2015-06-01","factorWeight":"?"}';
    var json_data2 = JSON.parse(data2);
    alert(json_data.name);
    test_specific_strategy(data1);
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
    $.getJSON('https://www.highcharts.com/samples/data/jsonp.php?filename=aapl-c.json&callback=?', function (data) {
        init_compare_chart(data)
    });//
});
function test_specific_strategy(json_data) {
    $.ajax({
        type:'post',
        url:'/Strategy/analyseWithSpecificStrategy',
        // data:{name:"Strategy_Vol",capital:1000000,
        //     taxRate:0.001,baseCode:"000010",interval:7,start:"2015/01/01",end:"2015/06/01",vol:100},
        data:{json_data:json_data},
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
        
            // Create the chart
            $('#compare_chart').highcharts('StockChart', {


                rangeSelector : {
                    selected : 1
                },

                title : {
                    text : 'AAPL Stock Price'
                },

                series : [{
                    name : 'AAPL',
                    data : data,
                    tooltip: {
                        valueDecimals: 2
                    }
                }]
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