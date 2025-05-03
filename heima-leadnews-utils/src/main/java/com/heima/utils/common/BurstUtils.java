package com.heima.utils.common;

/**
 * 分片桶字段算法
 */
public class BurstUtils {

    public final static String SPLIT_CHAR = "-";

    /**
     * 用-符号链接
     */
    public static String encrypt(Object... fileds){
        StringBuilder sb  = new StringBuilder();
        if(fileds!=null&&fileds.length>0) {
            sb.append(fileds[0]);
            for (int i = 1; i < fileds.length; i++) {
                sb.append(SPLIT_CHAR).append(fileds[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 默认第一组
     */
    public static String groudOne(Object... fileds){
        StringBuilder sb  = new StringBuilder();
        if(fileds!=null&&fileds.length>0) {
            sb.append("0");
            for (Object filed : fileds) {
                sb.append(SPLIT_CHAR).append(filed);
            }
        }
        return sb.toString();
    }
}