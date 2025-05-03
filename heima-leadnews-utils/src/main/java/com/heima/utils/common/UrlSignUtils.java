package com.heima.utils.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.SortedMap;

public enum UrlSignUtils {

    INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(UrlSignUtils.class);

    /**
     * @param params 所有的请求参数都会在这里进行排序加密
     * @return 得到签名
     */
    public String getSign(SortedMap<String, String> params) {
        if (params == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if ("sign".equals(key)) continue;

            if (key != null && value != null && !key.isEmpty() && !value.isEmpty()) {
                sb.append(key).append('=').append(value);
            }
        }

        String beforeSign = sb.toString();
        logger.debug("Before Sign : {}", beforeSign); // 改为 debug 避免敏感信息泄露
        return DigestUtils.md5Hex(beforeSign).toUpperCase();
    }

    /**
     * @param params 所有的请求参数都会在这里进行排序加密
     * @return 验证签名结果
     */
    public boolean verifySign(SortedMap<String, String> params) {
        if (params == null) {
            return false;
        }

        String inputSign = params.get("sign");
        if (inputSign == null || inputSign.isEmpty()) {
            return false;
        }

        String calculatedSign = getSign(params);
        logger.debug("verify Sign : {}", calculatedSign); // 改为 debug
        return inputSign.equals(calculatedSign);
    }
}