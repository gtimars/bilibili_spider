package com.gtj.spider.impl.process;

import com.alibaba.fastjson.JSONObject;
import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;
import com.gtj.spider.service.IProcessPage;
import com.gtj.spider.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Json页面解析实现类
 *
 * @author gtj
 */
public class JsonProcessPageImpl implements IProcessPage{

    private static Logger log = LoggerFactory.getLogger(JsonProcessPageImpl.class);

    @Override
    public void processVideoPage(VideoPage page, String videoId) {
        String content = page.getContent();
        JSONObject info = JSONObject.parseObject(content);
        //判断视频信息是否存在
        try {
            page.setMessage(info.getString("message"));
            if(page.getMessage().equals("0")) {
                String data = info.getString("data");
                JSONObject data_json = JSONObject.parseObject(data);

                //视频信息
                page.setTitle(data_json.getString("title"));
                page.setAid(data_json.getString("aid"));
                page.setPubdate(DateUtil.secondTotime(data_json.getString("pubdate")));
                page.setDesc(data_json.getString("desc"));
                page.setTname(data_json.getString("tname"));

                //获取up主信息
                String owner = data_json.getString("owner");
                JSONObject owner_json = JSONObject.parseObject(owner);
                page.setMid(owner_json.getString("mid"));
                page.setAuthor(owner_json.getString("name"));

                //获取视频热度信息
                String stat = data_json.getString("stat");
                JSONObject stat_json = JSONObject.parseObject(stat);
                page.setCoin(stat_json.getString("coin"));
                page.setFavorite(stat_json.getString("favorite"));
                page.setShare(stat_json.getString("share"));
                page.setLike(stat_json.getString("like"));
                page.setReply(stat_json.getString("reply"));
                page.setDanmaku(stat_json.getString("danmaku"));
                page.setView(stat_json.getString("view"));

                log.info("视频{}解析完毕！",page.getAid());
            }
        }catch (Exception e){
            log.error("视频{}解析失败！",videoId);
        }

    }

    @Override
    public void processUserPage(UserPage page) {
        List<String> contentList = page.getContentList();
        for(int i=0; i<contentList.size(); i++) {
            JSONObject info = JSONObject.parseObject(contentList.get(i));
            String data = info.getString("data");
            JSONObject data_json = JSONObject.parseObject(data);
            if(i==0){
                //up主信息
                page.setSex(data_json.getString("sex"));
                page.setUsername(data_json.getString("name"));
                page.setSign(data_json.getString("sign"));
                page.setUserid(data_json.getString("mid"));
                page.setBirthday(data_json.getString("birthday"));
                page.setCoins(data_json.getString("coins"));
                page.setLevel(data_json.getString("level"));
            }
            if(i==1) {
                //粉丝和关注数
                page.setFollower(data_json.getString("follower"));
                page.setFollowing(data_json.getString("following"));
            }
            if(i==2) {
                //总播放量
                String archive = data_json.getString("archive");
                JSONObject archive_json = JSONObject.parseObject(archive);
                page.setView(archive_json.getString("view"));
            }
            if(i==3) {
                //视频数量
                page.setVideoNum(data_json.getString("video"));
            }
        }
    }
}
