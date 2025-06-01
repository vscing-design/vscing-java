package com.vscing.common.service.supplier.impl.kky;

import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.utils.SignatureGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

/**
 * SupplierServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 16:04
 */
@Slf4j
@Service("kkySupplierService")
public class SupplierServiceImpl implements SupplierService {

  private static final OkHttpClient client = new OkHttpClient();

  @Autowired
  private SupplierProperties supplierProperties;

  @Override
  public String sendRequest(String url, Map<String, String> params) throws IOException {
    // 获取当前时间戳
    long timestamp = Instant.now().getEpochSecond();
    // 固定参数
    params.put("userid", supplierProperties.getUserNo());
    params.put("timestamp", String.valueOf(timestamp));
    // 日志记录：打印请求参数
//    String signingString = JSONUtil.toJsonStr(params);
//    log.info("signingString: {}", signingString);
    // 生成签名
    params.put("sign", SignatureGenerator.getKkySign(params, supplierProperties.getKey()));
    // 日志记录：打印请求参数
//    String requestString = JSONUtil.toJsonStr(params);
//    log.info("Request Parameters: {}", requestString);
    // 获取url
    if (!url.startsWith("http")) {
      url = supplierProperties.getBaseUrl() + url;
    }
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
    // 请求异常处理
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      return response.body().string();
    } catch (Exception e) {
      log.error("e: {}", e);
      return "{\"code\":-530,\"message\":\"请求异常\",\"data\":null}";
    }
  }

}
