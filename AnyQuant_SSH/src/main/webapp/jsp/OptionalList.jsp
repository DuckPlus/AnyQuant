<%--
  Created by IntelliJ IDEA.
  User: dsn
  Date: 2016/5/8
  Time: 4:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.0.0.min.js" type="text/javascript"></script>
    <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.11/js/jquery.dataTables.js"></script>

    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.11/css/jquery.dataTables.css">
    //<link rel="stylesheet" type="text/css" href="/css/StockList.css">
    <title>Duck_optionalList</title>
</head>
<body>
<table id="stock_list">
    <thead >
    <tr>
        <td>股票名称</td>
        <td>股票代码</td>
        <td>地区</td>
        <td>最高价</td>
        <td>最低价</td>
        <td>收盘价</td>
        <td>成交量</td>
        <td>换手率</td>
    </tr>
    </thead>
</table>

<script>
    $.ajax({
        type:'post',
        url:'/TableView/getStockList',
        contentType:'application/json;charset=utf-8',
        success:function (data){
            alert("welcome to 自选股界面");
            initTable(data)

        }
    })
    function initTable(stock_data) {
        $(document).ready(function() {
            $('#stock_list').dataTable( {
                "processing": true,
                /*
                 *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
                 */
                "autoWidth":false,
                data:stock_data,
                columns:[
                    {data:'code'},
                    {data:'name'},
                    {data:'region'}
                ]
            } );
        } );

    }
    //    initTable();
</script>


</body>
</html>
