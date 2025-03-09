package com.vscing.common.service.applet.impl.wechat.extend;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.cipher.PrivacyDecryptor;
import com.wechat.pay.java.core.cipher.PrivacyEncryptor;
import com.wechat.pay.java.core.http.DefaultHttpClientBuilder;
import com.wechat.pay.java.core.http.HostName;
import com.wechat.pay.java.core.http.HttpClient;
import com.wechat.pay.java.core.http.HttpHeaders;
import com.wechat.pay.java.core.http.HttpMethod;
import com.wechat.pay.java.core.http.HttpRequest;
import com.wechat.pay.java.core.http.HttpResponse;
import com.wechat.pay.java.core.http.JsonRequestBody;
import com.wechat.pay.java.core.http.MediaType;
import com.wechat.pay.java.core.http.RequestBody;
import com.wechat.pay.java.core.util.GsonUtil;

import java.util.Objects;

/**
 * TransferBillsService
 *
 * @author vscing
 * @date 2025/3/9 23:18
 */
public class TransferBillsService {

  private final HttpClient httpClient;
  private final HostName hostName;
  private final PrivacyEncryptor encryptor;
  private final PrivacyDecryptor decryptor;

  private TransferBillsService(HttpClient httpClient, HostName hostName, PrivacyEncryptor encryptor, PrivacyDecryptor decryptor) {
    this.httpClient = (HttpClient) Objects.requireNonNull(httpClient);
    this.hostName = hostName;
    this.encryptor = (PrivacyEncryptor)Objects.requireNonNull(encryptor);
    this.decryptor = (PrivacyDecryptor)Objects.requireNonNull(decryptor);
  }

  private RequestBody createRequestBody(Object request) {
    return (new JsonRequestBody.Builder()).body(GsonUtil.toJson(request)).build();
  }

  public static class Builder {
    private HttpClient httpClient;
    private HostName hostName;
    private PrivacyEncryptor encryptor;
    private PrivacyDecryptor decryptor;

    public Builder() {
    }

    public TransferBillsService.Builder config(Config config) {
      this.httpClient = (new DefaultHttpClientBuilder()).config(config).build();
      this.encryptor = config.createEncryptor();
      this.decryptor = config.createDecryptor();
      return this;
    }

    public TransferBillsService.Builder hostName(HostName hostName) {
      this.hostName = hostName;
      return this;
    }

    public TransferBillsService.Builder httpClient(HttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    public TransferBillsService.Builder encryptor(PrivacyEncryptor encryptor) {
      this.encryptor = encryptor;
      return this;
    }

    public TransferBillsService.Builder decryptor(PrivacyDecryptor decryptor) {
      this.decryptor = decryptor;
      return this;
    }

    public TransferBillsService build() {
      return new TransferBillsService(this.httpClient, this.hostName, this.encryptor, this.decryptor);
    }
  }

  public InitiateBillTransferResponse initiateBillTransfer(InitiateBillTransferRequest request) {
    String requestPath = "https://api.mch.weixin.qq.com/v3/fund-app/mch-transfer/transfer-bills";
    PrivacyEncryptor var10001 = this.encryptor;
    Objects.requireNonNull(var10001);
    InitiateBillTransferRequest realRequest = request.cloneWithCipher(var10001::encrypt);
    if (this.hostName != null) {
      requestPath = requestPath.replaceFirst(HostName.API.getValue(), this.hostName.getValue());
    }

    HttpHeaders headers = new HttpHeaders();
    headers.addHeader("Accept", MediaType.APPLICATION_JSON.getValue());
    headers.addHeader("Content-Type", MediaType.APPLICATION_JSON.getValue());
    headers.addHeader("Wechatpay-Serial", this.encryptor.getWechatpaySerial());
    HttpRequest httpRequest = (new HttpRequest.Builder()).httpMethod(HttpMethod.POST).url(requestPath).headers(headers).body(this.createRequestBody(realRequest)).build();
    HttpResponse<InitiateBillTransferResponse> httpResponse = this.httpClient.execute(httpRequest, InitiateBillTransferResponse.class);
    return (InitiateBillTransferResponse)httpResponse.getServiceResponse();
  }

}
