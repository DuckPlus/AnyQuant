<%--
  Created by IntelliJ IDEA.
  User: duanzhengmou
  Date: 5/17/16
  Time: 9:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>test1</title>
    <script type="text/javascript">
        function getparm()
        {
            var url=location.href;
            var tmp=url.split("?")[1];
            var parm=tmp.split("=")[1];
            alert(parm);
        }

    </script>
</head>
<body>
<label id="label1" >page test4</label>
<br><br>
<input type="button" id="bt1" value="get parm" onclick="getparm()">
<br><br>
</body>
</html>