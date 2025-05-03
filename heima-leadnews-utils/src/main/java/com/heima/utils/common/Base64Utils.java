package com.heima.utils.common;

import java.util.Base64;

public class Base64Utils {

    /**
     * 解码
     * @param base64 编码的字符串
     * @return 解码后的字节数组
     */
    public static byte[] decode(String base64) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] b = decoder.decode(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) { // 调整异常数据
                    b[i] += (byte) 256;
                }
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 编码
     * @param data 原始字节数组
     * @return Base64编码后的字符串
     */
    public static String encode(byte[] data) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }
}