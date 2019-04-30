package com.gtj.spider.util;


/**
 * @author gtj
 */
public class ThreadUtil {
    public static void sleep(long millions) {
        try {
            Thread.sleep(millions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
