package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppOrderBill {
    public static OrderBillResponse orderBill(OrderBillRequest params) throws OrderBillException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/pay/paymentservice/orderBill?");
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
            throw new OrderBillException(e.toString());
        } catch (InterruptedException e) {
            throw new OrderBillException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new OrderBillException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        OrderBillResponse res = gson.<OrderBillResponse>fromJson(response.body().toString(), OrderBillResponse.class);
        return res;
    }
}
