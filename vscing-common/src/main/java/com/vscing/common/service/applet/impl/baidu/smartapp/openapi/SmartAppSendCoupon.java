package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppSendCoupon {
    public static SendCouponResponse sendCoupon(SendCouponRequest params) throws SendCouponException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/v1.0/coupon/send?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("couponId=" + params.getCouponID());
        bodyBuilder.append("&openId=" + params.getOpenID());
        bodyBuilder.append("&uniqueSendingIdentity=" + params.getUniqueSendingIdentity());
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
            throw new SendCouponException(e.toString());
        } catch (InterruptedException e) {
            throw new SendCouponException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new SendCouponException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        SendCouponResponse res =
            gson.<SendCouponResponse>fromJson(response.body().toString(), SendCouponResponse.class);
        return res;
    }
}
