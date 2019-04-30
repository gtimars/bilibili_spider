package com.gtj.spider.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Bilibili用户实体类
 *
 * @author gtj
 */
public class UserPage {
    //url
    private String url;
    //页面内容
    private List<String> contentList = new ArrayList<>();
    //用户id
    private String userid;
    //用户名
    private String username;
    //性别
    private String sex;
    //签名
    private String sign;
    //生日
    private String birthday;
    //硬币数量
    private String coins;
    //等级
    private String level;
    //播放数
    private String view;
    //粉丝数
    private String follower;
    //关注数
    private String following;
    //视频数量
    private String videoNum;

    public String getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(String videoNum) {
        this.videoNum = videoNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserid() {
        return userid;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void addContentList(String content) {
        this.contentList.add(content);
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
