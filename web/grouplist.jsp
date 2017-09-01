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
    <!-- Bootstrap core CSS -->
    <link href="bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">

    <script src="${basePath}/easyui/jquery.min.js"></script>
    <script type='text/javascript'>
        $(function () {
            $.getJSON("${basePath}/getgrouplist", function (grouplist) {
                $.each(grouplist, function (index, group) {
                    $("#group-message").append(
                        $("<li>").append(
                            $("<a>", {
                                text: group.name + '-----' + group.id + "---------" + group.code,
                                href: '${basePath}/group/' + group.id
                            })
                        )
                    )
                })
            });

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
    <ul id="group-message">

    </ul>
</div>

<script src="${basePath}/bootstrap-3.3.7/js/jquery.min-2.2.4.js"></script>
<script src="${basePath}/bootstrap-3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
