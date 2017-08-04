<%--@elvariable id="login" type="java.lang.String"--%>
<%--@elvariable id="guid" type="java.lang.String"--%>
<%--@elvariable id="host" type="java.lang.String"--%>
<%--@elvariable id="nsName" type="java.lang.String"--%>
<%--@elvariable id="clientType" type="java.lang.String"--%>
<%--@elvariable id="meeting" type="com.del.jws.server.db.Meeting"--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Трансляция</title>
    <%@include file="common/plugins.jsp" %>
    <script src="<c:url value='/static/plugins/swfobject/swfobject.js' />"></script>
    <style>
        .online-user {
            font-size: 14px;;
        }
    </style>
    <script>
        var currentLogin = '${login}';
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
        swfobject.embedSWF(
                "<c:url value='/static/flash/JwsSender.swf' />",
                "client",
                "220", "150", "9.0.0",
                "expressInstall.swf",
                fv, params, attributes
        );

        function getMovie() {
            var M$ = navigator.appName.indexOf("Microsoft") != -1;
            return (M$ ? window : document)["BridgeMovie"]
        }

        var maxCountConnections = 10;

        function loadData() {
            try {
                $.each(getMovie().listDevices(),
                        function (key, value) {
                            $('#select').append($("<option></option>")
                                    .attr("value", key)
                                    .text(value));
                        });
            } catch (e) {
                if (maxCountConnections-- > 0) setTimeout(loadData, 200);
            }
        }

        jQuery(document).ready(function () {
            $('#record').click(function () {
                getMovie().record($('#select').prop('selectedIndex') - 1);
                return false;
            });
            $('#stop').click(function () {
                getMovie().stop();
                $('#user_list').html("");
                return false;
            });
            loadData();
        });

        var Buttons = {

            enable: function (ids) {
                $.each(ids.split(","),
                        function (key, value) {
                            $('#' + value).removeClass('disabled');
                        });
            }, disable: function (ids) {
                $.each(ids.split(","),
                        function (key, value) {
                            $('#' + value).addClass('disabled');
                        });
            }

        };

        function addUser(login) {
            $('#user_list').append('<p class="online-user" id="' + login + '"><span class="label label-success">Online</span>&nbsp;&nbsp;' + login + '</p>')
        }

        function removeUser(login) {
            $('#' + login).remove();
        }

        function userJoinRoom(r) {
            removeUser(r);
            addUser(r);
        }

        function userLeaveRoom(r) {
            removeUser(r);
        }

        function notify(code, msg) {
            switch (code) {
                case NotifyAction.STATUS_WAIT:
                    Buttons.enable('record,end');
                    Buttons.disable('stop');
                    break;
                case NotifyAction.STATUS_CONNECTING:
                    Buttons.disable('record,end,stop');
                    break;
                case NotifyAction.STATUS_CONNECTED:
                    Buttons.disable('record');
                    Buttons.enable('end,stop');
                    break;
                case NotifyAction.STATUS_BROADCASTING:
                    Buttons.disable('record');
                    Buttons.enable('end,stop');
                    break;
                case NotifyAction.ERROR_DEVICE_EXPECTED:
                    Buttons.enable('record,end');
                    Buttons.disable('stop');
                    break;
                case NotifyAction.ERROR_CONNECTION:
                    Buttons.enable('record,end');
                    Buttons.disable('stop');
                    break;
            }
        }


    </script>
</head>

<body>

<%@include file="common/menu.jsp" %>

<div id="root">
    <div class="main-container">

        <%@include file="common/alerts.jsp" %>

        <h3>Трансляция <fmt:formatDate value="${meeting.beginDate}" pattern="HH:mm dd/MM/yyyy"/></h3>

        <div class="btn-toolbar" style="width: 800px">

            <table style="float:left;">
                <tr>
                    <td style="padding-right: 20px; vertical-align: top">
                        <div>
                            <select style="min-width: 350px;" class="form-control" id="select"></select>
                        </div>
                        <div class="btn-group" style="padding-top: 20px">
                            <a id="record" href="#" class="btn btn-primary">Запустить</a>
                            <a id="stop" href="#" class="btn btn-default">Остановить</a>
                            <a id="end" href="<c:url value="//end-meeting-${meeting.id}" />" class="btn btn-danger">Закончить</a>
                        </div>
                        <div style="clear:both;"></div>
                        <div style="padding: 30px 10px 10px; width: 250px">
                            <div id="client" style="padding: 3px;">
                                <h1>FlashPlayer is not available</h1>
                                <p><a href="http://www.adobe.com/go/getflashplayer">
                                    <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
                                         alt="Get Adobe Flash player"/></a>
                                </p>
                            </div>
                        </div>
                    </td>
                    <td style="vertical-align: top">
                        <div class="panel panel-default" style="float:left; width: 300px;">
                            <div class="panel-heading">
                                <h3 class="panel-title">Список участников</h3>
                            </div>
                            <div id="user_list" class="panel-body" style="min-height: 50px; max-height: 300px;overflow-y: auto"></div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>

    </div>
</div>


</body>
</html>