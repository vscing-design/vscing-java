package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppGetQrCode {
    public static GetQrCodeResponse getQrCode(GetQrCodeRequest params) throws GetQrCodeException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/qrcode/getv2?");
        
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("path=" + params.getPath());
        bodyBuilder.append("&width=" + params.getWidth());
        bodyBuilder.append("&mf=" + params.getMf());
        bodyBuilder.append("&img_flag=" + params.getImgFlag());

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
            throw new GetQrCodeException(e.toString());
        } catch (InterruptedException e) {
            throw new GetQrCodeException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new GetQrCodeException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        GetQrCodeResponse res = gson.<GetQrCodeResponse>fromJson(response.body().toString(), GetQrCodeResponse.class);
        return res;
    }
}
