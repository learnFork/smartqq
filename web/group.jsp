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
                if ($("#message li").length > 5) { $("ul li:eq(0)").remove(); }
                $("#message").append(
                    $("<li>").append(
                        $("<span>").append(
                            $("<img>").attr('src',"img/favicon.ico")
                        ).append(
                            $("<a>",{href: "#",text:msg.groupId + "|" + msg.userId})
                        )
                    ).append(
                        $("<p>",{text: msg.content})
                    )
                );
            };
            ws.onclose = function (event) {
                console.log(event.data);
            };

            $(".groupMessage").addClass('active');
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
      <ul id="message">

      </ul>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
    <script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
  </body>
</html>
