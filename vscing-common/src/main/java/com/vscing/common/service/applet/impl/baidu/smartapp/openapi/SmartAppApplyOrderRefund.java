package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppApplyOrderRefund {
    public static ApplyOrderRefundResponse applyOrderRefund(ApplyOrderRefundRequest params)
        throws ApplyOrderRefundException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/pay/paymentservice/applyOrderRefund?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("applyRefundMoney=" + params.getApplyRefundMoney());
        bodyBuilder.append("&bizRefundBatchId=" + params.getBizRefundBatchID());
        bodyBuilder.append("&isSkipAudit=" + params.getIsSkipAudit());
        bodyBuilder.append("&orderId=" + params.getOrderID());
        bodyBuilder.append("&refundReason=" + params.getRefundReason());
        bodyBuilder.append("&refundType=" + params.getRefundType());
        bodyBuilder.append("&tpOrderId=" + params.getTpOrderID());
        bodyBuilder.append("&userId=" + params.getUserID());
        bodyBuilder.append("&refundNotifyUrl=" + params.getRefundNotifyURL());
        bodyBuilder.append("&pmAppKey=" + params.getPmAppKey());
        String postData = bodyBuilder.toString();

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ApplyOrderRefundException(e.toString());
        } catch (InterruptedException e) {
            throw new ApplyOrderRefundException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new ApplyOrderRefundException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        ApplyOrderRefundResponse res =
            gson.<ApplyOrderRefundResponse>fromJson(response.body().toString(), ApplyOrderRefundResponse.class);
        return res;
    }
}
