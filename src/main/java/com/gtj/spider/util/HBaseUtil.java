package com.gtj.spider.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * hbase工具类
 *
 * @author gtj
 */
public class HBaseUtil {
    //表名
    public static final String VIDEO_TABLE_NAME = "video";
    public static final String USER_TABLE_NAME = "biliUser";
    //列簇
    public static final String VIDEO_COLUMNFAMILY = "video_info";
    public static final String USER_COLUMNFAMILY = "user_info";
    //video列名
    public static final String AID = "aid";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String PUBDATE = "pubdate";
    public static final String DESC = "desc";
    public static final String VIEW = "view";
    public static final String DANMAKU = "danmaku";
    public static final String FAVORITE = "favorite";
    public static final String COIN = "coin";
    public static final String LIKE = "like";
    public static final String SHARE = "share";
    public static final String REPLY = "reply";
    public static final String TNAME = "tname";

    //user列名
    public static final String USERID = "userid";
    public static final String USERNAME = "username";
    public static final String SEX = "sex";
    public static final String SIGN = "sign";
    public static final String BIRTHDAY = "birthday";
    public static final String COINS = "coins";
    public static final String LEVEL = "level";
    public static final String VIEWNUM = "view";
    public static final String FOLLOWER = "follower";
    public static final String FOLLOWING = "following";
    public static final String VIDEONUM = "videoNum";

    Configuration conf = null;
    Admin admin = null;
    Connection connection = null;

    /**
     * 加载配置
     */
    public HBaseUtil() {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", ConfigUtil.getProperties("config","hbase.zookeeper.quorum"));
        conf.set("hbase.zookeeper.property.clientPort", ConfigUtil.getProperties("config","hbase.zookeeper.property.clientPort"));
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    创建表
     */
    public void createTable(String tableName, String column) throws IOException {
        TableName tabName = TableName.valueOf(tableName);

        if(admin.tableExists(tabName)) {
            System.out.println("表" + tabName + "已存在！");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tabName);
            hTableDescriptor.addFamily(new HColumnDescriptor(column.getBytes()));
            admin.createTable(hTableDescriptor);
            System.out.println(tabName + "创建成功！");
        }
    }

    /*
    删除表
     */
    public void delTable(String tableName) {
        TableName tabName = TableName.valueOf(tableName);
        try {
            if(admin.tableExists(tabName)) {
                admin.disableTable(tabName);
                admin.deleteTable(tabName);
                System.out.println("表" + tabName + "删除成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("表" + tabName + "删除失败！");
        }
    }

    /*
    查看所有表
     */
    public void getAllTable() throws IOException {
        if(admin != null) {
            HTableDescriptor[] listTables = admin.listTables();
            for (HTableDescriptor listTable : listTables) {
                System.out.println(listTable.getNameAsString());
            }
        }
    }

    /**
     * 插入一条数据
     * @param tableName
     * @param rowkey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public void put(String tableName, String rowkey, String columnFamily, String column, String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(column),Bytes.toBytes(value));
        table.put(put);
    }

    /**
     * 删除某条纪录
     * @param tableName
     * @param rowkey
     * @param columnFamily
     * @param column
     * @throws IOException
     */
    public void delRow(String tableName, String rowkey, String columnFamily, String column) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowkey.getBytes());
        //删除指定列簇
        delete.addFamily(columnFamily.getBytes());
        //删除指定列
        delete.addColumn(columnFamily.getBytes(),column.getBytes());
        table.delete(delete);
    }

    /**
     * 根据rowkey查询
     * @param tableName
     * @param rowkey
     * @throws IOException
     */
    public void getData(String tableName,String rowkey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowkey.getBytes());
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println(new String(CellUtil.cloneQualifier(cell))+ ":"+
                    new String(CellUtil.cloneValue(cell))+" ");
        }
    }
}
