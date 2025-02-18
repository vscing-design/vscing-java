package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public class SmartAppUploadCouponImage {
    public static UploadCouponImageResponse uploadCouponImage(UploadCouponImageRequest params, File file)
        throws UploadCouponImageException, IOException {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().build();
        okhttp3.RequestBody body =
            new okhttp3.MultipartBody.Builder()
                .setType(okhttp3.MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    file.getName(),
                    okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), file))
                .build();
        okhttp3.Request request =
            new okhttp3.Request.Builder()
                .url(
                    "https://openapi.baidu.com/file/2.0/smartapp/v1.0/coupon/upload/image?access_token="
                        + params.getAccessToken())
                .method("POST", body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();

        Gson gson = new Gson();
        UploadCouponImageResponse res =
            gson.<UploadCouponImageResponse>fromJson(response.body().string(), UploadCouponImageResponse.class);
        return res;
    }
}
