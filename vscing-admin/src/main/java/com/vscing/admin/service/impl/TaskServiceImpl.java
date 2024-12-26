package com.vscing.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.admin.service.TaskService;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TaskServiceImpl
 *
 * @author vscing
 * @date 2024/12/26 23:39
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private CityMapper cityMapper;

  @Autowired
  private DistrictMapper areaMapper;

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncAddress() {

    // 获取当前时间戳
    long timestamp = Instant.now().toEpochMilli();

    // 准备请求参数
    Map<String, String> params = new HashMap<>();
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

    } catch (Exception e) {
      log.debug("Exception: {}", e);
    }


  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncCinema() {
//    4.1 接口地址
//    https://ot.jfshou.cn/ticket/ticket_api/cinema/query
//    4.2 接口描述
//        获取某城市或地区下的影院列表
//    4.3 请求参数
//    参数名	类型	是否必传	示例值	描述
//    userNo	string	是	18888888888	此为合作商户编号，可通过商务人员获取
//    cityId	int	是	2	城市id
//    regionId	int	否	158	地区id
//    timestamp	string	是	1632638476740	13位毫秒级时间戳
//    sign	string	是	8526842F6D6C31424A234193BFBAD7F5	签名，详见签名规则

//    data信息：
//    参数名	类型	是否必传	示例值	描述
//    cinemaId	int	是	809	影院id
//    cinemaName	string	是	北京百老汇影城国瑞购物中心店	影院名称
//    cinemaAddress	string	是	东城区崇外大街18号国瑞购物中心首层	影院地址
//    cinemaPhone	string	否	010-84388257	影院电话
//    longitude	double	是	116.438574	经度
//    latitude	double	是	39.950376	纬度
//    regionName	string	否	东城区	地区


  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncMovie() {
//    5.1 接口地址
//    https://ot.jfshou.cn/ticket/ticket_api/film/query
//    5.2 接口描述
//        获取影片列表
//    5.3 请求参数
//    参数名	类型	是否必传	示例值	描述
//    userNo	string	是	18888888888	此为合作商户编号，可通过商务人员获取
//    timestamp	string	是	1632638476740	13位毫秒级时间戳
//    sign	string	是	8526842F6D6C31424A234193BFBAD7F5	签名，详见签名规则
//    5.4 响应参数
//    参数名	类型	是否必传	示例值	描述
//    code	int	是	200	错误代码，具体可参考响应提示代码表
//    message	string	是	OK	错误信息，具体可参考响应提示代码表
//    data	array	否		影片列表

  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncShow() {
//    6.1 接口地址
//    https://ot.jfshou.cn/ticket/ticket_api/show/preferential/query
//    6.2 接口描述
//        获取影院下场次列表
//    6.3 请求参数
//    参数名	类型	是否必传	示例值	描述
//    userNo	string	是	18888888888	此为合作商户编号，可通过商务人员获取
//    cinemaId	int	是	763	院线id
//    time	string	否	2021-11-01 00:00:00	查询当前该时间至该时间范围内的场次信息，参数需大于当前时间
//    timestamp	string	是	1632638476740	13位毫秒级时间戳
//    sign	string	是	8526842F6D6C31424A234193BFBAD7F5	签名，详见签名规则
//    6.4 响应参数
//    参数名	类型	是否必传	示例值	描述
//    code	int	是	200	错误代码，具体可参考响应提示代码表
//    message	string	是	OK	错误信息，具体可参考响应提示代码表
//    data	object	否		场次信息
  }
}
