<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/8
  Time: 5:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <meta charset="utf-8">
    <title>Stock Statistic System</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <!--[if lte IE 8]><link rel="stylesheet" href="../css/responsive-nav.css"><![endif]-->
    <!--[if gt IE 8]><!--><link rel="stylesheet" href="../css/main_background.css"><!--<![endif]-->
    <script src="../../responsive-nav.js"></script>
    <%String pagename=request.getParameter("page");%>
</head>
<body>

<div role="navigation" id="foo" class="nav-collapse">
    <ul>
        <li class="active"><a href="#">股票列表</a></li>
        <li><a href="#">大盘列表</a></li>
        <li><a href="#">自选股</a></li>
    </ul>
</div>
<div style="position: absolute;left:150px;top: 10px">
    <jsp:include page="StockList.jsp"/>
</div>

<script>
    var navigation = responsiveNav("foo", {customToggle: ".nav-toggle"});
</script>


</body>
</html>
