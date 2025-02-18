package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppGetAccessToken {
    public static GetAccessTokenResponse getAccessToken(GetAccessTokenRequest params) throws GetAccessTokenException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/oauth/2.0/token?");
        uriBuilder.append("grant_type=" + params.getGrantType());
        uriBuilder.append("&client_id=" + params.getClientID());
        uriBuilder.append("&client_secret=" + params.getClientSecret());
        uriBuilder.append("&scope=" + params.getScope());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new GetAccessTokenException(e.toString());
        } catch (InterruptedException e) {
            throw new GetAccessTokenException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new GetAccessTokenException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        GetAccessTokenResponse res =
            gson.<GetAccessTokenResponse>fromJson(response.body().toString(), GetAccessTokenResponse.class);
        return res;
    }
}
