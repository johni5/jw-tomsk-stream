<%--@elvariable id="error" type="java.lang.String>"--%>
<%--@elvariable id="success" type="java.lang.String>"--%>
<%--@elvariable id="warn" type="java.lang.String>"--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${not empty error}">
    <div class="alert alert-dismissible alert-error">

        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <p>${error}</p>
    </div>
</c:if>

<c:if test="${not empty success}">
    <div class="alert alert-dismissible alert-success">

        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <p>${success}</p>
    </div>
</c:if>

<c:if test="${not empty warn}">
    <div class="alert alert-dismissible alert-warning">

        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <p>${warn}</p>
    </div>
</c:if>

