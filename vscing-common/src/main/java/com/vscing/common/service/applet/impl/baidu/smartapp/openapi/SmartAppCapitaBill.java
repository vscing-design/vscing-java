package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppCapitaBill {
    public static CapitaBillResponse capitaBill(CapitaBillRequest params) throws CapitaBillException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/pay/paymentservice/capitaBill?");
        uriBuilder.append("access_token=" + params.getAccessToken());
        uriBuilder.append("&billTime=" + params.getBillTime());
        uriBuilder.append("&pmAppKey=" + params.getPmAppKey());

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
            throw new CapitaBillException(e.toString());
        } catch (InterruptedException e) {
            throw new CapitaBillException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new CapitaBillException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        CapitaBillResponse res =
            gson.<CapitaBillResponse>fromJson(response.body().toString(), CapitaBillResponse.class);
        return res;
    }
}
