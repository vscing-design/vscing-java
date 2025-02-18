package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppSubmitSkuCoupon {
    public static SubmitSkuCouponResponse submitSkuCoupon(SubmitSkuCouponRequest params)
        throws SubmitSkuCouponException {
        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/server/submit/skuCoupon?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        String postData = "";
        Gson gpostdata = new Gson();
        postData = gpostdata.toJson(params.getSubmitSkuCouponRequestPostBodyItem());
        System.out.println(postData);

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
            throw new SubmitSkuCouponException(e.toString());
        } catch (InterruptedException e) {
            throw new SubmitSkuCouponException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new SubmitSkuCouponException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        SubmitSkuCouponResponse res =
            gson.<SubmitSkuCouponResponse>fromJson(response.body().toString(), SubmitSkuCouponResponse.class);
        return res;
    }
}
