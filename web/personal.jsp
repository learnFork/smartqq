<%--
  Created by IntelliJ IDEA.
  User: alanturing
  Date: 8/29/17
  Time: 4:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    request.setAttribute("basePath", request.getContextPath());
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>personal</title>
    <script src="/easyui/jquery.min.js"></script>
    <script type='text/javascript'>
        $(function () {
            $.getJSON("${basePath}/query/personalmessage/"+${id},function (messages) {
                $.each(messages,function (index, message) {
                    $("#group-message").append(
                        $("<li>").append(
                            $("<p>", {text: message.content})
                        )
                    )
                })
            });

        });
    </script>
</head>
<body>
<div class="container">
    <ul id="group-message">

    </ul>
</div>

<script src="/bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
<script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
