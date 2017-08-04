<%--<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Доступ запрещен</title>
    <%@include file="common/plugins.jsp" %>
</head>
<body>
<%@include file="common/menu.jsp" %>


<div id="root">
    <div class="main-container">
        <div class="alert alert-dismissible alert-warning">
            <h4>Проблемы с доступом!</h4>
            <p>
                У вас нет прав для доступа к этой странице!
            </p>
        </div>
    </div>
</div>

</body>
</html>