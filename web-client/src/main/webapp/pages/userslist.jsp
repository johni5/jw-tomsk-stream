<%--@elvariable id="users" type="java.util.List<com.del.jws.server.db.User>"--%>

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

<div id="root" style="min-width: 1024px;">
    <div class="main-container">

        <%@include file="common/alerts.jsp" %>

        <div class="panel panel-default">
            <!-- Default panel contents -->
            <div class="panel-heading"><span class="lead">Список пользователей </span></div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Email</th>
                    <th>Логин</th>
                    <th>Роль</th>
                    <th>IP</th>
                    <sec:authorize access="hasRole('ADMIN')">
                        <th width="100"></th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.login}</td>
                        <td>${user.role}</td>
                        <td>${user.lastIp}</td>
                        <sec:authorize access="hasRole('ADMIN')">
                            <td>
                                <div class="btn-group btn-group-justified" style="width: 300px">
                                    <a href="<c:url value='/edit-user-${user.login}' />" class="btn btn-default btn-sm">Редактировать</a>
                                    <a href="<c:url value='/delete-user-${user.login}' />" class="btn btn-danger btn-sm">Удалить</a>
                                </div>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <sec:authorize access="hasRole('ADMIN')">
            <div class="well">
                <a href="<c:url value='//newuser' />" class="btn btn-primary">Новый пользователь</a>
            </div>
        </sec:authorize>

    </div>
</div>
</body>
</html>