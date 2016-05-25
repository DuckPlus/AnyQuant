<%@ page import="java.util.Enumeration" %>
<!--Created by IntelliJ IDEA.-->
  <!--User: duanzhengmou-->
  <!--Date: 5/6/16-->
  <!--Time: 3:57 PM-->
  <!--To change this template use File | Settings | File Templates.&#45;&#45;%>-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>


    <link rel="shortcut icon" href="/image/short_cut.png">
    <!--fonts-->
    <link href='https://fonts.googleapis.com/css?family=Titillium+Web:200' rel='stylesheet' type='text/css'>
    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>

    <!-- DataTables -->
    <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
    <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.7/css/jquery.dataTables.css">

    <link rel="stylesheet" type="text/css" href="/css/StockList.css">

    <!--表头css???奇葩-->
    <!--<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">-->
        <!--tbody css-->
    <!--<link  rel="stylesheet" type="text/css" href ="http://cdn.datatables.net/plug-ins/28e7751dbec/integration/jqueryui/dataTables.jqueryui.css">-->


    <!--<script type = "text/javascript" src = "http://cdn.datatables.net/plug-ins/28e7751dbec/integration/jqueryui/dataTables.jqueryui.js"></script>-->

        <!--highChart 的js支持-->
        <script src="https://code.highcharts.com/stock/highstock.js"></script>
        <script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
    <!--动画的css支持-->
    <link rel="stylesheet" type="text/css" href="/css/animate.css">


    <title>Duck_stockList</title>
</head>
<body style=";margin-left: 0px;margin-left: 0px;margin-top: 0px;margin-right: 0px">
<div id="curtain" style="background-color: #83b0b9; " >
    <div id="stock_h1" class="animated fadeInLeft" style="font-family: 'Titillium Web', sans-serif">股票列表<br>Stock List</div>
</div>
<script>
    function changeHeight() {
        <% Enumeration attrs = session.getAttributeNames();
           while(attrs.hasMoreElements()){
           System.out.println("element:"+attrs.nextElement());
           }
        %>
        document.getElementById("curtain").style.height=window.screen.height;

    }
    window.onload=changeHeight;

</script>


<div id="stock_list_container" class="stock_list_pane">
<table id="stock_list" class="display">
    <thead>
        <tr>
            <th>股票名称</th>
            <th>股票代码</th>
            <th>开盘价</th>
            <th>最高价</th>
            <th>最低价</th>
            <th>收盘价</th>
            <th>成交量</th>
            <th>换手率</th>
        </tr>

    </thead>
    <tbody>
    </tbody>

    <tfoot>
    </tfoot>

</table>
    <!--<button class="button" id="button">Select Row</button>-->
</div>

<script>
    var allStock;
    $(document).ready(function () {
        $.ajax({
            type:'post',
            url:'/Stock/getStockDataList',
            contentType:'application/json;charset=utf-8',
            success:function (data){
//                alert("success");
                  initTable(data);
            },
            error:function () {
                alert("请求失败");
            }
        });
            }
    );

        /*
         *初始化table的方法,包括样式的配置
         */
        function initTable(allStock) {
            var table = $('#stock_list').DataTable( {

                data:allStock,
                columns:[
                    {data:'name'},
                    {data:'code'},
                    {data:'open'},
                    {data:'high'},
                    {data:'low'},
                    {data:'close'},
                    {data:'turnoverVol'},
                    {data:'changeRate'}
                ],
//                "scrollY":"500px",
//                "scrollY": "50%",
//                "scrollCollapse": true,
//                "padding":false,
                /*
                 *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
                 */
                "autoWidth":false,
                "columnDefs": [
                    // 将name列变为红色
                    {
                        "targets": [0], // 目标列位置，下标从0开始
                        "data": "name", // 数据列名
                        "render": function(data, type, full) { // 返回自定义内容
                            return "<span style='color:red;'>" + data + "</span>";
                        }
                    }
                ],
                //国际化
                "oLanguage": {
                    "sProcessing": "疯狂加载数据中.",
                    "sLengthMenu": "每页显示 _MENU_ 条记录",
                    "sZeroRecords": "抱歉， 没有找到",
                    "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                    "sInfoEmpty": "没有数据",
                    "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
                    "sZeroRecords": "没有检索到数据",
                    "sSearch": "模糊查询:  ",
                    "oPaginate": {
                        "sFirst": "首页",
                        "sPrevious": "前一页",
                        "sNext": "后一页",
                        "sLast": "尾页"
                    }
                },


            } );

//以下是关于选择行的代码

            $('#stock_list tbody').on( 'click', 'tr', function () {
//                if ( $(this).hasClass('selected') ) {
//                    $(this).removeClass('selected');
//                }
//                else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                var selected_row = table.row('.selected').index();
                var value = table.cell(selected_row,1).data();
                showStockChart(value);
//                }
            } );
        }
//    initTable();

</script>

<div id="duck_stock_chart" style="background-color: #2b542c;margin-top: 0px;height: 400px;">



</div>
<script>
    function showStockChart(stockCode) {
        $.ajax({
           url:"/Stock/getStockDataListByTime",

            type:'get',
            data:{code:stockCode,start:'2016-01-01',end:'2016-01-31'},
            success:function (data) {
                var ohlc = [],
                        volume = [],
                        dataLength = data.length,
                // set the allowed units for data grouping
                        groupingUnits = [[
                            'week',                         // unit name
                            [1]                             // allowed multiples
                        ], [
                            'month',
                            [1, 2, 3, 4, 6]
                        ]],

                        i = 0;
                for (i; i < dataLength; i += 1) {

                    ohlc.push([
                        new Date(data[i].date).getTime(),
                        data[i].open,
                        data[i].high,
                        data[i].low,
                        data[i].close
                    ]);
//                alert("loaded ohlc: open"+data[i].open+"high"+data[i].high+"low"+data[i].low+"close"+data[i].close);
                    volume.push([
//                    data[i][0], // the date
//                    data[i][5] // the volume
                        new Date(data[i].date).getTime(),
                        data[i].turnoverVol
                    ]);
//                alert("loaded volume: date:"+new Date(data[i].date).getTime()+ "vol 1000");
                }


                // create the chart
                $('#duck_stock_chart').highcharts('StockChart', {

                    plotOptions: {
                        candlestick: {
                            color: 'green',
                            upColor: 'red'
                        }
                    },
                    rangeSelector: {
                        selected: 1
                    },

                    title: {
                        text: stockCode+' AAPL Historical'
                    },

                    yAxis: [{
                        labels: {
                            align: 'right',
                            x: -3
                        },
                        title: {
                            text: 'OHLC'
                        },
                        height: '60%',
                        lineWidth: 2
                    }, {
                        labels: {
                            align: 'right',
                            x: -3
                        },
                        title: {
                            text: 'Volume'
                        },
                        top: '65%',
                        height: '35%',
                        offset: 0,
                        lineWidth: 2
                    }],

                    series: [{
                        type: 'candlestick',
                        name: 'AAPL',
                        data: ohlc,
                        dataGrouping: {
                            units: groupingUnits
                        }
                    }, {
                        type: 'column',
                        name: 'Volume',
                        data: volume,
                        yAxis: 1,
                        dataGrouping: {
                            units: groupingUnits
                        }
                    }]
                });
            },
            error:function () {
                alert("StockList_d.html!wrong!");
            }

        });

    }
</script>

</body>

</html>
