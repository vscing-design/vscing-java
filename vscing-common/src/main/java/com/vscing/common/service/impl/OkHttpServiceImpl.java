package com.vscing.common.service.impl;

import com.vscing.common.service.OkHttpService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * OkHttpServiceImpl
 *
 * @author vscing
 * @date 2025/1/8 11:31
 */
@Service
public class OkHttpServiceImpl implements OkHttpService {

  @Autowired
  private OkHttpClient okHttpClient;

  /**
   * 发送 GET 请求
   */
  @Override
  public String doGet(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
    HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
    if (params != null) {
      for (Map.Entry<String, String> entry : params.entrySet()) {
        urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
      }
    }
    Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());

    if (headers != null) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        requestBuilder.addHeader(entry.getKey(), entry.getValue());
      }
    }

    try (Response response = okHttpClient.newCall(requestBuilder.build()).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      ResponseBody responseBody = response.body();
      if (responseBody == null) {
        throw new IOException("Response body is null");
      }
      return responseBody.string();
    }
  }

  /**
   * 发送 POST 请求（JSON 体）
   */
  @Override
  public String doPostJson(String url, String jsonBody, Map<String, String> headers) throws IOException {
    RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
    Request.Builder requestBuilder = new Request.Builder()
        .url(url)
        .post(body);

    if (headers != null) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        requestBuilder.addHeader(entry.getKey(), entry.getValue());
      }
    }

    try (Response response = okHttpClient.newCall(requestBuilder.build()).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      ResponseBody responseBody = response.body();
      if (responseBody == null) {
        throw new IOException("Response body is null");
      }
      return responseBody.string();
    }
  }

  /**
   * 发送 POST 请求（表单数据）
   */
  @Override
  public String doPostForm(String url, Map<String, String> formData, Map<String, String> headers) throws IOException {
    FormBody.Builder formBuilder = new FormBody.Builder();
    if (formData != null) {
      for (Map.Entry<String, String> entry : formData.entrySet()) {
        formBuilder.add(entry.getKey(), entry.getValue());
      }
    }
    RequestBody body = formBuilder.build();

    Request.Builder requestBuilder = new Request.Builder()
        .url(url)
        .post(body);

    if (headers != null) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        requestBuilder.addHeader(entry.getKey(), entry.getValue());
      }
    }

    try (Response response = okHttpClient.newCall(requestBuilder.build()).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      ResponseBody responseBody = response.body();
      if (responseBody == null) {
        throw new IOException("Response body is null");
      }
      return responseBody.string();
    }
  }

  /**
   * 发送 POST 请求（JSON 体）
   */
  @Override
  public byte[] doPostResponse(String url, String jsonBody, Map<String, String> headers) throws IOException {
    RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
    Request.Builder requestBuilder = new Request.Builder()
        .url(url)
        .post(body);

    if (headers != null) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        requestBuilder.addHeader(entry.getKey(), entry.getValue());
      }
    }

    try (Response response = okHttpClient.newCall(requestBuilder.build()).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      // 读取响应体字节数组
      ResponseBody responseBody = response.body();
      if (responseBody == null) {
        throw new IOException("Response body is null");
      }
      byte[] responseBodyBytes = responseBody.bytes();
      // 确保响应体被关闭
      responseBody.close();
      return responseBodyBytes;
    }
  }

}
