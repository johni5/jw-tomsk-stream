package com.del.jws.red5;

import com.del.jws.server.db.Meeting;
import com.del.jws.server.db.User;
import com.del.jws.server.db.UserRole;
import com.del.jws.server.security.AuthUtils;
import com.del.jws.server.security.ClientType;
import com.del.jws.server.service.UserService;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.ServiceUtils;
import org.red5.server.api.stream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dodolinel
 * date: 20.03.2017
 */
public class Application extends ApplicationAdapter {

    private static final Logger logger = Red5LoggerFactory.getLogger(Application.class);
    private ClientManager clientManager = new ClientManager("jws_users_so", false);
    private Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public boolean appStart(IScope app) {
        return super.appStart(app);
    }

    @Override
    public boolean appConnect(IConnection conn, Object[] params) {
        UserService userService = AppUtils.lookupBean(UserService.class);
        if (params != null && params.length > 1) {
            String guid = params[0].toString();
            ClientType clientType = ClientType.valueOf(params[1].toString());
            try {
                String login = AuthUtils.parseLogin(guid);
                User user = userService.findByLogin(login);
                if (AuthUtils.getGuid(login, user.getPassword()).equals(guid)) {
                    if (clientType.check(user.getRole())) {
                        logger.info("Connected by " + login + " for client " + clientType);
                        String id = conn.getClient().getId();
                        if (users.containsKey(login)) {
                            rejectClient("User '" + users + "' is already online.");
                            return false;
                        }
                        clientManager.addClient(scope, login, id);
                        users.put(login, user);
                        return super.appConnect(conn, params);
                    } else {
                        logger.warn("Access denied for " + login);
                    }
                } else {
                    logger.warn("Password incorrect for " + login);
                }
            } catch (IllegalAccessException e) {
                logger.warn("Access denied: " + e.getMessage());
            }
        } else {
            logger.warn("Access denied: GUID expected!");
        }
        return false;
    }

    @Override
    public void streamPlayItemPlay(ISubscriberStream stream, IPlayItem item, boolean isLive) {
        String login = clientManager.returnClient(stream.getScope(), stream.getConnection().getClient().getId());
        ServiceUtils.invokeOnAllScopeConnections(stream.getScope(), "UserJoinRoom",
                new Object[]{login, stream.getBroadcastStreamPublishName()}, null);
        super.streamPlayItemPlay(stream, item, isLive);
    }

    @Override
    public void streamPlayItemStop(ISubscriberStream stream, IPlayItem item) {
        super.streamPlayItemStop(stream, item);
        IStreamCapableConnection conn = stream.getConnection();
        String id = conn.getClient().getId();
        String login = clientManager.removeClient(scope, id);
        logger.info(login + " disconnected");
        ServiceUtils.invokeOnAllScopeConnections(conn.getScope(), "UserLeaveRoom", new Object[]{login}, null);
        users.remove(login);
    }

    @Override
    public void streamPublishStart(IBroadcastStream stream) {
        try {
            UserService userService = AppUtils.lookupBean(UserService.class);
            Long meetingId = AppUtils.encodeMeetingId(stream.getPublishedName());
            Meeting meeting = userService.findMeetingById(meetingId);
            meeting.setStartDate(new Date());
            userService.saveMeeting(meeting);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        super.streamPublishStart(stream);
    }

    @Override
    public void appDisconnect(IConnection conn) {
        super.appDisconnect(conn);
        String id = conn.getClient().getId();
        String login = clientManager.removeClient(scope, id);
        logger.info(login + " disconnected");
        ServiceUtils.invokeOnAllScopeConnections(conn.getScope(), "UserLeaveRoom", new Object[]{login}, null);
        users.remove(login);
    }

    @Override
    public void streamBroadcastClose(IBroadcastStream stream) {
        super.streamBroadcastClose(stream);
        ServiceUtils.invokeOnAllScopeConnections(stream.getScope(), "streamBroadcastClose", new Object[]{stream.getPublishedName()}, null);
    }

    public String getUserLogin() {
        logger.info("----------------------------- getUserLogin()");
        return "Hello world!";
    }
}
