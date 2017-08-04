<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Регистрация</title>
    <%@include file="common/plugins.jsp" %>
</head>

<body>

<%@include file="common/menu.jsp" %>


<div id="root">
    <div class="main-container">
        <div class="form-area panel panel-default">
            <div class="panel-body">

                <form:form method="POST" modelAttribute="user" class="form-horizontal">

                    <form:input type="hidden" path="id" id="id"/>

                    <c:if test="${not empty success}">
                        <div class="alert alert-dismissible alert-success">

                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <p>${success}</p>
                        </div>
                    </c:if>

                    <fieldset>
                        <legend>Форма регистрации пользователя</legend>

                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable" for="firstName">Имя</label>
                            <div class="col-md-7">
                                <form:input type="text" path="firstName" id="firstName" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="firstName" class="help-inline"/>
                                </div>
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable" for="lastName">Фамилия</label>
                            <div class="col-md-7">
                                <form:input type="text" path="lastName" id="lastName" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="lastName" class="help-inline"/>
                                </div>
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable" for="login">Логин</label>
                            <div class="col-md-7">
                                <c:set value="login" var="path"/>
                                <c:choose>
                                    <c:when test="${edit}">
                                        <form:input type="text" path="${path}" id="login" class="form-control input-sm"
                                                    disabled="true"/>
                                    </c:when>
                                    <c:otherwise>
                                        <form:input type="text" path="${path}" id="login"
                                                    class="form-control input-sm"/>
                                        <div class="has-error">
                                            <form:errors path="login" class="help-inline"/>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable" for="password">Пароль</label>
                            <div class="col-md-7">
                                <form:input type="password" path="password" id="password"
                                            class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="password" class="help-inline"/>
                                </div>
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable" for="email">Email</label>
                            <div class="col-md-7">
                                <form:input type="text" path="email" id="email" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="email" class="help-inline"/>
                                </div>
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable" for="role">Роль</label>
                            <div class="col-md-7">
                                <form:select path="role" items="${roles}" itemLabel="name" multiple="false"
                                             class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="role" class="help-inline"/>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-lg-offset-2">
                                <c:choose>
                                    <c:when test="${edit}">
                                        <input type="submit" value="Обновить" class="btn btn-primary"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" value="Регистрация" class="btn btn-primary"/>
                                    </c:otherwise>
                                </c:choose>
                                <a href="<c:url value='//list' />" class="btn btn-default">Отмена</a>
                            </div>
                        </div>

                    </fieldset>

                </form:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>