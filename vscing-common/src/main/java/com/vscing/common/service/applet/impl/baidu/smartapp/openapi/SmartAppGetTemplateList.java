package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppGetTemplateList {
    public static GetTemplateListResponse getTemplateList(GetTemplateListRequest params)
        throws GetTemplateListException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/template/templatelist?");
        uriBuilder.append("access_token=" + params.getAccessToken());
        uriBuilder.append("&offset=" + params.getOffset());
        uriBuilder.append("&count=" + params.getCount());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        HttpRequest request =
            HttpRequest.newBuilder().uri(URI.create(uriBuilder.toString())).header("Content-Type", "").GET().build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new GetTemplateListException(e.toString());
        } catch (InterruptedException e) {
            throw new GetTemplateListException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new GetTemplateListException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        GetTemplateListResponse res =
            gson.<GetTemplateListResponse>fromJson(response.body().toString(), GetTemplateListResponse.class);
        return res;
    }
}
