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

    <title>Alan Turing</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/messagebox.css" rel="stylesheet">
    <style>

    </style>

    <script src="easyui/jquery.min.js"></script>
    <script src="easyui/jquery.easyui.min.js"></script>

    <script type='text/javascript'>
        $(function () {
            var ws;
            ws = new WebSocket("ws://" + location.host + "${basePath}/groupmessage");
            ws.onopen = function () {
                console.log("websocket connected...");
            };
            ws.onmessage = function (event) {
                var msg = JSON.parse(event.data);
                var color = '#' + msg.groupId.substring(0, 3) + msg.userId.substring(0, 3);
                if ($("#message li").length > 5) {
                    $("ul li:eq(0)").remove();
                }
                $("#message").append(
                    $("<li>").css("background-color","#"+color).append(
                        $("<span>").append(
                            $("<img class='img-thumbnail'>").attr('src', "img/favicon.ico")
                        ).append(
                            $("<a>", {href: "#", text: msg.groupId + "|" + msg.userId})
                        )
                    ).append(
                        $("<p>", {text: msg.content})
                    ).append(
                        $("<HR style=\"color:#987cb9;strength:10\" width=\"100%\" color=#987cb9 SIZE=1>")
                    )
                );
            };
            ws.onclose = function (event) {
                console.log(event.data);
            };

            $(".home").addClass('active');
        });


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
            <a href="http://helloalanturing.com" class="navbar-brand">HelloAlanTuring</a>
        </div>
        <!--menu list-->
        <nav class="collapse navbar-collapse" id="mainNavBar">
            <ul class="nav navbar-nav">
                <li class="home"><a href="index.jsp">Home</a></li>
                <li class="groupMessage"><a href="group.jsp">GroupMessage</a></li>
            </ul>
        </nav>
    </div>
</div>

<div class="container">

    <ul id="message" class="message">
<%--        <li>
        <span>
          <img src="img/favicon.ico" class="img-circle"/>
          <a href="sldfjsldfj">Tag1</a>
          <a href="sldfjsldfj">Tag2</a>
          <a href="sldfjsldfj">Tag3</a>
        </span>
            <p>This is a test Placed at the end of the document so the pages load faster This is a test Placed at the
                end of the document so the pages load faster This is a test Placed at the end of the document so the
                pages load faster </p>
            <HR style="color:#987cb9;strength:10" width="100%" color=#987cb9 SIZE=1>
        </li>
        <li>
        <span>
          <img src="img/favicon.ico" class="img-rounded"/>
          <a href="sldfjsldfj">Tag1</a>
          <a href="sldfjsldfj">Tag2</a>
          <a href="sldfjsldfj">Tag3</a>
        </span>
            <p>This is a test Placed at the end of the document so the pages load faster This is a test Placed at the
                end of the document so the pages load faster This is a test Placed at the end of the document so the
                pages load faster </p>
            <HR style="color:#987cb9;strength:10" width="100%" color=#987cb9 SIZE=1>
        </li>--%>
        <li>
        <span>
          <img src="img/favicon.ico" class="img-thumbnail"/>
          <a href="sldfjsldfj">Tag1</a>
          <a href="sldfjsldfj">Tag2</a>
          <a href="sldfjsldfj">Tag3</a>
        </span>
            <p>This is a test Placed at the end of the document so the pages load faster This is a test Placed at the
                end of the document so the pages load faster This is a test Placed at the end of the document so the
                pages load faster </p>
            <HR style="color:#987cb9;strength:10" width="100%" color=#987cb9 SIZE=1>
        </li>
        <%--<li>
        <span>
          <img src="img/favicon.ico" class="img-responsive"/>
          <a href="sldfjsldfj">Tag1</a>
          <a href="sldfjsldfj">Tag2</a>
          <a href="sldfjsldfj">Tag3</a>
        </span>
            <p>This is a test Placed at the end of the document so the pages load faster This is a test Placed at the
                end of the document so the pages load faster This is a test Placed at the end of the document so the
                pages load faster </p>
            <HR style="color:#987cb9;strength:10" width="100%" color=#987cb9 SIZE=1>
        </li>--%>

    </ul>

</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
<script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
