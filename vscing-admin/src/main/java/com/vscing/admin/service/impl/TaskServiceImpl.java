package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.admin.service.TaskService;
import com.vscing.common.util.HttpClientUtil;
import com.vscing.model.dto.AddressListDto;
import com.vscing.model.entity.Cinema;
import com.vscing.model.entity.City;
import com.vscing.model.entity.District;
import com.vscing.model.entity.Movie;
import com.vscing.model.entity.Province;
import com.vscing.model.mapper.CinemaMapper;
import com.vscing.model.mapper.CityMapper;
import com.vscing.model.mapper.DistrictMapper;
import com.vscing.model.mapper.MovieMapper;
import com.vscing.model.mapper.MovieProducerMapper;
import com.vscing.model.mapper.ProvinceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  private ProvinceMapper provinceMapper;

  @Autowired
  private CityMapper cityMapper;

  @Autowired
  private DistrictMapper areaMapper;

  @Autowired
  private CinemaMapper cinemaMapper;

  @Autowired
  private MovieMapper movieMapper;

  @Autowired
  private MovieProducerMapper movieProducerMapper;


  @Async("threadPoolTaskExecutor")
  @Override
  public void syncAddress() {

    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();

      // 发送请求并获取响应
      String responseBody = HttpClientUtil.postRequest(
          "https://test.ot.jfshou.cn/ticket/ticket_api/city/query",
          params
      );

      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();

      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

      // 遍历数据
      for (Map<String, Object> data : dataList) {
        String cityCode = (String) data.get("cityCode");
        Long cityId = objectMapper.convertValue(data.get("cityId"), Long.class);

        if(cityCode == null || cityId == null){
          continue;
        }
        // 尝试在 city 表中查找 cityCode
        City city = cityMapper.findByCode(cityCode);
        log.info("cityCode: {}, cityId: {}", cityCode, cityId);
        if (city != null) {
          log.info("city");
          // 如果找到，则更新 city 表中的 cityId
          cityMapper.updateCityId(city.getId(), cityId);
        } else {
          log.info("area");
          // 如果 city 表中找不到，尝试在 area 表中查找 cityCode
          District area = areaMapper.findByCode(cityCode);
          if (area != null) {
            log.info("areaCity");
            // 如果找到，则更新 area 表中的 cityId
            areaMapper.updateCity(area.getId(), cityId);
          }
        }

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

      log.info("同步地址结束");

    } catch (Exception e) {
      log.error("同步地址失败", e);
    }
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncCinema() {
    // 获取城市
    List<City> cityList = cityMapper.getList(new AddressListDto());
    for(City city : cityList){
      Province province = provinceMapper.selectById(city.getProvinceId());
      if(province == null){
        continue;
      }

      try {
        // 准备请求参数
        Map<String, String> params = new HashMap<>();
        params.put("cityId", String.valueOf(city.getS1CityId()));

        // 发送请求并获取响应
        String responseBody = HttpClientUtil.postRequest(
            "https://test.ot.jfshou.cn/ticket/ticket_api/cinema/query",
            params
        );

        // 将 JSON 字符串解析为 JsonNode 对象
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

        for (Map<String, Object> data : dataList) {
          Long cinemaId = objectMapper.convertValue(data.get("cinemaId"), Long.class);
          String cinemaName = (String) data.get("cinemaName");
          String cinemaAddress = (String) data.get("cinemaAddress");
          Double longitude = (Double) data.get("longitude");
          Double latitude = (Double) data.get("latitude");
          // 可能没有
          String cinemaPhone = (String) data.get("cinemaPhone");
          String regionName = (String) data.get("regionName");

          log.info("cinemaId: {}, cinemaName: {}, cinemaAddress: {}, longitude: {}, latitude: {}, cinemaPhone: {}, regionName: {}",
              cinemaId, cinemaName, cinemaAddress, longitude, latitude, cinemaPhone, regionName);

          if(cinemaId != null || cinemaName != null || cinemaAddress != null || longitude != null || latitude != null){

            Cinema cinema = new Cinema();
            cinema.setId(IdUtil.getSnowflakeNextId());
            cinema.setSupplierId(1869799230973227008L);
            cinema.setTpCinemaId(cinemaId);
            cinema.setName(cinemaName);
            cinema.setAddress(cinemaAddress);
            cinema.setLng(longitude);
            cinema.setLat(latitude);

            cinema.setProvinceId(province.getId());
            cinema.setProvinceName(province.getName());
            cinema.setCityId(city.getId());
            cinema.setCityName(city.getName());
            cinema.setCreatedBy(0L);
            if(cinemaPhone != null){
              cinema.setPhone(cinemaPhone);
            }
            if(regionName != null){
              District district = areaMapper.findByName(regionName, city.getId());
              if(district != null){
                cinema.setDistrictId(district.getId());
                cinema.setDistrictName(district.getName());
              }
            }
            int res = cinemaMapper.insert(cinema);
            log.info("res: {}", res);
          }
        }

        log.info(city.getName() + "同步地址结束");

      } catch (Exception e) {
        log.error(city.getName() + "同步影院失败", e);
      }
    }

    log.info("全部影院同步结束");
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncMovie() {

    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();

      // 发送请求并获取响应
      String responseBody = HttpClientUtil.postRequest(
          "https://test.ot.jfshou.cn/ticket/ticket_api/film/query",
          params
      );

      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();

      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

      for (Map<String, Object> data : dataList) {
        Long movieId = objectMapper.convertValue(data.get("movieId"), Long.class);
        String movieName = (String) data.get("movieName");
        Integer duration = (Integer) data.get("duration");
        String publishDate = (String) data.get("publishDate");
        String director = (String) data.get("director");
        String cast = (String) data.get("cast");
        String intro = (String) data.get("intro");
        String versionType = (String) data.get("versionType");
        String language = (String) data.get("language");
        String movieType = (String) data.get("movieType");
        String posterUrl = (String) data.get("posterUrl");
        String plotUrl = (String) data.get("plotUrl");
        String grade = (String) data.get("grade");
        Integer like = (Integer) data.get("like");
        String publishStatus = (String) data.get("publishStatus");
        Object producer = data.get("producer");

        log.info("movieId: {}, movieName: {}, duration: {}, publishDate: {}, director: {}, cast: {}, intro: {}, versionType: {}, language: {}, movieType: {}, posterUrl: {}, plotUrl: {}, grade: {}, like: {}, publishStatus: {}",
            movieId, movieName, duration, publishDate, director, cast, intro, versionType, language, movieType, posterUrl, plotUrl, grade, like, publishStatus);

        Movie movie = new Movie();
        long id = IdUtil.getSnowflakeNextId();
        movie.setId(id);
        movie.setTpMovieId(movieId);
        movie.setName(movieName);
        movie.setDuration(duration);
        movie.setPublishDate(LocalDateTime.parse(publishDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        movie.setDirector(director);
        movie.setCast(cast);
        movie.setIntro(intro);
        movie.setVersionType(versionType);
        movie.setLanguage(language);
        movie.setMovieType(movieType);
        movie.setPosterUrl(posterUrl);
        movie.setPlotUrl(plotUrl);
        movie.setGrade(grade);
        movie.setLike(like);
        movie.setPublishStatus(publishStatus);

        // 收集 actors 和 directors
        if (producer instanceof Map) {
          @SuppressWarnings("unchecked")
          Map<String, Object> producerMap = (Map<String, Object>) producer;

          List<Map<String, Object>> actorsList = (List<Map<String, Object>>) producerMap.get("actors");
          List<Map<String, Object>> directorsList = (List<Map<String, Object>>) producerMap.get("directors");

          if (actorsList != null && !actorsList.isEmpty()) {
            for (Map<String, Object> actors : actorsList) {
              String avatar = (String) actors.get("avatar");
              log.info("avatar: {}", avatar);
            }
          }

          if (directorsList != null && !directorsList.isEmpty()) {
            for (Map<String, Object> directors : directorsList) {
              String avatar = (String) directors.get("avatar");
              log.info("avatar: {}", avatar);
            }
          }




        }

      }
      log.info("同步影片结束");
    } catch (Exception e) {
      log.error("同步影片失败", e);
    }
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncShow() {
    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("cinemaId", "763");
      // 获取当天的 LocalDate 对象
      LocalDateTime endOfDayPrecise = LocalDate.now().atTime(23, 59, 59);
      // 定义日期时间格式
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      // 格式化为字符串
      String startTimeString = endOfDayPrecise.format(formatter);
//      params.put("time", startTimeString);
      log.info("startTimeString: {}", startTimeString);

      // 发送请求并获取响应
      String responseBody = HttpClientUtil.postRequest(
          "https://test.ot.jfshou.cn/ticket/ticket_api/show/preferential/query",
          params
      );

      log.info("responseBody: {}", responseBody);

      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();

      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      Map<String, Object> data = (Map<String, Object>) responseMap.get("data");

      List<Map<String, Object>> showInfor = (List<Map<String, Object>>) data.get("showInfor");
      log.info("showInfor: {}", showInfor);

    } catch (Exception e) {
      log.error("同步场次失败", e);
    }
  }
}
