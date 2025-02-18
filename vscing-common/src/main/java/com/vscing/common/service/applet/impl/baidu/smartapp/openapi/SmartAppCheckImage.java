package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public class SmartAppCheckImage {
    public static CheckImageResponse checkImage(CheckImageRequest params, File file)
        throws CheckImageException, IOException {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://openapi.baidu.com");
        uriBuilder.append("/file/2.0/smartapp/riskDetection/v2/syncCheckImage?");
        uriBuilder.append("access_token=" + params.getAccessToken());

        uriBuilder.append("&sp_sdk_ver=" + "1.0.0");
        uriBuilder.append("&sp_sdk_lang=" + "java");

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().build();
        okhttp3.RequestBody body =
            new okhttp3.MultipartBody.Builder()
                .setType(okhttp3.MultipartBody.FORM)
                .addFormDataPart("type", params.getType())
                .addFormDataPart(
                    "image",
                    file.getName(),
                    okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), file))
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(uriBuilder.toString()).method("POST", body).build();

        okhttp3.Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        CheckImageResponse res = gson.<CheckImageResponse>fromJson(response.body().string(), CheckImageResponse.class);
        return res;
    }
}
