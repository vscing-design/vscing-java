package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppFindByTpOrderID {
    public static FindByTpOrderIDResponse findByTpOrderId(FindByTpOrderIDRequest params)
        throws FindByTpOrderIDException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/pay/paymentservice/findByTpOrderId?");
        uriBuilder.append("access_token=" + params.getAccessToken());
        uriBuilder.append("&tpOrderId=" + params.getTpOrderID());
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
            throw new FindByTpOrderIDException(e.toString());
        } catch (InterruptedException e) {
            throw new FindByTpOrderIDException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new FindByTpOrderIDException("StatusCode not 200!");
        }

        String jsonResponse = response.body().toString();

        try {
            // 解析整个 JSON 响应
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject dataObject = jsonObject.getAsJsonObject("data");

            // 将 bizInfo 字段设置为空字符串
            dataObject.addProperty("bizInfo", "");

            // 将整个 JSON 对象转回字符串
            jsonResponse = jsonObject.toString();

            Gson gson = new Gson();
            FindByTpOrderIDResponse res =
                gson.<FindByTpOrderIDResponse>fromJson(jsonResponse, FindByTpOrderIDResponse.class);
            return res;

        } catch (Exception e) {
            throw new FindByTpOrderIDException("bizInfo error!");
        }
    }
}
