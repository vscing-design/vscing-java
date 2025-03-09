package com.vscing.common.service;

import java.io.IOException;
import java.util.Map;

/**
 * OkHttpService
 *
 * @author vscing
 * @date 2025/1/8 11:31
 */
public interface OkHttpService {

  /**
   * 发送 GET 请求
   */
  public String doGet(String url, Map<String, String> params, Map<String, String> headers) throws IOException;

  /**
   * 发送 POST 请求（JSON 体）
   */
  public String doPostJson(String url, String jsonBody, Map<String, String> headers) throws IOException;

  /**
   * 发送 POST 请求（表单数据）
   */
  public String doPostForm(String url, Map<String, String> formData, Map<String, String> headers) throws IOException;

  /**
   * 发送 POST 请求（JSON 体）
   */
  public byte[] doPostResponse(String url, String jsonBody, Map<String, String> headers) throws IOException;

}
