package com.gtj.spider.service;

/**
 * @author gtj
 */
public interface IRepository {

    //从仓库中获取url
    String pollVideo();

    //添加video信息的Id号
    void addVideo(String videoId);

    //添加user信息的Id号
    void addUser(String userId);

    //判断userid是否存在
    boolean userIdisExist(String userId);
}
