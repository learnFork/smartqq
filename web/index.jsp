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
    <script src="easyui/jquery.min.js"></script>
<%--    <script src="easyui/jquery.easyui.min.js"></script>--%>

    <script type='text/javascript'>
        $(function () {
            //add latest 8 message
            getLatestGroupMessage();
            var ws;
            ws = new WebSocket("ws://" + location.host + "${basePath}/websocket/qqmessage");
            ws.onopen = function () {
                console.log("websocket connected...");
            };
            ws.onmessage = function (event) {

                //var msg = JSON.parse(event.data);
                var msg = event.data;
                if ($("#message").find("li").length > 5) {
                    $("ul li:eq(0)").remove();
                }
                $("#message").append(
                    $("<li>").append(
                        $("<span>").append(
                            $("<img class='img-thumbnail'>").attr('src', "img/favicon.ico")
                        )
                    ).append(
                        $("<p>", {text: msg})
                    ).append(
                        $("<HR style=\"color:#987cb9;\" width=\"100%\" color=#987cb9 SIZE=1>")
                    )
                );
            };
            ws.onclose = function (event) {
                console.log(event.data);
            };

            $(".home").addClass('active');
        });

        function getLatestGroupMessage() {
            $.ajax({
                type: "POST",
                url: "${basePath}/getLatestGroupMessage",
                //data: {},
                dataType: "json",
                success: function(data){
                    $.each(data.groupMessages, function(index, message){
                        $("#message").append(
                            $("<li>").append(
                                $("<span>").append(
                                    $("<img class='img-thumbnail'>").attr('src', "img/favicon.ico")
                                )
                            ).append(
                                $("<p>", {text: message.content})
                            ).append(
                                $("<HR style=\"color:#987cb9;\" width=\"100%\" color=#987cb9 SIZE=1>")
                            )
                        );
                    });
                }
            });

        }

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

    <ul id="message" class="message">
        <%--<li>
        <span>
          <img src="img/favicon.ico" class="img-thumbnail"/>
          <a href="index.jsp">Tag1</a>
        </span>
            <p>This is a test Placed at the end of the document so the pages load faster This is a test Placed at the
                end of the document so the pages load faster This is a test Placed at the end of the document so the
                pages load faster </p>
            <HR style="color:#987cb9;strength:10" width="100%" color=#987cb9 SIZE=1>
        </li>--%>

    </ul>

</div>

<script src="bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
<script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>


</body>
</html>
