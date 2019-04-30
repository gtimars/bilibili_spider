package com.gtj.spider.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件工具类
 *
 * @author gtj
 */
public class ConfigUtil {

    public static String getProperties(String properties, String filename) {
        Properties p = new Properties();
        InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream(
                properties + ".properties");
        try {
            p.load(in);
            return p.getProperty(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
