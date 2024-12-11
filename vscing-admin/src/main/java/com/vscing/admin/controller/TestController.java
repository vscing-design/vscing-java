package com.vscing.admin.controller;

import com.vscing.admin.dto.UserDto;
import com.vscing.admin.vo.UserVo;
import com.vscing.common.api.CommonResult;
import com.vscing.common.util.SignatureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.stream.Collectors;
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

    // https://docs.qq.com/doc/DWXVlWmtHbnpLUXdT
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<Object> listAll() {

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


        try {
            // 将参数转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(params);

            // 创建 HttpClient 实例
            HttpClient client = HttpClient.newHttpClient();

            String query = "?userNo=17856563214&timestamp=" + timestamp + "&sign=" + sign;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://test.ot.jfshou.cn/ticket/ticket_api/city/query" + query))
//                    .uri(new URI("http://127.0.0.1:5500/v1/test/addInfo"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // 发送请求并获取响应
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Map<String, Object> result = new HashMap<>(2);
            result.put("statusCode", response.statusCode());
            result.put("body", response.body());

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
