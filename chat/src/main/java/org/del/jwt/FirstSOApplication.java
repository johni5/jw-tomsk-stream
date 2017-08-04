package org.del.jwt;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;

/**
 * Created by dodolinel
 * date: 20.02.2017
 */
public class FirstSOApplication extends ApplicationAdapter {

    @Override
    public boolean appConnect(org.red5.server.api.IConnection conn, Object[] params) {
        if (params.length == 2 && "DemoUser".equals(params[0]) && "DemoPassword".equals(params[1]))
            return super.appConnect(conn, params);
        return false;
    }


    @Override
    public void appDisconnect(IConnection conn) {
        super.appDisconnect(conn);
    }

}
