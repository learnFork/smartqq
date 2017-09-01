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
    <style type="text/css">
        #qrcode {
            position: absolute;
            left: 338px;
            top: 91px;
            width: 167px;
            height: 167px;
            border: 1px solid #7a7a7a;
        }

    </style>
    <script src="easyui/jquery.min.js"></script>
    <script type='text/javascript'>
        $(function () {
            $.getJSON("${basePath}/start", function (messages) {
                $.each(messages, function (index, message) {
                    $("#message").append(
                        $("<li>").append(
                            $("<span>").append(
                                $("<img class='img-thumbnail' src='img/favicon.ico'>")
                            )
                        ).append($("<span>", {text: message.nickname}))
                            .append(
                            $("<p>", {text: message.content})
                        )
                    );
                });
            });
            var ws = new WebSocket("ws://" + location.host + "${basePath}/websocket/qqmessage");
            ws.onopen = function () {
                console.log("websocket connected...");
            };
            ws.onmessage = function (event) {
                var message = JSON.parse(event.data);
                $("#message").append(
                    $("<li>").append(
                        $("<span>").append(
                            $("<img class='img-thumbnail' src='img/favicon.ico'>")
                        )
                    ).append($("<span>", {text: message.nickname}))
                        .append(
                            $("<p>", {text: message.content})
                        ));
            };

            ws.onclose = function (event) {
                console.log(event.data);
            };

            $(".home").addClass('active');

            /*$("#qrcode").hide();

            $("#scan").click(function () {
                $("#qrcode").fadeIn("slow");
            });
            $("#qrcode").click(function () {
                $("#qrcode").fadeOut("slow");
            });*/

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
            <a href="http://jcker.org" class="navbar-brand">Jcker</a>
        </div>
        <!--menu list-->
        <nav class="collapse navbar-collapse" id="mainNavBar">
            <ul class="nav navbar-nav">
                <li class="home"><a href="${basePath}/">Home</a></li>
                <li class="group" id="login"><a href="${basePath}/grouplist">Group</a></li>
                <li class="personal" id="scan"><a href="${basePath}/personal">Personal</a></li>
            </ul>
        </nav>
    </div>
</div>

<div class="container">
    <ul id="message" class="message">
    </ul>
</div>
<%--<div id="qrcode"><img src="${basePath}/qrcode"></div>--%>

<script src="bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
<script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>


</body>
</html>
