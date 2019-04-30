package com.gtj.spider.impl.store;

import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;
import com.gtj.spider.service.IStorePage;
import com.gtj.spider.util.ESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * elasticsearch存储实现类
 *
 * @author gtj
 */
public class EsStoreImpl implements IStorePage{

    private static Logger log = LoggerFactory.getLogger(EsStoreImpl.class);

    ESUtil esUtil = new ESUtil();

    private static final String VIDEO_INDEX = "bili_video";
    private static final String VIDEO_TYPE = "video";

    private static final String USER_INDEX = "bili_user";
    private static final String USER_TYPE = "user";

    @Override
    public void storeVideo(VideoPage page) {
        HashMap<String, Object> pageMap = new HashMap<>();
        HashMap<String, Object> videoMap = new HashMap<>();
        HashMap<String, Object> ownerMap = new HashMap<>();
        HashMap<String, Object> statMap = new HashMap<>();

        //视频信息
        videoMap.put("title",page.getTitle());
        videoMap.put("pubdate",page.getPubdate());
        videoMap.put("desc",page.getDesc());

        //up主信息
        ownerMap.put("mid",page.getMid());
        ownerMap.put("author",page.getAuthor());

        //视频热度信息
        statMap.put("view",page.getView());
        statMap.put("danmaku",page.getDanmaku());
        statMap.put("coin",page.getCoin());
        statMap.put("favorite",page.getFavorite());
        statMap.put("like",page.getLike());
        statMap.put("reply",page.getReply());
        statMap.put("share",page.getShare());

        pageMap.put("video_tag",page.getTname());
        pageMap.put("video_info",videoMap);
        pageMap.put("owner",ownerMap);
        pageMap.put("vidoe_stat",statMap);

        esUtil.createIndex(VIDEO_INDEX, VIDEO_TYPE, page.getAid(), pageMap);

        log.info("bilibili视频{}已存储到ES中！",page.getAid());
    }

    @Override
    public void storeUser(UserPage page) {
        HashMap<String, Object> pageMap = new HashMap<>();
        HashMap<String, Object> userinfoMap = new HashMap<>();
        HashMap<String, Object> statMap = new HashMap<>();

        //up主信息
        userinfoMap.put("name",page.getUsername());
        userinfoMap.put("sex",page.getSex());
        userinfoMap.put("birthday",page.getBirthday());
        userinfoMap.put("sign",page.getSign());
        userinfoMap.put("level",page.getLevel());
        //热度信息
        statMap.put("viewNum",page.getView());
        statMap.put("videoNum",page.getVideoNum());
        statMap.put("following",page.getFollowing());
        statMap.put("follower",page.getFollower());
        statMap.put("coins",page.getCoins());

        pageMap.put("user_info",userinfoMap);
        pageMap.put("user_stat",statMap);

        esUtil.createIndex(USER_INDEX, USER_TYPE, page.getUserid(), pageMap);

        log.info("bilibil用户{}已存储到ES中！",page.getUserid());
    }
}
