package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppCustomSend {
    public static CustomSendResponse customSend(CustomSendRequest params) throws CustomSendException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/message/custom/send?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("user_type=" + params.getUserType());
        bodyBuilder.append("&open_id=" + params.getOpenID());
        bodyBuilder.append("&msg_type=" + params.getMsgType());
        bodyBuilder.append("&content=" + params.getContent());
        bodyBuilder.append("&pic_url=" + params.getPicURL());
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
            throw new CustomSendException(e.toString());
        } catch (InterruptedException e) {
            throw new CustomSendException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new CustomSendException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        CustomSendResponse res =
            gson.<CustomSendResponse>fromJson(response.body().toString(), CustomSendResponse.class);
        return res;
    }
}
