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
    <title>group</title>
    <script src="${basePath}/easyui/jquery.min.js"></script>
    <script type='text/javascript'>
        $(function () {
            $("#groupinfo").append(
                $("<p>", {
                    text: '${group.name}-------${group.id}-----${group.code}'
                }));

        });
    </script>
</head>
<body>
<div class="container">
    <div id="groupinfo">
    </div>
</div>

<script src="${basePath}/bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
<script src="${basePath}/bootstrap-3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
