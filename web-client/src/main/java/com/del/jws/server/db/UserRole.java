package com.del.jws.server.db;

/**
 * Created by dodolinel
 * date: 27.03.2017
 */
public enum UserRole {

    ADMIN,
    SUBSCRIBER,
    PUBLISHER
    ;

    public String getName() {
        return name();
    }
}
