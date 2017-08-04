<%--@elvariable id="meetings" type="java.util.List<com.del.jws.server.db.Meeting>"--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:setLocale value="ru_RU"/>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Встречи</title>
    <%@include file="common/plugins.jsp" %>
    <script src="<c:url value='/static/plugins/swfobject/swfobject.js' />"></script>
</head>

<body>

<%@include file="common/menu.jsp" %>

<div id="root">
    <div class="main-container">

        <%@include file="common/alerts.jsp" %>

        <c:if test="${empty meetings}">
            <div class="alert alert-dismissible alert-warning">
                <h4>На данный момент трансляция не ведется!</h4>
            </div>
        </c:if>


        <c:if test="${not empty meetings}">

            <table class="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Начало трансляции</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetings}" var="meeting" varStatus="st">
                    <tr>
                        <td>${st.index + 1}</td>
                        <td>
                            <a href="<c:url value="//subscribe-${meeting.id}" />">
                                <fmt:formatDate value="${meeting.beginDate}" pattern="HH:mm dd/MM/yyyy"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>


        </c:if>

    </div>
</div>


</body>
</html>