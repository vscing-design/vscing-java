package com.vscing.admin.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.common.api.CommonResult;
import com.vscing.common.util.SignatureGenerator;
import com.vscing.model.entity.District;
import com.vscing.model.mapper.CityMapper;
import com.vscing.model.mapper.DistrictMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TestController
 *
 * @date 2024/12/11 00:51
 * @auth vscing(vscing @ foxmail.com)
 */
@Slf4j
@RestController
@RequestMapping("/v1/test")
public class TestController {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private DistrictMapper areaMapper;


    // https://docs.qq.com/doc/DWXVlWmtHbnpLUXdT
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<Object> listAll() {

        // 获取当前时间戳
        Instant now = Instant.now();
        long timestamp = now.toEpochMilli();

        // 准备请求参数
        Map<String, String> params = new HashMap<>(3);
        String timestampStr = String.valueOf(timestamp);
        params.put("userNo", "17856563214");
        params.put("timestamp", timestampStr);

        // 秘钥
        String key = "BA19B011B243479B8A90CEA61A7286AF";

        // 生成签名
        String sign = SignatureGenerator.generateSignature(params, key);
        params.put("sign", sign);

        // 日志记录：打印请求参数
        log.info("Request Parameters: {}", params);

        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient();

        // 构建 multipart/form-data 请求体
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        MultipartBody requestBody = multipartBuilder.build();

        // 创建 POST 请求
        Request request = new Request.Builder()
                .url("https://test.ot.jfshou.cn/ticket/ticket_api/city/query")
                .post(requestBody)
                .build();

        // 发送请求并获取响应
        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            Map<String, Object> result = new HashMap<>(2);

            // 将 JSON 字符串解析为 JsonNode 对象
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> responseMap = objectMapper.readValue(response.body().string(), Map.class);
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

            for (Map<String, Object> data : dataList) {
//                String cityCode = (String) data.get("cityCode");
                Long cityId = objectMapper.convertValue(data.get("cityId"), Long.class);

                // 尝试在 city 表中查找 cityCode
//                City city = cityMapper.findByCode(cityCode);
//                log.info("cityCode: {}, cityId: {}", cityCode, cityId);
//                if (city != null) {
//                    log.info("city");
//                    // 如果找到，则更新 city 表中的 cityId
//                    cityMapper.updateCityId(city.getId(), cityId);
//                } else {
//                    log.info("area");
//                    // 如果 city 表中找不到，尝试在 area 表中查找 cityCode
//                    District area = areaMapper.findByCode(cityCode);
//                    if (area != null) {
//                        log.info("areaCity");
//                        // 如果找到，则更新 area 表中的 cityId
//                        areaMapper.updateCity(area.getId(), cityId);
//                    }
//                }

                // 遍历 regions 并更新 area 表中的 regionId
                List<Map<String, Object>> regions = (List<Map<String, Object>>) data.get("regions");
                if (regions != null) {
                    for (Map<String, Object> region : regions) {
                        String regionName = (String) region.get("regionName");
                        Long regionId = objectMapper.convertValue(region.get("regionId"), Long.class);
                        log.info("regionName: {}, regionId: {}", regionName, regionId);
                        if(regionName != null && regionId != null){
                            District areaByRegionName = areaMapper.findByName(regionName, cityId);
                            if (areaByRegionName != null) {
                                log.info("areaRegion", areaByRegionName.getId());
                                areaMapper.updateRegion(areaByRegionName.getId(), regionId);
                            }
                        }

                    }
                }
            }

            return CommonResult.success(result);

        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }
}
