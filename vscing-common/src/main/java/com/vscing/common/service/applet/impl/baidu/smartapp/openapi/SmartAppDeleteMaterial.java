package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppDeleteMaterial {
    public static DeleteMaterialResponse deleteMaterial(DeleteMaterialRequest params) throws DeleteMaterialException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/articlemount/material/delete?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("app_id=" + params.getAppID());
        bodyBuilder.append("&id=" + params.getID());
        bodyBuilder.append("&path=" + params.getPath());
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
            throw new DeleteMaterialException(e.toString());
        } catch (InterruptedException e) {
            throw new DeleteMaterialException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new DeleteMaterialException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        DeleteMaterialResponse res =
            gson.<DeleteMaterialResponse>fromJson(response.body().toString(), DeleteMaterialResponse.class);
        return res;
    }
}
