package com.del.jws.server.security;

import com.del.jws.server.db.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dodolinel
 * date: 25.04.2017
 */
public enum ClientType {

    RECORD(UserRole.ADMIN, UserRole.PUBLISHER),
    VIEW(UserRole.ADMIN, UserRole.PUBLISHER, UserRole.SUBSCRIBER);

    private List<UserRole> roles = new ArrayList<>();

    ClientType(UserRole... roles) {
        if (roles != null) this.roles.addAll(Arrays.asList(roles));
    }

    public boolean check(UserRole role) {
        return roles.contains(role);
    }
}
