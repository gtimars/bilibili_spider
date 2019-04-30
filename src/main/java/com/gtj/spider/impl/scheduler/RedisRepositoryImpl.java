package com.gtj.spider.impl.scheduler;

import com.gtj.spider.service.IRepository;
import com.gtj.spider.util.RedisUtil;

/**
 * 基于redis进行url任务调度
 *
 * @author gtj
 */
public class RedisRepositoryImpl implements IRepository {

    private static final String VIDEO_KEY = "video_key";
    private static final String USER_KEY = "user_key";

    @Override
    public String pollVideo() {
        String videoId = RedisUtil.pollListValue(VIDEO_KEY);
        return videoId;
    }

    @Override
    public void addVideo(String videoId) {
        RedisUtil.addList(VIDEO_KEY,videoId);
    }

    @Override
    public void addUser(String userId) {
        RedisUtil.addset(USER_KEY,userId);
    }

    @Override
    public boolean userIdisExist(String userId) {
        return RedisUtil.isExist(USER_KEY,userId);
    }
}
