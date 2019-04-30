package com.gtj.spider.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * httpclient下载工具类
 *
 * @author gtj
 */
public class HttpClientDownLoadUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientDownLoadUtil.class);

    //模拟浏览器
    private final static String User_Agent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";

    public static String getContent(String url) {

        String content = null;
        CloseableHttpClient httpClient = HttpClients.custom().build();

        //get请求
        HttpGet httpGet = new HttpGet(url);
        ThreadUtil.sleep(500);

        try {
            httpGet.setHeader("User-Agent",User_Agent);
            httpGet.addHeader("Accept-Encoding","gzip, deflate, br");

            long startTime = System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            long endTime = System.currentTimeMillis();
           // log.info("下载网页：{}，用时{}",url,endTime-startTime);

            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
        } catch (IOException e) {
            log.error("下载网页{}出错！",url);
        }
        return content;
    }
}
