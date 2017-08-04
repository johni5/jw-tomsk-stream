<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="config" var="conf" scope="page"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script src="plugins/swfobject/swfobject.js"></script>

    <script>
        swfobject.embedSWF(
                "client/habr.swf",
                "client_2",
                "1000", "600", "9.0.0",
                "expressInstall.swf",
                {
                    "contextName": "<%=application.getContextPath()%>",
                    "host": "localhost",
                    "login": "DemoUser",
                    "pas": "DemoPassword"
                }
        );
        <%--swfobject.embedSWF(--%>
                <%--"client/flash-client-<fmt:message bundle="${conf}" key="project.version"/>.swf",--%>
                <%--"client_2",--%>
                <%--"1000", "600", "9.0.0",--%>
                <%--"expressInstall.swf",--%>
                <%--{--%>
                    <%--"contextName": "<%=application.getContextPath()%>",--%>
                    <%--"host": "localhost",--%>
                    <%--"login": "DemoUser",--%>
                    <%--"pas": "DemoPassword"--%>
                <%--}--%>
        <%--);--%>
    </script>
</head>
<body>
<div id="client_2" style="padding: 3px;">
    <h1>FlashPlayer is not available</h1>
    <p><a href="http://www.adobe.com/go/getflashplayer">
        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
             alt="Get Adobe Flash player"/></a>
    </p>
</div>
</body>
</html>
