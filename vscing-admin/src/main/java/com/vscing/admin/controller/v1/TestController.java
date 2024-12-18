package com.vscing.admin.controller.v1;

import com.vscing.common.api.CommonResult;
import com.vscing.common.util.SignatureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;

/**
 * TestController
 *
 * @date 2024/12/11 00:51
 * @auth vscing(vscing @ foxmail.com)
 */

@RestController
@RequestMapping("/v1/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // https://docs.qq.com/doc/DWXVlWmtHbnpLUXdT
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<Object> listAll() {

        // 获取当前时间戳
        Instant now = Instant.now();
        long timestamp = now.toEpochMilli();

        // 准备请求参数
        Map<String, String> params = new HashMap<>(3);
        String timestampStr = String.valueOf(timestamp);
        params.put("userNo", "17856563214");
        params.put("timestamp", timestampStr);

        // 秘钥
        String key = "BA19B011B243479B8A90CEA61A7286AF";

        // 生成签名
        String sign = SignatureGenerator.generateSignature(params, key);
        params.put("sign", sign);

        // 日志记录：打印请求参数
        log.info("Request Parameters: {}", params);

        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient();

        // 构建 multipart/form-data 请求体
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        MultipartBody requestBody = multipartBuilder.build();

        // 创建 POST 请求
        Request request = new Request.Builder()
                .url("https://test.ot.jfshou.cn/ticket/ticket_api/city/query")
                .post(requestBody)
                .build();

        // 发送请求并获取响应
        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            Map<String, Object> result = new HashMap<>(2);

            // 将 JSON 字符串解析为 JsonNode 对象
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());

            result.put("statusCode", response.code());
            result.put("body", jsonNode);

            // 使用 writerWithDefaultPrettyPrinter() 打印漂亮的 JSON
//            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
//            System.out.println("Pretty JSON:\n" + prettyJson);

            return CommonResult.success(result);

        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @RequestMapping(value = "/addInfo", method = RequestMethod.POST)
    public CommonResult<Object> addInfo(@RequestBody Map<String, Object> info) {
        return CommonResult.success(info);
    }
}
