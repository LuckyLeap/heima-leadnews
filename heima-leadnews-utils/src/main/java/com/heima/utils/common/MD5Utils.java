package com.heima.utils.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    /**
     * MD5加密
     */
    public static String encode(String str) {
        try {
            //创建具有指定算法名称的摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要
            md.update(str.getBytes());
            //进行哈希计算并返回一个字节数组
            byte[] mdBytes = md.digest();
            StringBuilder hash = new StringBuilder();
            //循环字节数组
            for (byte mdByte : mdBytes) {
                int temp;
                //如果有小于0的字节,则转换为正数
                if (mdByte < 0)
                    temp = 256 + mdByte;
                else
                    temp = mdByte;
                if (temp < 16)
                    hash.append("0");
                //将字节转换为16进制后，转换为字符串
                hash.append(Integer.toString(temp, 16));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encodeWithSalt(String numStr, String salt) {
        return encode(encode(numStr) + salt);
    }
}