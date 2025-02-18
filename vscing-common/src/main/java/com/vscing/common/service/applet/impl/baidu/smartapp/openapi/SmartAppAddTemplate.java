package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppAddTemplate {
    public static AddTemplateResponse addTemplate(AddTemplateRequest params) throws AddTemplateException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/template/templateadd?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("id=" + params.getID());
        bodyBuilder.append("&keyword_id_list=" + params.getKeywordIDList());
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
            throw new AddTemplateException(e.toString());
        } catch (InterruptedException e) {
            throw new AddTemplateException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new AddTemplateException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        AddTemplateResponse res =
            gson.<AddTemplateResponse>fromJson(response.body().toString(), AddTemplateResponse.class);
        return res;
    }
}
