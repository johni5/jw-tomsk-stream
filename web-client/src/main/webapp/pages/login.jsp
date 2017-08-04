<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Авторизация</title>
    <%@include file="common/plugins.jsp" %>
</head>

<body>
<div id="root">
    <div class="main-container">
        <div class="form-area panel panel-default">
            <div class="panel-body">
                <c:url var="loginUrl" value="/login"/>

                <form action="${loginUrl}" method="post" class="form-horizontal">

                    <c:if test="${param.error != null}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <p>Неправильный пользоватетель или пароль</p>
                        </div>
                    </c:if>
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-dismissible alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <p>Вы успешно вышли из системы.</p>
                        </div>
                    </c:if>

                    <fieldset>
                        <legend>Вход</legend>
                        <div class="form-group">
                            <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
                            <div class="col-lg-10">
                                <input class="form-control" id="username" name="login" placeholder="Введите ваш логин"
                                       type="text" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>
                            <div class="col-lg-10">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="Введите ваш пароль" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-lg-10">
                                <div class="checkbox">
                                    <label><input type="checkbox" id="rememberme" name="remember-me"> Запомнить меня</label>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="form-group">
                            <div class="col-lg-10 col-lg-offset-2">
                                <button type="submit" class="btn btn-primary">Войти</button>
                            </div>
                        </div>

                    </fieldset>
                </form>

            </div>
        </div>
    </div>
</div>

</body>
</html>