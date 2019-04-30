package com.gtj.spider.service;


import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;

/**
 * 页面解析接口
 *
 * @author gtj
 */
public interface IProcessPage {
    //解析视频页面
    void processVideoPage(VideoPage page);
    //解析用户页面
    void processUserPage(UserPage page);
}
