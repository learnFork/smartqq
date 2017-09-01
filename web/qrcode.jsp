<%--
  Created by IntelliJ IDEA.
  User: alanturing
  Date: 8/28/17
  Time: 2:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    request.setAttribute("basePath", request.getContextPath());
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Scan</title>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                type: "POST",
                url: "${basePath}/qrcode",
                dataType: "json",
                success: function(data){

                }
            });
        })
    </script>
</head>
<body>

<script src="bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
</body>
</html>
