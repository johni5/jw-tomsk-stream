package chat2;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.service.ServiceUtils;
import org.red5.server.api.so.ISharedObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Application extends ApplicationAdapter {
    public HashMap<String, User> users = new HashMap<String, User>();
    private ClientManager clientMgr = new ClientManager("users_so", true);

    public class cam_send {
        public String id = null;
        public String status = null;

        public cam_send(String id, String status) {
            this.id = id;
            this.status = status;
        }
    }

    public class ret_priv_mess {
        public String text = null;
        public String id = null;
        public String nik = null;

        public ret_priv_mess(String text, String id, String nik) {
            this.text = text;
            this.id = id;
            this.nik = nik;
        }
    }

    public boolean appStart(IScope app) {
        return super.appStart(app);
    }

    public void appStop() {
    }

    public boolean getpassword(String user_id) {
        try {
            String data = "mydata=mydatavalue&mydata2=n";
            // Send the request
            // System.out.println("Начало соединения..");
            URL url = new URL("http://google.com");
            URLConnection conn = url.openConnection();
            //System.out.println("Соединились..");
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            //write parameters
            writer.write(data);
            writer.flush();
            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // answer.append(line);
                // System.out.println("Получение ответа от сервера.." + line);
                String falseString = "NOTFOUND";
            }
            writer.close();
            reader.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean appConnect(IConnection conn, Object[] params) {
        if (params == null || params.length != 3) {
            rejectClient("Client must pass 2 params !.");
            return false;
        }
        //if (getpassword(params[0].toString()) == true) rejectClient("true");
        //if (getpassword(params[0].toString()) == false) rejectClient("false");
        //getSession ( params[0].toString() );
        String id = conn.getClient().getId();
        String nikname = params[0].toString();
        String sex = params[1].toString();
        String isWatching = "false";
        String cam = params[2].toString();
        // test if username is already used
        if (users.containsKey(nikname)) {
            rejectClient("Ошибка!!! Ник: <<" + nikname + ">> уже используется.");
            return false;
        }
        users.put(nikname, new User(id, nikname, sex, isWatching, cam));
        clientMgr.addClient(scope, nikname, id);
        ServiceUtils.invokeOnAllScopeConnections(conn.getScope(), "UserJoinRoom", new Object[]{new User(id, nikname, sex, isWatching, cam)}, null);
        return true;
    }

    public void appDisconnect(IConnection conn) {
        IScope appScope = Red5.getConnectionLocal().getScope();
        String id = conn.getClient().getId();
        String nikname = clientMgr.removeClient(scope, id);
        User user = users.get(nikname);
        users.remove(nikname);
        ServiceUtils.invokeOnAllScopeConnections(conn.getScope(), "UserLeaveRoom", new Object[]{user}, null);
        super.appDisconnect(conn);
    }

    public boolean roomJoin(IClient client, IScope room) {
        return super.roomJoin(client, room);
    }

    public void change_webcam(String status) {
        IConnection conn = Red5.getConnectionLocal();
        String id = conn.getClient().getId();
        ServiceUtils.invokeOnAllScopeConnections(conn.getScope(), "onchange_webcam", new Object[]{new cam_send(id, status)}, null);
        IScope appScope = Red5.getConnectionLocal().getScope();
        String pseudo = clientMgr.returnClient(scope, id);
        User user = users.get(pseudo);
        //users.remove(pseudo);
        users.put(pseudo, new User(user.id, user.nikname, user.sex, user.isWatching, "on"));
    }

    public void watch(String UserId) {
        IConnection conn2 = Red5.getConnectionLocal();
        String uid = conn2.getClient().getId();
        IScope appScope = Red5.getConnectionLocal().getScope();
        Collection<Set<IConnection>> connecciones = appScope.getConnections();
        for (Set<IConnection> listConnection : connecciones) {
            Iterator<IConnection> it = listConnection.iterator();
            while (it.hasNext()) {
                IConnection conn = it.next();
                String id = conn.getClient().getId();
                if (!(UserId.equals(id))) continue;
                if (conn instanceof IServiceCapableConnection) {
                    ((IServiceCapableConnection) conn).invoke("onwatch", new Object[]{uid});
                    return;
                }
            }
        }
    }

    public void offwatch(String UserId) {
        IConnection conn2 = Red5.getConnectionLocal();
        String uid = conn2.getClient().getId();
        IScope appScope = Red5.getConnectionLocal().getScope();
        Collection<Set<IConnection>> connecciones = appScope.getConnections();
        for (Set<IConnection> listConnection : connecciones) {
            Iterator<IConnection> it = listConnection.iterator();
            while (it.hasNext()) {
                IConnection conn = it.next();
                String id = conn.getClient().getId();
                if (!(UserId.equals(id))) continue;
                if (conn instanceof IServiceCapableConnection) {
                    ((IServiceCapableConnection) conn).invoke("offwatch", new Object[]{uid});
                    return;
                }
            }
        }
    }

    public void privatMessage(String Text, String UserId, String fr) {
        IConnection conn2 = Red5.getConnectionLocal();
        String uid = conn2.getClient().getId();
        IScope appScope = Red5.getConnectionLocal().getScope();
        Collection<Set<IConnection>> connecciones = appScope.getConnections();
        for (Set<IConnection> listConnection : connecciones) {
            Iterator<IConnection> it = listConnection.iterator();
            while (it.hasNext()) {
                IConnection conn = it.next();
                String id = conn.getClient().getId();
                if (!(UserId.equals(id))) continue;
                if (conn instanceof IServiceCapableConnection) {
                    ((IServiceCapableConnection) conn).invoke("takeprivatmessege", new Object[]{new ret_priv_mess(Text, uid, fr)});
                    return;
                }
            }
        }
    }

    public void roomLeave(IClient client, IScope room) {
        //String id = client.getId();
        // String nikname = clientMgr.removeClient(scope, id);
        // User user=users.get(nikname);
        // users.remove(nikname);
        // ServiceUtils.invokeOnAllConnections (room,"UserLeaveRoom", new Object[] {"cc"} );
        // user.id,user.nikname,user.sex,user.isWatching super.roomLeave(client, room);
    }

    public String getUserId() {
        IConnection conn2 = Red5.getConnectionLocal();
        String uid = conn2.getClient().getId();
        return uid;
    }

    public HashMap<String, User> getUserList() {
        return users;
    }

    public void chatLogin(String userName, String textColor, String chatAvatar) {
        chatMessage(userName, "Пользователь вошел в чат", textColor, chatAvatar);
    }

    public void chatMessage(String userName, String message, String textColor, String chatAvatar) {
        IScope scope = Red5.getConnectionLocal().getScope();
        ISharedObject chat = getSharedObject(scope, "chat");
        // List<ChatHistoryItem> history = (List<ChatHistoryItem>) chat.getAttribute("remoteHistory");
        java.util.List<ChatHistoryItem> history = new ArrayList<ChatHistoryItem>();
        ChatHistoryItem item = new ChatHistoryItem();
        item.user = userName;
        item.date = new Date();
        item.message = message;
        item.textColor = textColor;
        item.chatAvatar = chatAvatar;
        history.add(item);
        //chat.removeAttribute("remoteHistory");
        chat.setAttribute("remoteHistory", history);
    }

    public boolean roomStart(IScope room) {
        if (!super.roomStart(room)) return false;
        createSharedObject(room, "chat", false);
        ISharedObject chat = getSharedObject(room, "chat");
        java.util.List<ChatHistoryItem> history = new ArrayList<ChatHistoryItem>();
        ChatHistoryItem item = new ChatHistoryItem();
        item.user = "Administrator";
        item.date = new Date();
        item.message = "Red5Flexchat start";
        item.textColor = "0xbb0f00";
        item.chatAvatar = "";
        history.add(item);
        chat.setAttribute("remoteHistory", history);
        return true;
    }
}