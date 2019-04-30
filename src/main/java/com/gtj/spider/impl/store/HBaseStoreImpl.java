package com.gtj.spider.impl.store;

import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;
import com.gtj.spider.service.IStorePage;
import com.gtj.spider.util.HBaseUtil;
import com.gtj.spider.util.StringUtil;

import java.io.IOException;

/**
 * Hbase存储实现类
 *
 * @author gtj
 */
public class HBaseStoreImpl implements IStorePage{

    HBaseUtil hBaseUtil = new HBaseUtil();

    @Override
    public void storeVideo(VideoPage page) {
        //设计rowkey
        String aid = StringUtil.getFixedLengthStr(page.getAid(),8);
        String rowkey = StringUtil.getReverseStr(aid);
        try {
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.AID, page.getAid());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.TITLE, page.getTitle());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.DESC, page.getDesc());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.PUBDATE, page.getPubdate());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.AUTHOR, page.getAuthor());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.VIEW, page.getView());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.DANMAKU, page.getDanmaku());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.FAVORITE, page.getFavorite());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.LIKE, page.getLike());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.SHARE, page.getShare());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.REPLY, page.getReply());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.TNAME, page.getTname());
            hBaseUtil.put(HBaseUtil.VIDEO_TABLE_NAME, rowkey, HBaseUtil.VIDEO_COLUMNFAMILY, HBaseUtil.COIN, page.getCoin());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeUser(UserPage page) {
        //设计rowkey
        String userid = StringUtil.getFixedLengthStr(page.getUserid(),8);
        String rowkey = StringUtil.getReverseStr(userid);
        try {
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.USERID, page.getUserid());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.USERNAME, page.getUsername());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.SIGN, page.getSign());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.SEX, page.getSex());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.BIRTHDAY, page.getBirthday());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.LEVEL, page.getLevel());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.COINS, page.getCoins());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.FOLLOWER, page.getFollower());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.FOLLOWING, page.getFollowing());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.VIEWNUM, page.getView());
            hBaseUtil.put(HBaseUtil.USER_TABLE_NAME, rowkey , HBaseUtil.USER_COLUMNFAMILY,HBaseUtil.VIDEONUM, page.getVideoNum());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
