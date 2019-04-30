package com.gtj.spider.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis工具类
 *
 * @author gtj
 */
public class RedisUtil {

    private static JedisPool jedisPool = null;

    /*
    初始化
     */
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //控制一个Pool最多有多少个状态为idle
        jedisPoolConfig.setMaxIdle(
                Integer.parseInt(ConfigUtil.getProperties("config","jedis.MaxIdle")));
        //可用连接实例的最大数目
        jedisPoolConfig.setMaxTotal(
                Integer.parseInt(ConfigUtil.getProperties("config","jedis.MaxTotal")));
        //等待可用连接的最大时间
        jedisPoolConfig.setMaxWaitMillis(
                Integer.parseInt(ConfigUtil.getProperties("config","jedis.MaxWaitMillis")));
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(jedisPoolConfig,
                ConfigUtil.getProperties("config","jedis.host"),
                Integer.parseInt(ConfigUtil.getProperties("config","jedis.port")));
    }

    /**
     * 获取jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加list值
     *
     * @param key
     * @param value
     */
    public static void addList(String key, String value) {
        Jedis jedis = getJedis();
        jedis.lpush(key, value);
        jedis.close();
    }

    /**
     * 添加set值
     *
     * @param key
     * @param value
     */
    public static void addset(String key, String value) {
        Jedis jedis = getJedis();
        jedis.sadd(key, value);
        jedis.close();
    }

    /**
     * 查看value是否是集合中的成员
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean isExist(String key, String value) {
        Jedis jedis = getJedis();
        Boolean sismember = jedis.sismember(key, value);
        jedis.close();
        return sismember;
    }

    /**
     * 删除并返回list中最后一个元素
     *
     * @param key
     */
    public static String pollListValue(String key) {
        Jedis jedis = getJedis();
        String value = jedis.lpop(key);
        jedis.close();
        return value;
    }

    public static void main(String[] args) {
        RedisUtil.addList("video_key", "1");
    }
}
