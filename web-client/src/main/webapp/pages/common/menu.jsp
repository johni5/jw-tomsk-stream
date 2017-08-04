<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="/" />">JW Tomsk Stream</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
            <ul class="nav navbar-nav">
                <sec:authorize access="hasAnyRole('ADMIN', 'SUBSCRIBER')">
                    <li><a href="<c:url value="//meetings" />">Программа</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ADMIN', 'PUBLISHER')">
                    <li><a href="<c:url value="//publish" />">Трансляция</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="<c:url value="//list" />">Пользователи</a></li>
                    <li><a href="<c:url value="//admin" />">Админка</a></li>
                </sec:authorize>
            </ul>
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="<c:url value="//logout" />">Выйти</a></li>
                </ul>
            </sec:authorize>
        </div>
    </div>
</nav>

