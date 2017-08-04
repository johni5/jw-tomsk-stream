package com.del.jws.server.security;

import java.util.Base64;
import java.util.StringTokenizer;

/**
 * Created by dodolinel
 * date: 24.04.2017
 */
public class AuthUtils {

    private static String SEPARATOR = ":";

    public static String getGuid(String login, String md5Pass) throws IllegalAccessException {
        String auth = login + SEPARATOR + md5Pass;
        try {
            byte[] encode = Base64.getEncoder().encode(auth.getBytes());
            return new String(encode);
        } catch (Exception e) {
            throw new IllegalAccessException("Ошибка аутентификации!");
        }
    }

    public static String parseLogin(String guid) throws IllegalAccessException {
        byte[] decodedBytes = Base64.getDecoder().decode(guid);
        try {
            String guidStr = new String(decodedBytes, "UTF-8");
            StringTokenizer tokenizer = new StringTokenizer(guidStr, SEPARATOR);
            return tokenizer.nextToken();
        } catch (Exception e) {
            throw new IllegalAccessException("Ошибка чтения GUID!");
        }
    }

}
