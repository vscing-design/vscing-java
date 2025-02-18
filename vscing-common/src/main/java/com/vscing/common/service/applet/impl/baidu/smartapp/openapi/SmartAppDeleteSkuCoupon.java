package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppDeleteSkuCoupon {
    public static DeleteSkuCouponResponse deleteSkuCoupon(DeleteSkuCouponRequest params)
        throws DeleteSkuCouponException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/server/delete/skuCoupon?");
        uriBuilder.append("access_token=" + params.getAccessToken());
        uriBuilder.append("&app_id=" + params.getAppID());
        uriBuilder.append("&path_list=" + params.getPathList());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new DeleteSkuCouponException(e.toString());
        } catch (InterruptedException e) {
            throw new DeleteSkuCouponException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new DeleteSkuCouponException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        DeleteSkuCouponResponse res =
            gson.<DeleteSkuCouponResponse>fromJson(response.body().toString(), DeleteSkuCouponResponse.class);
        return res;
    }
}
