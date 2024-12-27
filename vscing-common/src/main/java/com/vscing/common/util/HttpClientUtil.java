package com.vscing.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

/**
 * HttpClientUtil
 *
 * @author vscing
 * @date 2024/12/28 00:30
 */
@Slf4j
public class HttpClientUtil {

  private static final OkHttpClient client = new OkHttpClient();

  public static String postRequest(String url, Map<String, String> params) throws IOException {
    // 获取当前时间戳
    long timestamp = Instant.now().toEpochMilli();
    // 固定参数
    params.put("userNo", "17856563214");
    params.put("timestamp", String.valueOf(timestamp));
    // 秘钥
    String key = "BA19B011B243479B8A90CEA61A7286AF";
    // 生成签名
    String sign = SignatureGenerator.generateSignature(params, key);
    params.put("sign", sign);

    // 日志记录：打印请求参数
    log.info("Request Parameters: {}", params);

    // 构建 multipart/form-data 请求体
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
    }

    MultipartBody requestBody = multipartBuilder.build();

    // 创建 POST 请求
    Request request = new Request.Builder()
        .url(url)
        .post(requestBody)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      return response.body().string();
    }
  }

}
