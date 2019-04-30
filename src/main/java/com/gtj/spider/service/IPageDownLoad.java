package com.gtj.spider.service;


import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;

/**
 * 页面下载接口
 *
 * @author gtj
 */
public interface IPageDownLoad {
    //视频url
    VideoPage downLoadVideoPage(String url);

    //用户url
    UserPage downLoadUserPage(String userInfoUrl, String userStatUrl, String upStatUrl, String videoNumUrl);
}
