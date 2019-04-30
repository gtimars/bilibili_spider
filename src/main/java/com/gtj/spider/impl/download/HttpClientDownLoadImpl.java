package com.gtj.spider.impl.download;

import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;
import com.gtj.spider.service.IPageDownLoad;
import com.gtj.spider.util.AbuyunHttpclientUtil;
import com.gtj.spider.util.ThreadUtil;

/**
 * 页面下载实现类
 *
 * @author gtj
 */
public class HttpClientDownLoadImpl implements IPageDownLoad{
    @Override
    public VideoPage downLoadVideoPage(String url) {
        VideoPage page = new VideoPage();
        page.setContent(AbuyunHttpclientUtil.getContent(url));
        return page;
    }

    @Override
    public UserPage downLoadUserPage(String userInfoUrl, String userStatUrl, String upStatUrl, String videoNumUrl) {
        UserPage page = new UserPage();
        page.addContentList(AbuyunHttpclientUtil.getContent(userInfoUrl));
        ThreadUtil.sleep(200);
        page.addContentList(AbuyunHttpclientUtil.getContent(userStatUrl));
        ThreadUtil.sleep(200);
        page.addContentList(AbuyunHttpclientUtil.getContent(upStatUrl));
        ThreadUtil.sleep(200);
        page.addContentList(AbuyunHttpclientUtil.getContent(videoNumUrl));
        return page;
    }
}
