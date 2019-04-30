package com.gtj.spider.util;

/**
 * @author gtj
 */
public class StringUtil {

    /**
     * 根据已知字符串的长度生成定长字符串，长度不够的补0
     * @param id
     * @param maxLength
     * @return
     */
    public static String getFixedLengthStr(String id, int maxLength) {
        if(id.length() >= maxLength) {
            return id;
        }
        StringBuffer sb = new StringBuffer();
        //补全字符
        int length = maxLength - id.length();
        for(int i=0; i< length; i++){
            sb.append('0');
        }
        String newId = sb.toString() + id;
        return newId;
    }

    /**
     * 反转字符串
     * @param id
     * @return
     */
    public static String getReverseStr(String id) {
        return new StringBuffer(id).reverse().toString();
    }

    public static void main(String[] args) {
        String fixedLengthStr = StringUtil.getFixedLengthStr("12", 8);
        System.out.println(StringUtil.getReverseStr(fixedLengthStr));
    }
}
