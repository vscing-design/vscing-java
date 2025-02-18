package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppGetSessionKeyV2 {
    public static GetSessionKeyV2Response getSessionKeyV2(GetSessionKeyV2Request params)
        throws GetSessionKeyV2Exception {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/getsessionkey?");
        uriBuilder.append("code=" + params.getCode());
        uriBuilder.append("&access_token=" + params.getAccessToken());
        uriBuilder.append("&client_id=" + params.getClientID());
        uriBuilder.append("&sk=" + params.getSk());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new GetSessionKeyV2Exception(e.toString());
        } catch (InterruptedException e) {
            throw new GetSessionKeyV2Exception(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new GetSessionKeyV2Exception("StatusCode not 200!");
        }

        Gson gson = new Gson();
        GetSessionKeyV2Response res =
            gson.<GetSessionKeyV2Response>fromJson(response.body().toString(), GetSessionKeyV2Response.class);
        return res;
    }
}
