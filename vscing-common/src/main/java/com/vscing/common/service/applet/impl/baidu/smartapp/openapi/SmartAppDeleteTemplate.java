package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppDeleteTemplate {
    public static DeleteTemplateResponse deleteTemplate(DeleteTemplateRequest params) throws DeleteTemplateException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/template/templatedel?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("template_id=" + params.getTemplateID());
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
            throw new DeleteTemplateException(e.toString());
        } catch (InterruptedException e) {
            throw new DeleteTemplateException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new DeleteTemplateException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        DeleteTemplateResponse res =
            gson.<DeleteTemplateResponse>fromJson(response.body().toString(), DeleteTemplateResponse.class);
        return res;
    }
}
