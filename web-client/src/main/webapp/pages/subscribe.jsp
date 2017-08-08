<%--@elvariable id="guid" type="java.lang.String"--%>
<%--@elvariable id="host" type="java.lang.String"--%>
<%--@elvariable id="nsName" type="java.lang.String"--%>
<%--@elvariable id="clientType" type="java.lang.String"--%>
<%--@elvariable id="redirectCloseUrl" type="java.lang.String"--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Программа</title>
    <%@include file="common/plugins.jsp" %>
    <script src="<c:url value='/static/plugins/swfobject/swfobject.js' />"></script>
    <script>
        var fv = {};
        var attributes = {
            id: "BridgeMovie",
            name: "BridgeMovie"
        };
        var params = {
            allowScriptAccess: 'sameDomain',
            swLiveConnect: 'true',
            scale: 'noscale'
        };
        fv.guid = '${guid}';
        fv.host = '${host}';
        fv.nsName = '${nsName}';
        fv.clientType = '${clientType}';

        fv.bufferTime = '2'; // 0-100 step 0.1

        swfobject.embedSWF(
                "<c:url value='/static/flash/JwsReceiver.swf' />",
                "client",
                "150", "20", "9.0.0",
                "expressInstall.swf",
                fv, params, attributes
        );

        function getMovie() {
            var M$ = navigator.appName.indexOf("Microsoft") != -1;
            return (M$ ? window : document)["BridgeMovie"]
        }

        jQuery(document).ready(function () {
            $('#play').click(function () {
                getMovie().play();
                return false;
            });
            $('#pause').click(function () {
                getMovie().pause();
                return false;
            });
        });

        function streamBroadcastClose(){
            window.location = "${redirectCloseUrl}";
        }

    </script>
</head>

<body>

<%@include file="common/menu.jsp" %>

<div id="root">
    <div class="main-container">

        <%@include file="common/alerts.jsp" %>

        <div style="padding: 10px;">
            <div id="client" style="padding: 3px;">
                <h1>FlashPlayer is not available</h1>
                <p><a href="http://www.adobe.com/go/getflashplayer">
                    <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
                         alt="Get Adobe Flash player"/></a>
                </p>
            </div>
        </div>

        <div style="clear: both"></div>

        <div class="btn-toolbar">
            <div class="btn-group">
                <a id="play" href="#" class="btn btn-primary">Воспроизведение</a>
                <a id="pause" href="#" class="btn btn-default">Пауза</a>
            </div>
        </div>

    </div>
</div>


</body>
</html>