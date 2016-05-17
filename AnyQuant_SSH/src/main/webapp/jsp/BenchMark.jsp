<%--
  Created by IntelliJ IDEA.
  User: dsn
  Date: 2016/5/10
  Time: 4:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="../css/reset.css"> <!-- CSS reset -->
    <link rel="stylesheet" href="../css/style.css"> <!-- Resource style -->
    <script src="../js/modernizr.js"></script> <!-- Modernizr -->

    <title>Animated Page Transition #2 | CodyHouse</title>
</head>
<body>
<%@include file="../html/SideButton.html"%>
<main class="cd-main">
    <section class="cd-section services visible">
        <header>
            <div class="cd-title">
                <h2>BenchMark.jsp</h2>
                <span>Some text here</span>
            </div>

        </header>
    </section> <!-- .cd-section -->
</main> <!-- .cd-main -->


<div id="cd-loading-bar" data-scale="1" class="services"></div> <!-- lateral loading bar -->
<script src="../js/jquery-2.1.4.js"></script>
<script src="../js/velocity.min.js"></script>
<script src="../js/main.js"></script> <!-- Resource jQuery -->
</body>
</html>