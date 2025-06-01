package com.vscing.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * SignatureGenerator
 *
 * @date 2024/12/11 00:57
 * @auth vscing(vscing @ foxmail.com)
 */
@Slf4j
public class SignatureGenerator {

//    public static void main(String[] args) {
//        // 请求参数
//        Map<String, String> params = new LinkedHashMap<>();
//        params.put("userNo", "18888888888");
//        params.put("cityId", "2");
//        params.put("timestamp", "1633017600000");
//
//        // 秘钥
//        String key = "afdaffda1232";
//
//        // 生成签名
//        String sign = generateSignature(params, key);
//
//        // 构建最终URL
//        String url = "https://ot.jfshou.cn/ticket/ticket_api/cinema/query?" + buildQuery(params) + "&sign=" + sign;
//        System.out.println("Final URL: " + url);
//    }

    /**
     * 生成签名
     * @author vscing (vscing@foxmail.com)
     * @date 2024-12-11 01:01:43
    */
    public static String generateSignature(Map<String, String> params, String key) {
        // 按照字典序排序
        Map<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        stringBuilder.append("key=").append(key);

        // 对拼接后的字符串进行MD5加密并转为大写
        return md5(stringBuilder.toString()).toUpperCase();
    }

    /**
     * 生成签名
     * @param param 提交数据（键值对，值必须为字符串）
     * @param token 商户密钥
     * @return 签名字符串
     */
    public static String getKkySign(Map<String, String> param, String token) {
        // 1. 按键排序
        TreeMap<String, String> sortedParam = new TreeMap<>(param);

        // 2. 构建签名字符串
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : sortedParam.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();

            // 3. 跳过空值、null值、sign字段
            if (val == null || key.equals("sign") || val.trim().isEmpty()) {
                continue;
            }

            // 4. 拼接参数（首项不加 &）
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("&");
            }
            stringBuilder.append(key).append("=").append(val);
        }

        // 5. 生成 MD5 签名
        String signText = stringBuilder + token;

        return md5(signText).toLowerCase();
    }
    /**
     * 参数转换
     * @author vscing (vscing@foxmail.com)
     * @date 2024-12-11 01:00:39
    */
    public static String buildQuery(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        // Remove the last '&' character
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * md5加密
     * @author vscing (vscing@foxmail.com)
     * @date 2024-12-11 01:01:55
    */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sha256加密
     * @author vscing (vscing@foxmail.com)
     * @date 2024-12-11 01:01:55
     */
    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
