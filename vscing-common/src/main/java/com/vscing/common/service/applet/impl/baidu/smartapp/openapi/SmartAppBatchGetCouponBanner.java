package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppBatchGetCouponBanner {
    public static BatchGetCouponBannerResponse batchGetCouponBanner(BatchGetCouponBannerRequest params)
        throws BatchGetCouponBannerException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/v1.0/coupon/banner/batchGet?");
        uriBuilder.append("access_token=" + params.getAccessToken());
        uriBuilder.append("&couponId=" + params.getCouponID());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new BatchGetCouponBannerException(e.toString());
        } catch (InterruptedException e) {
            throw new BatchGetCouponBannerException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new BatchGetCouponBannerException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        BatchGetCouponBannerResponse res =
            gson.<BatchGetCouponBannerResponse>fromJson(response.body().toString(), BatchGetCouponBannerResponse.class);
        return res;
    }
}
