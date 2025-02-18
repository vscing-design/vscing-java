package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppSubmitResource {
    public static SubmitResourceResponse submitResource(SubmitResourceRequest params) throws SubmitResourceException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/access/submitresource?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("app_id=" + params.getAppID());
        bodyBuilder.append("&body=" + params.getBody());
        bodyBuilder.append("&ext=" + params.getExt());
        bodyBuilder.append("&feed_sub_type=" + params.getFeedSubType());
        bodyBuilder.append("&feed_type=" + params.getFeedType());
        bodyBuilder.append("&images=" + params.getImages());
        bodyBuilder.append("&mapp_sub_type=" + params.getMappSubType());
        bodyBuilder.append("&mapp_type=" + params.getMappType());
        bodyBuilder.append("&path=" + params.getPath());
        bodyBuilder.append("&tags=" + params.getTags());
        bodyBuilder.append("&title=" + params.getTitle());
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
            throw new SubmitResourceException(e.toString());
        } catch (InterruptedException e) {
            throw new SubmitResourceException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new SubmitResourceException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        SubmitResourceResponse res =
            gson.<SubmitResourceResponse>fromJson(response.body().toString(), SubmitResourceResponse.class);
        return res;
    }
}
