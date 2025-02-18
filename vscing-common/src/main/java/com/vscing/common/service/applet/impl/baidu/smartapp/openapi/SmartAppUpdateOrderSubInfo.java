package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SmartAppUpdateOrderSubInfo {
    public static UpdateOrderSubInfoResponse updateOrderSubInfo(UpdateOrderSubInfoRequest params)
        throws UpdateOrderSubInfoException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/ordercenter/app/update/sub/info?");
        uriBuilder.append("access_token=" + params.getAccessToken());
        uriBuilder.append("&open_id=" + params.getOpenID());
        uriBuilder.append("&scene_id=" + params.getSceneID());
        uriBuilder.append("&scene_type=" + params.getSceneType());
        uriBuilder.append("&pm_app_key=" + params.getPmAppKey());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        Map<String, Object> applicationJsonDataMap = new HashMap<String, Object>();

        applicationJsonDataMap.put("Data", params.getData());
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
            throw new UpdateOrderSubInfoException(e.toString());
        } catch (InterruptedException e) {
            throw new UpdateOrderSubInfoException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new UpdateOrderSubInfoException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        UpdateOrderSubInfoResponse res =
            gson.<UpdateOrderSubInfoResponse>fromJson(response.body().toString(), UpdateOrderSubInfoResponse.class);
        return res;
    }
}
