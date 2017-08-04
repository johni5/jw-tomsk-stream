package com.del.jws.utils;

/**
 * Created by DodolinEL
 * date: 15.05.2017
 */
public class Unchecked {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        if (o == null) {
            return null;
        }
        return (T) o;
    }

}
