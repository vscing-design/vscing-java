package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppSendTemplateMessage {
    public static SendTemplateMessageResponse sendTemplateMessage(SendTemplateMessageRequest params)
        throws SendTemplateMessageException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/template/send?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("template_id=" + params.getTemplateID());
        bodyBuilder.append("&touser_openId=" + params.getTouserOpenID());
        bodyBuilder.append("&data=" + params.getData());
        bodyBuilder.append("&page=" + params.getPage());
        bodyBuilder.append("&scene_id=" + params.getSceneID());
        bodyBuilder.append("&scene_type=" + params.getSceneType());
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
            throw new SendTemplateMessageException(e.toString());
        } catch (InterruptedException e) {
            throw new SendTemplateMessageException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new SendTemplateMessageException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        SendTemplateMessageResponse res =
            gson.<SendTemplateMessageResponse>fromJson(response.body().toString(), SendTemplateMessageResponse.class);
        return res;
    }
}
