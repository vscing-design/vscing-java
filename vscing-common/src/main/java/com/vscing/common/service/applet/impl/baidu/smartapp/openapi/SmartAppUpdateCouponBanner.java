package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SmartAppUpdateCouponBanner {
    public static UpdateCouponBannerResponse updateCouponBanner(UpdateCouponBannerRequest params)
        throws UpdateCouponBannerException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/v1.0/coupon/banner/update?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        Map<String, Object> applicationJsonDataMap = new HashMap<String, Object>();

        applicationJsonDataMap.put("appRedirectPath", params.getAppRedirectPath());
        applicationJsonDataMap.put("bannerId", params.getBannerID());
        applicationJsonDataMap.put("couponId", params.getCouponID());
        applicationJsonDataMap.put("picUrl", params.getPicURL());
        applicationJsonDataMap.put("title", params.getTitle());
        String postData = new Gson().toJson(applicationJsonDataMap);

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new UpdateCouponBannerException(e.toString());
        } catch (InterruptedException e) {
            throw new UpdateCouponBannerException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new UpdateCouponBannerException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        UpdateCouponBannerResponse res =
            gson.<UpdateCouponBannerResponse>fromJson(response.body().toString(), UpdateCouponBannerResponse.class);
        return res;
    }
}
