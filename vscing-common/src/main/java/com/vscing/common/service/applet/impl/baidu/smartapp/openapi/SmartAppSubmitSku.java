package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppSubmitSku {
    public static SubmitSkuResponse submitSku(SubmitSkuRequest params) throws SubmitSkuException {
        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/server/submit/sku?");
        uriBuilder.append("access_token=" + params.getAccessToken());
        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        String postData = "";
        Gson gpostdata = new Gson();
        postData = gpostdata.toJson(params.getSubmitSkuRequestPostBodyItem());

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new SubmitSkuException(e.toString());
        } catch (InterruptedException e) {
            throw new SubmitSkuException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new SubmitSkuException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        SubmitSkuResponse res = gson.<SubmitSkuResponse>fromJson(response.body().toString(), SubmitSkuResponse.class);
        return res;
    }
}
