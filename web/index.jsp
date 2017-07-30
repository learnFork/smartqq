<%
    request.setAttribute("basePath", request.getContextPath());
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Group Message</title>
    <jsp:include page="js.jsp"/>
    <link href="css/index.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${basePath}/js/index.js"></script>
    <script type='text/javascript'>
        var ws;
        $(function () {
            $("body").append($("<h3>",{text:"社区QQ群击中关键字的信息将被同步到社区的这个页面,欢迎测试!"}));
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
        });
    </script>
</head>
<body>
<div id="tagscloud">
    <ul id="message"></ul>
<%--    <ul id="wechat">
        <span><img src="img/favicon.ico">&nbsp;<a href="#">tag1</a> </span>
        <p>aaaaaaa</p>
    </ul>--%>
</div>
</body>
</html>
