package com.gtj.spider.service;

import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;

/**
 * 数据持久化接口
 *
 * @author gtj
 */
public interface IStorePage {
    //视频信息存储
    void storeVideo(VideoPage page);
    //用户信息存储
    void storeUser(UserPage page);
}
