package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAppGetTemplateLibraryList {
    public static GetTemplateLibraryListResponse getTemplateLibraryList(GetTemplateLibraryListRequest params)
        throws GetTemplateLibraryListException {

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/rest/2.0/smartapp/template/librarylist?");
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
            throw new GetTemplateLibraryListException(e.toString());
        } catch (InterruptedException e) {
            throw new GetTemplateLibraryListException(e.toString());
        }
        if (response.statusCode() != 200) {
            throw new GetTemplateLibraryListException("StatusCode not 200!");
        }

        Gson gson = new Gson();
        GetTemplateLibraryListResponse res =
            gson.<GetTemplateLibraryListResponse>fromJson(
                response.body().toString(), GetTemplateLibraryListResponse.class);
        return res;
    }
}
