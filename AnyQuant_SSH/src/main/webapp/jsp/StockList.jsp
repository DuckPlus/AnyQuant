<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.swing.text.TabableView" %><%--
  Created by IntelliJ IDEA.
  User: duanzhengmou
  Date: 5/6/16
  Time: 3:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.0.0.min.js" type="text/javascript"></script>
    <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.11/js/jquery.dataTables.js"></script>

    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.11/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="/css/StockList.css">
    <title>Duck_stockList</title>
</head>
<body>
<%

%>
<table id="stock_list">
    <thead >
        <tr>
            <td>股票名称</td>
            <td>股票代码</td>
            <td>开盘价</td>
            <td>最高价</td>
            <td>最低价</td>
            <td>收盘价</td>
            <td>成交量</td>
            <td>换手率</td>
        </tr>
    </thead>
    <tbody>

    </tbody>

    <tfoot>


    </tfoot>

</table>

<script>
        $.ajax({
            type:'post',
            url:'/TableView/getStockList',
            contentType:'application/json;charset=utf-8',
            success:function (data){
                alert("welcome");
//                var jsonData = eval('('+data+')');
//                var result = "[";
//                for (var i=0;i<jsonData.length;i++){
//                    result+="[";
//                    var item = jsonData[i];
//                    result+="\"";
//                    result = result + item.code + "\",\"";
//                    result = result + item.name + "\"]";
//                    if (i!=jsonData.length-1) result+=",";
//                }
//                result+="]";
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
