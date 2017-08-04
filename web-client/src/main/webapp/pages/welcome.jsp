<%--@elvariable id="user" type="com.del.jws.server.db.User"--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список пользователей</title>
    <%@include file="common/plugins.jsp" %>
</head>

<body>

<%@include file="common/menu.jsp" %>

<div id="root">
    <div class="main-container">
        <div class="well">
            <H3>${user.firstName} ${user.lastName}</H3>
            <h4>Добро пожаловать!</h4>
        </div>
    </div>
</div>


</body>
</html>