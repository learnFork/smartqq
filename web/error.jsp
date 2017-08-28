<%
    request.setAttribute("basePath", request.getContextPath());
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Jcker</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/messagebox.css" rel="stylesheet">
    <style>

    </style>


    <script type='text/javascript'>
    </script>
</head>

<body>
<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <!--Logo-->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNavBar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="http://jcker.org" class="navbar-brand">Jcker</a>
        </div>
        <!--menu list-->
        <nav class="collapse navbar-collapse" id="mainNavBar">
            <ul class="nav navbar-nav">
                <li class="home"><a href="http://jcker.org">Home</a></li>
                <%--<li class="groupMessage"><a href="group.jsp">Group</a></li>--%>
            </ul>
        </nav>
    </div>
</div>

<div class="container">

    <h1>Already Login QQ: ${userInfo.uin}</h1>

</div>

<script src="bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
<script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
