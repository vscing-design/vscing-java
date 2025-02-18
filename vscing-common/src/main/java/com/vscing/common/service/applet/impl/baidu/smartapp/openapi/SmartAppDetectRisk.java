package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppDetectRisk {
    public static DetectRiskResponse detectRisk(DetectRiskRequest params) throws DetectRiskException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/detectrisk?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("appkey=" + params.getAppkey());
        bodyBuilder.append("&xtoken=" + params.getXtoken());
        bodyBuilder.append("&type=" + params.getType());
        bodyBuilder.append("&clientip=" + params.getClientip());
        bodyBuilder.append("&ts=" + params.getTs());
        bodyBuilder.append("&ev=" + params.getEv());
        bodyBuilder.append("&useragent=" + params.getUseragent());
        bodyBuilder.append("&phone=" + params.getPhone());
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
            throw new DetectRiskException(e.toString());
        } catch (InterruptedException e) {
            throw new DetectRiskException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new DetectRiskException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        DetectRiskResponse res =
            gson.<DetectRiskResponse>fromJson(response.body().toString(), DetectRiskResponse.class);
        return res;
    }
}
