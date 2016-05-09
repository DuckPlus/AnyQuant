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

    <%--tab上的小图标--%>
    <link rel="shortcut icon" href="/image/shoot_cut.png">
    <!-- DataTables CSS -->
    <%--<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css">--%>

    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>

    <!-- DataTables -->
    <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>

    <link rel="stylesheet" type="text/css" href="/css/StockList.css">

    <%--表头css???奇葩--%>
    <link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
        <%--tbody css--%>
    <link  rel="stylesheet" type="text/css" href ="http://cdn.datatables.net/plug-ins/28e7751dbec/integration/jqueryui/dataTables.jqueryui.css">


    <script type = "text/javascript" src = "http://cdn.datatables.net/plug-ins/28e7751dbec/integration/jqueryui/dataTables.jqueryui.js"></script>

    <title>Duck_stockList</title>
</head>
<body>

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
    <%--<tr>--%>
        <%--<td>股票名称</td>--%>
        <%--<td>股票代码</td>--%>
        <%--<td>开盘价</td>--%>
        <%--<td>最高价</td>--%>
        <%--<td>最低价</td>--%>
        <%--<td>收盘价</td>--%>
        <%--<td>成交量</td>--%>
        <%--<td>换手率</td>--%>
    <%--</tr>--%>
    </tfoot>

</table>
    <button class="button" id="button">Select Row</button>
</div>

<script>
    var allStock;
    $(document).ready(function () {
        $.ajax({
            type:'post',
            url:'/Stock/getStockList',
            contentType:'application/json;charset=utf-8',
            success:function (data){

                initTable(data);
            },
            error:function () {
                alert("请求失败");
            }
        });
            }
    );

//    alert(allStock);
        function initTable(allStock) {
            var table = $('#stock_list').DataTable( {

                data:allStock,
                columns:[
                    {data:'code'},
                    {data:'name'},
                    {data:'region'}
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
                    // 增加一列，包括删除和修改，同时将需要传递的数据传递到链接中
//                    {
//                        "targets": [4], // 目标列位置，下标从0开始
//                        "data": "name", // 数据列名
//                        "render": function(data, type, full) { // 返回自定义内容
//                            return "<button>加入自选</button>";
//                        }
//                    }
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
                if ( $(this).hasClass('selected') ) {
                    $(this).removeClass('selected');
                }
                else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
            } );

            $('#button').click( function () {
//                var value = table.row('.selected').$element.getVal();
                var selected_row = table.row('.selected').index();
                var value = table.cell(selected_row,0).data();

                alert(value);
            } );
        }
//    initTable();

</script>

</body>

</html>
