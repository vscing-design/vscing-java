package com.vscing.common.utils;

import cn.hutool.core.bean.BeanUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 请求工具类
 * @author vscing (vscing@foxmail.com)
 * @date 2024-12-11 00:56:45
*/
@Slf4j
public class RequestUtil {

    /**
     * 获取请求真实IP地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        //通过HTTP代理服务器转发时添加
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            // 从本地访问时根据网卡取本机配置的IP
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                    if(inetAddress == null) {
                        throw new RuntimeException("获取本地IP地址失败");
                    }
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取本地IP地址失败: {}", e.getMessage());
                    throw new RuntimeException("获取本地IP地址失败: " + e.getMessage(), e);
                }
            }
        }
        // 通过多个代理转发的情况，第一个IP为客户端真实IP，多个IP会按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取 HTTP 请求体 body。使用原始报文。
    */
    public static String getRequestBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (ServletInputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * 请求体中除sign外的参数转换
     *
     * @param request 请求体对象
     * @return 转换后的字符串
     */
    public static <T> String encryptBodyWithoutSign(T request) {
        // 将对象转换为Map
        Map<String, Object> params = BeanUtil.beanToMap(request);

        // 去除sign字段
        params.remove("sign");

        // 按字典顺序排序并拼接成字符串
        List<Map.Entry<String, Object>> entries = new ArrayList<>(params.entrySet());
        entries.sort(Map.Entry.comparingByKey());

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : entries) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1); // 移除最后一个&
        }
        return sb.toString();
    }

}