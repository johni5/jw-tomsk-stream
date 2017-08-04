package com.del.jws.red5;

import org.red5.server.api.Red5;
import org.springframework.context.ApplicationContext;

/**
 * Created by DodolinEL
 * date: 15.05.2017
 */
public class AppUtils {

    public static final String NS_NAME_PREFIX = "jws-ns-";

    private AppUtils() {
    }

    public static <T> T lookupBean(Class<T> tClass) {
        ApplicationContext applicationContext = getApplicationContext();
        return applicationContext.getBean(tClass);
    }

    public static ApplicationContext getApplicationContext() {
        return Red5.getConnectionLocal().getScope().getContext().getApplicationContext();
    }

    public static String codeMeetingId(Long meetingId) {
        return NS_NAME_PREFIX + meetingId;
    }

    public static Long encodeMeetingId(String nsName) {
        return Long.parseLong(nsName.substring(NS_NAME_PREFIX.length()));
    }


}
