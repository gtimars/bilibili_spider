package com.gtj.spider.util;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * @author gtj
 */
public class ESUtil {

    private static TransportClient client = null;

    public ESUtil() {
        //构建Client
        Settings settings = Settings.builder().
                put("cluster.name",ConfigUtil.getProperties("config","cluster.name")).
                put("client.transport.ignore_cluster_name",ConfigUtil.getProperties("config",
                        "client.transport.ignore_cluster_name")).
                build();

        try {
            client = new PreBuiltTransportClient(settings).
                    addTransportAddress(new TransportAddress(InetAddress.getByName(
                            ConfigUtil.getProperties("config","es.host1")),
                            Integer.parseInt(ConfigUtil.getProperties("config","es.port")))).
                    addTransportAddress(new TransportAddress(InetAddress.getByName(
                            ConfigUtil.getProperties("config","es.host2")),
                            Integer.parseInt(ConfigUtil.getProperties("config","es.port")))).
                    addTransportAddress(new TransportAddress(InetAddress.getByName(
                            ConfigUtil.getProperties("config","es.host3")),
                            Integer.parseInt(ConfigUtil.getProperties("config","es.port"))));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加索引
     */
    public void createIndex(String index, String type,String id,HashMap value) {
        client.prepareIndex(index,type,id).
                setSource(value).get();
    }

    /**
     * 查询
     */
    public void searchIndex(String index, String type, String id) {
        GetResponse response = client.prepareGet(index,type,id).get();
    }
}
