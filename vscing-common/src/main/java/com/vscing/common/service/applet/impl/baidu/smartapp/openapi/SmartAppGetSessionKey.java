package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppGetSessionKey {
    public static GetSessionKeyResponse getSessionKey(GetSessionKeyRequest params) throws GetSessionKeyException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://spapi.baidu.com");
        uriBuilder.append("/oauth/jscode2sessionkey?");
        uriBuilder.append("code=" + params.getCode());
        uriBuilder.append("&client_id=" + params.getClientID());
        uriBuilder.append("&sk=" + params.getSk());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        HttpRequest request =
            HttpRequest.newBuilder().uri(URI.create(uriBuilder.toString())).header("Content-Type", "").GET().build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new GetSessionKeyException(e.toString());
        } catch (InterruptedException e) {
            throw new GetSessionKeyException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new GetSessionKeyException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        GetSessionKeyResponse res =
            gson.<GetSessionKeyResponse>fromJson(response.body().toString(), GetSessionKeyResponse.class);
        return res;
    }
}
