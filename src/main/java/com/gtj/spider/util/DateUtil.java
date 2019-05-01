package com.gtj.spider.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gtj
 */
public class DateUtil {

    /**
     * 将秒数转换为日期格式
     * @param second
     * @return
     */
    public static String secondTotime(String second) {
        Long s = new Long((second));
        Date date = new Date(s * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
