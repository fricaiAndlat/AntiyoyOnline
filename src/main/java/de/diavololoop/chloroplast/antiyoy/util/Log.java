package de.diavololoop.chloroplast.antiyoy.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Log {

    public final static String CH_NET = "net";
    public final static String CH_CLIENT = "client";
    public final static String CH_DEBUG = "debug";
    public final static String CH_ALL = "spam";

    private final static Map<String, Boolean> ACTIVE = new HashMap<String, Boolean>();

    static {
        //ACTIVE.put(CH_ALL, false);
    }


    private final static Log INSTANCE = new Log();

    public static void p(String channel, String m) {
        INSTANCE.log(channel, m);
    }


    private void log(String channel, String m) {
        if (ACTIVE.containsKey(channel)) {
            if (ACTIVE.get(channel)) {
                System.out.printf("%20s: %s\r\n", channel, m);
            }
        } else {
            System.out.printf("%20s: %s\r\n", channel, m);
        }

    }

}
