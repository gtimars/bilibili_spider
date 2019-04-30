package com.gtj.spider.start;

import com.gtj.spider.entity.UserPage;
import com.gtj.spider.entity.VideoPage;
import com.gtj.spider.impl.download.HttpClientDownLoadImpl;
import com.gtj.spider.impl.process.JsonProcessPageImpl;
import com.gtj.spider.impl.scheduler.RedisRepositoryImpl;
import com.gtj.spider.impl.store.EsStoreImpl;
import com.gtj.spider.service.IPageDownLoad;
import com.gtj.spider.service.IProcessPage;
import com.gtj.spider.service.IRepository;
import com.gtj.spider.service.IStorePage;
import com.gtj.spider.util.UrlUtil;
import com.gtj.spider.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 开启爬虫
 *
 * @author gtj
 */
public class Start_Bilibili {

    Logger log = LoggerFactory.getLogger(Start_Bilibili.class);

    private IPageDownLoad pageDownLoad;
    private IProcessPage processPage;
    private IStorePage storePage;
    private IRepository repository;

    //线程池
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);


    public void setStorePage(IStorePage storePage) {
        this.storePage = storePage;
    }

    public void setProcessPage(IProcessPage processPage) {
        this.processPage = processPage;
    }

    public void setPageDownLoad(IPageDownLoad pageDownLoad) {
        this.pageDownLoad = pageDownLoad;
    }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    /**
     * 视频页面下载
     *
     * @param url
     * @return
     */
    public VideoPage downLoadPage(String url) {
        return this.pageDownLoad.downLoadVideoPage(url);
    }

    /**
     * 视频页面解析
     *
     * @param page
     */
    public void process(VideoPage page) {
        this.processPage.processVideoPage(page);
    }

    /**
     * 用户页面下载
     *
     * @param userInfoUrl,userStatUrl,upStatUrl,videoNumUrl
     * @return
     */
    public UserPage downLoadUserPage(String userInfoUrl, String userStatUrl, String upStatUrl, String videoNumUrl) {
        return this.pageDownLoad.downLoadUserPage(userInfoUrl, userStatUrl, upStatUrl, videoNumUrl);
    }

    /**
     * 用户页面解析
     *
     * @param page
     */
    public void processUser(UserPage page) {
        this.processPage.processUserPage(page);
    }

    /**
     * 视频信息持久化
     *
     * @param page
     */
    public void storeVideo(VideoPage page) {
        this.storePage.storeVideo(page);
    }

    /**
     * 用户信息持久化
     *
     * @param page
     */
    public void storeUser(UserPage page) {
        this.storePage.storeUser(page);
    }

    /*
    视频信息处理
     */
    public String getVideoInfo(String videoId) {

        //下载页面
        VideoPage page = downLoadPage(UrlUtil.VIDEO_URL + videoId);

        //解析
        if (page != null) {
            process(page);
            if(page.getMessage().equals("0")){
                //持久化
                storeVideo(page);
            }else {
                log.info("视频{}不存在或下架了！", videoId);
            }
        }
        return page.getMid();
    }

    /*
    用户信息处理
     */
    public void getUserInfo(String mid) {
        //页面下载
        UserPage page = Start_Bilibili.this.downLoadUserPage(UrlUtil.USERINFO_URL + mid,
                UrlUtil.USERSTAT_URL + mid, UrlUtil.UPSTAT_URL + mid,
                UrlUtil.VIDEONUM_URL + mid);
        //解析
        processUser(page);
        //持久化
        storeUser(page);
    }


    /**
     * 启动爬虫
     */
    public void run(){
        while (true) {
            //从redis仓库中获取视频id
            final String videoId = repository.pollVideo();

            if (videoId != null) {
                //保存下一个视频号
                int next_videoId1 = Integer.parseInt(videoId) * 2;
                int next_videoId2 = Integer.parseInt(videoId)*2 + 1;
                //爬取在2019/4/1之前的视频（截止的视频号是个大概，有可能会漏一些3/31的视频,也会爬取会部分4/1日的视频）
                if (next_videoId1 > 0 && next_videoId1 < 48000000 && next_videoId2 > 0 && next_videoId2 < 48000000) {
                    repository.addVideo(String.valueOf(next_videoId1));
                    repository.addVideo(String.valueOf(next_videoId2));
                }

                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        //视频信息处理，返回用户id
                        String mid = getVideoInfo(videoId);
                        ThreadUtil.sleep((long) Math.random() * 3000);

                        //用户信息处理
                        if (mid != null) {
                            if (!repository.userIdisExist(mid)) {
                                repository.addUser(mid);
                                getUserInfo(mid);
                                ThreadUtil.sleep((long) Math.random() * 3000);
                            }
                        }
                    }
                });
            } else {
                log.warn("暂时没有视频数据了，休息一下，或手动添加视频种子!");
                System.exit(0);
            }

        }
    }

    public static void main(String[] args) {
        final Start_Bilibili spider = new Start_Bilibili();
        spider.setPageDownLoad(new HttpClientDownLoadImpl());
        spider.setProcessPage(new JsonProcessPageImpl());
        // spider.setStorePage(new HBaseStoreImpl());
        spider.setStorePage(new EsStoreImpl());
        spider.setRepository(new RedisRepositoryImpl());

        spider.run();
    }
}
