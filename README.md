# Bilibili爬虫
爬取B站的视频信息，爬取时通过解析到的up主id号同时进行对up主信息的爬取
## 说明
- 使用httpclient下载网页，解析json页面后可以存储在Hbase或elasticsearch中
- 使用了多线程抓取，通过redis存储视频号实现分布式
- 使用阿布云代理
- 抓取视频信息的api：https://api.bilibili.com/x/web-interface/view?aid=
- 抓取用户个人信息的api：https://api.bilibili.com/x/space/acc/info?mid= 
- 粉丝数和关注数：https://api.bilibili.com/x/relation/stat?vmid=
- 播放量和阅读量：https://api.bilibili.com/x/space/upstat?mid=
- up主的视频数：https://api.bilibili.com/x/space/navnum?mid=
                    

