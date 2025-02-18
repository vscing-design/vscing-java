package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppModifyMaterial {
    public static ModifyMaterialResponse modifyMaterial(ModifyMaterialRequest params) throws ModifyMaterialException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/articlemount/material/modify?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("app_id=" + params.getAppID());
        bodyBuilder.append("&id=" + params.getID());
        bodyBuilder.append("&imageUrl=" + params.getImageURL());
        bodyBuilder.append("&title=" + params.getTitle());
        bodyBuilder.append("&path=" + params.getPath());
        bodyBuilder.append("&category1Code=" + params.getCategory1Code());
        bodyBuilder.append("&category2Code=" + params.getCategory2Code());
        bodyBuilder.append("&desc=" + params.getDesc());
        bodyBuilder.append("&labelAttr=" + params.getLabelAttr());
        bodyBuilder.append("&labelDiscount=" + params.getLabelDiscount());
        bodyBuilder.append("&buttonName=" + params.getButtonName());
        bodyBuilder.append("&bigImage=" + params.getBigImage());
        bodyBuilder.append("&verticalImage=" + params.getVerticalImage());
        bodyBuilder.append("&extJson=" + params.getExtJSON());
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
            throw new ModifyMaterialException(e.toString());
        } catch (InterruptedException e) {
            throw new ModifyMaterialException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new ModifyMaterialException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        ModifyMaterialResponse res =
            gson.<ModifyMaterialResponse>fromJson(response.body().toString(), ModifyMaterialResponse.class);
        return res;
    }
}
