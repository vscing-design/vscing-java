package com.vscing.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.admin.service.MovieService;
import com.vscing.admin.service.OrderService;
import com.vscing.admin.service.ShowService;
import com.vscing.admin.service.TaskService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.model.dto.AddressListDto;
import com.vscing.model.dto.CinemaListDto;
import com.vscing.model.entity.Cinema;
import com.vscing.model.entity.City;
import com.vscing.model.entity.District;
import com.vscing.model.entity.Movie;
import com.vscing.model.entity.MovieProducer;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.Province;
import com.vscing.model.entity.Show;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.http.HttpOrder;
import com.vscing.model.mapper.CinemaMapper;
import com.vscing.model.mapper.CityMapper;
import com.vscing.model.mapper.DistrictMapper;
import com.vscing.model.mapper.MovieMapper;
import com.vscing.model.mapper.MovieProducerMapper;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.ProvinceMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

  private static final String ORDER_STATUS_GENERATE_SUCCESS = "GENERATE_SUCCESS";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private SupplierServiceFactory supplierServiceFactory;

  @Autowired
  private OrderService orderService;

  @Autowired
  private MovieService movieService;

  @Autowired
  private ShowService showService;

  @Autowired
  private ProvinceMapper provinceMapper;

  @Autowired
  private CityMapper cityMapper;

  @Autowired
  private DistrictMapper districtMapper;

  @Autowired
  private CinemaMapper cinemaMapper;

  @Autowired
  private MovieMapper movieMapper;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private MovieProducerMapper movieProducerMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private ShowMapper showMapper;

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncTable() {
    // 日期
    String dateSuffix = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    // 新增扩展表
    String showTableName = "vscing_show_" + dateSuffix;
    String showAreaTableName = "vscing_show_area_" + dateSuffix;

    String createShowTableSQL = String.format(
        "CREATE TABLE IF NOT EXISTS %s LIKE vscing_show",
        showTableName
    );
    String createShowAreaTableSQL = String.format(
        "CREATE TABLE IF NOT EXISTS %s LIKE vscing_show_area",
        showAreaTableName
    );

    jdbcTemplate.execute(createShowTableSQL);
    jdbcTemplate.execute(createShowAreaTableSQL);

    // 迁移数据
    String migrateShowSQL = String.format(
        "INSERT INTO %s SELECT * FROM vscing_show WHERE stop_sell_time >= CURDATE()",
        showTableName
    );
    jdbcTemplate.update(migrateShowSQL);

    String migrateShowAreaSQL = String.format(
        "INSERT INTO %s SELECT * FROM vscing_show_area WHERE show_id IN " +
            "(SELECT id FROM vscing_show WHERE stop_sell_time >= CURDATE())",
        showAreaTableName
    );
    jdbcTemplate.update(migrateShowAreaSQL);

    // 删除已迁移的数据，确保事务一致性
    jdbcTemplate.update("DELETE FROM vscing_show_area WHERE show_id IN " +
        "(SELECT id FROM vscing_show WHERE stop_sell_time < CURDATE())");
    jdbcTemplate.update("DELETE FROM vscing_show WHERE stop_sell_time < CURDATE()");
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncAddress() {

    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();

      SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");

      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/city/query", params);

      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();

      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

      if (dataList == null) {
        log.info("dataList is null");
        return;
      }

      // 遍历数据
      for (Map<String, Object> data : dataList) {
        String cityCode = (String) data.get("cityCode");
        Long cityId = objectMapper.convertValue(data.get("cityId"), Long.class);

        if(cityCode == null || cityId == null){
          continue;
        }

        try {
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
            District area = districtMapper.findByCode(cityCode);
            if (area != null) {
              log.info("areaCity");
              // 如果找到，则更新 area 表中的 cityId
              districtMapper.updateCity(area.getId(), cityId);
            }
          }
        } catch (Exception e) {
          log.error("cityCode: {}, cityId: {}", cityCode, cityId, e);
          continue;
        }

        // 遍历 regions 并更新 area 表中的 regionId
        List<Map<String, Object>> regions = (List<Map<String, Object>>) data.get("regions");
        if (regions != null) {
          for (Map<String, Object> region : regions) {
            try {
              String regionName = (String) region.get("regionName");
              Long regionId = objectMapper.convertValue(region.get("regionId"), Long.class);
              log.info("regionName: {}, regionId: {}", regionName, regionId);
              if(regionName != null && regionId != null){
                District areaByRegionName = districtMapper.findByName(regionName, cityId);
                if (areaByRegionName != null) {
                  log.info("areaRegion", areaByRegionName.getId());
                  districtMapper.updateRegion(areaByRegionName.getId(), regionId);
                }
              }
            } catch (Exception e) {
              log.error("regionName: {}, regionId: {}", region.get("regionName"), region.get("regionId"), e);
              continue;
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
  public void syncCityCinema() {
    // 获取城市
    List<City> cityList = cityMapper.getList(new AddressListDto());
    for(City city : cityList){
      Province province = provinceMapper.selectById(city.getProvinceId());
      if(province == null){
        continue;
      }
      log.info("cityName: {}", city.getName());

      try {
        // 准备请求参数
        Map<String, String> params = new HashMap<>();
        params.put("cityId", String.valueOf(city.getS1CityId()));
        SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
        // 发送请求并获取响应
        String responseBody = supplierService.sendRequest("/cinema/query", params);

//        log.info("responseBody: {}", responseBody);

        // 将 JSON 字符串解析为 JsonNode 对象
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
        Integer code = (Integer) responseMap.getOrDefault("code", 0);
        String message = (String) responseMap.getOrDefault("message", "未知错误");
        if(code != ResultCode.SUCCESS.getCode()) {
          log.info("code: {}, message: {}", code, message);
          continue;
        }

        List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

        for (Map<String, Object> data : dataList) {
          log.info("开始处理数据");
          try {
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

            // 查找影院是否存在
            Cinema oldCinema = cinemaMapper.selectByTpCinemaId(cinemaId);
            if (oldCinema != null) {
              log.info("cinemaId: {}, 已存在", cinemaId);
              continue;
            }

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
                District district = districtMapper.findByName(regionName, city.getId());
                if(district != null){
                  cinema.setDistrictId(district.getId());
                  cinema.setDistrictName(district.getName());
                }
              }
              int res = cinemaMapper.insert(cinema);
              log.info("res: {}", res);
            }
          } catch (Exception e) {
            log.error("影院同步错误原因", e);
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
  public void syncDistrictCinema() {
    List<District> districtList = districtMapper.getTaskList();
    for(District district : districtList){
      City city = cityMapper.selectById(district.getCityId());
      if(city == null){
        continue;
      }
      Province province = provinceMapper.selectById(city.getProvinceId());
      if(province == null){
        continue;
      }
      log.info("districtName: {}", district.getName());

      try {
        // 准备请求参数
        Map<String, String> params = new HashMap<>();
        params.put("cityId", String.valueOf(district.getS1CityId()));
        SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
        // 发送请求并获取响应
        String responseBody = supplierService.sendRequest("/cinema/query", params);

//        log.info("responseBody: {}", responseBody);

        // 将 JSON 字符串解析为 JsonNode 对象
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
        Integer code = (Integer) responseMap.getOrDefault("code", 0);
        String message = (String) responseMap.getOrDefault("message", "未知错误");
        if(code != ResultCode.SUCCESS.getCode()) {
          log.info("code: {}, message: {}", code, message);
          continue;
        }

        List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

        for (Map<String, Object> data : dataList) {
          log.info("开始处理数据");
          try {
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
              cinema.setDistrictId(district.getId());
              cinema.setDistrictName(district.getName());
              int res = cinemaMapper.insert(cinema);
              log.info("res: {}", res);
            }
          } catch (Exception e) {
            log.error("影院同步错误原因", e);
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
      SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/film/query", params);
//      log.info("responseBody: {}", responseBody);

      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();

      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      Integer code = (Integer) responseMap.getOrDefault("code", 0);
      String message = (String) responseMap.getOrDefault("message", "未知错误");
      if(code != ResultCode.SUCCESS.getCode()) {
        log.info("code: {}, message: {}", code, message);
        return;
      }
      List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");

      for (Map<String, Object> data : dataList) {
        try {
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
          Map<String, Object> producer = (Map<String, Object>) data.get("producer");

          log.info("movieId: {}, movieName: {}, duration: {}, publishDate: {}, director: {}, cast: {}, intro: {}, versionType: {}, language: {}, movieType: {}, posterUrl: {}, plotUrl: {}, grade: {}, like: {}, publishStatus: {}, producer: {}",
              movieId, movieName, duration, publishDate, director, cast, intro, versionType, language, movieType, posterUrl, plotUrl, grade, like, publishStatus, producer);
          // 查找影片是否存在
          Movie oldmovie = movieMapper.selectByTpMovieId(movieId);
          if (oldmovie != null) {
            log.info("movieId: {}, 已存在", movieId);
            continue;
          }
          // 影片信息
          Long id = IdUtil.getSnowflakeNextId();
          Movie movie = new Movie();
          movie.setId(id);
          movie.setSupplierId(1869799230973227008L);
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

          // 影片主演、演员
          List<MovieProducer> movieProducers = new ArrayList<>();
          if (producer != null) {
            List<Map<String, Object>> actorsList = (List<Map<String, Object>>) producer.get("actors");
            if (actorsList != null && !actorsList.isEmpty()) {
              for (Map<String, Object> actors : actorsList) {
                String avatar = (String) actors.get("avatar");
                String enName = (String) actors.get("enName");
                String scName = (String) actors.get("scName");
                String actName = (String) actors.get("actName");
                log.info("演员 avatar: {}, enName: {}, scName: {}, actName: {}", avatar, enName, scName, actName);
                MovieProducer movieProducer = new MovieProducer();
                movieProducer.setId(IdUtil.getSnowflakeNextId());
                movieProducer.setMovieId(id);
                movieProducer.setType(2);
                movieProducer.setAvatar(avatar);
                movieProducer.setEnName(enName);
                movieProducer.setScName(scName);
                movieProducer.setActName(actName);
                movieProducer.setCreatedBy(0L);
                movieProducers.add(movieProducer);
              }
            }

            List<Map<String, Object>> directorsList = (List<Map<String, Object>>) producer.get("directors");
            if (directorsList != null && !directorsList.isEmpty()) {
              for (Map<String, Object> directors : directorsList) {
                String avatar = (String) directors.get("avatar");
                String enName = (String) directors.get("enName");
                String scName = (String) directors.get("scName");
                log.info("导演 avatar: {}, enName: {}, scName: {}", avatar, enName, scName);
                MovieProducer movieProducer = new MovieProducer();
                movieProducer.setId(IdUtil.getSnowflakeNextId());
                movieProducer.setMovieId(id);
                movieProducer.setType(1);
                movieProducer.setAvatar(avatar);
                movieProducer.setEnName(enName);
                movieProducer.setScName(scName);
                movieProducer.setActName("");
                movieProducer.setCreatedBy(0L);
                movieProducers.add(movieProducer);
              }
            }
          }

          // 开始保存数据
          boolean res = movieService.initMovie(movie, movieProducers);
          log.info("res: {}", res);

        } catch (Exception e) {
          log.error("影片同步错误原因", e);
        }
      }
      log.info("同步影片结束");
    } catch (Exception e) {
      log.error("同步影片失败", e);
    }
  }

  @Override
  public String getSyncShow(Integer id) {
    String responseBody = "";
    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("cinemaId", String.valueOf(id));

      SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
      // 发送请求并获取响应
      responseBody = supplierService.sendRequest("/show/preferential/query", params);
    } catch (Exception e) {
      log.error("同步异常: {}", e);
    }
    return responseBody;
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncShow() {
    try {

      List<Cinema> cinemaList = cinemaMapper.getList(new CinemaListDto());

      if(cinemaList != null && !cinemaList.isEmpty()){
        for (Cinema cinema : cinemaList) {
          // 准备请求参数
          Map<String, String> params = new HashMap<>();
          params.put("cinemaId", String.valueOf(cinema.getTpCinemaId()));

          SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
          // 发送请求并获取响应
          String responseBody = supplierService.sendRequest("/show/preferential/query", params);

          // 将 JSON 字符串解析为 JsonNode 对象
//          log.info("responseBody: {}", responseBody);

          // 将 JSON 字符串解析为 JsonNode 对象
          ObjectMapper objectMapper = new ObjectMapper();

          Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
          Integer code = (Integer) responseMap.getOrDefault("code", 0);
          String message = (String) responseMap.getOrDefault("message", "未知错误");
          if(code != ResultCode.SUCCESS.getCode()) {
            log.info("code: {}, message: {}", code, message);
            continue;
          }
          Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
          if (data == null) {
            continue;
          }
          List<Map<String, Object>> showInforList = (List<Map<String, Object>>) data.get("showInfor");
          if (showInforList != null && !showInforList.isEmpty()) {
            for (Map<String, Object> showInfor : showInforList) {
              try {
                String showId = (String) showInfor.get("showId");
                String hallName = (String) showInfor.get("hallName");
                Integer duration = (Integer) showInfor.get("duration");
                String showTime = (String) showInfor.get("showTime");
                String stopSellTime = (String) showInfor.get("stopSellTime");
                String showVersionType = (String) showInfor.get("showVersionType");
                BigDecimal showPrice = Convert.toBigDecimal(data.get("showPrice"));
                BigDecimal userPrice = Convert.toBigDecimal(data.get("userPrice"));
                // 需要用这个去找影片信息
                Long tpMovieId = Convert.toLong(showInfor.get("movieId"));
                Movie movie = movieMapper.findByTpMovieId(tpMovieId);
                if (movie == null) {
                  log.info("tpMovieId: {}, 不存在", tpMovieId);
                  continue;
                }
                // 补充电影id和影院id
                log.info("tpCinemaId: {}, tpMovieId: {}, showId: {}, hallName: {}, duration: {}, showTime: {}, stopSellTime: {}, showVersionType: {}, showPrice: {}, userPrice: {}",
                    cinema.getTpCinemaId(), tpMovieId, showId, hallName, duration, showTime, stopSellTime, showVersionType, showPrice, userPrice);
                // 查找影片是否存在
                Show oldShow = showMapper.selectByTpShowId(showId);
                if (oldShow != null) {
                  log.info("showId: {}, 已存在", showId);
                  continue;
                }
                Show show = new Show();
                Long id = IdUtil.getSnowflakeNextId();
                show.setId(id);
                show.setTpShowId(showId);
                show.setSupplierId(1869799230973227008L);
                show.setCinemaId(cinema.getId());
                show.setMovieId(movie.getId());
                show.setHallName(hallName);
                show.setDuration(duration);
                show.setShowTime(LocalDateTime.parse(showTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                show.setStopSellTime(LocalDateTime.parse(stopSellTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                show.setShowVersionType(showVersionType);
                show.setShowPrice(showPrice);
                show.setUserPrice(userPrice);

                // 区间定价
                List<ShowArea> showAreaList = new ArrayList<>();
                List<Map<String, Object>> areaPriceList = (List<Map<String, Object>>) showInfor.get("areaPrice");

                if (areaPriceList != null && !areaPriceList.isEmpty()) {
                  for (Map<String, Object> areaPrice : areaPriceList) {
                    String area = (String) areaPrice.get("area");
                    BigDecimal areaShowPrice = Convert.toBigDecimal(areaPrice.get("showPrice"));
                    BigDecimal areaUserPrice = Convert.toBigDecimal(areaPrice.get("userPrice"));

                    log.info("showId: {}, area: {}, areaShowPrice: {}, areaUserPrice: {}", id, area, areaShowPrice, areaUserPrice);

                    ShowArea showArea = new ShowArea();
                    showArea.setId(IdUtil.getSnowflakeNextId());
                    showArea.setShowId(id);
                    showArea.setArea(area);
                    showArea.setShowPrice(areaShowPrice);
                    showArea.setUserPrice(areaUserPrice);
                    showArea.setCreatedBy(0L);

                    showAreaList.add(showArea);
                  }
                } else {
                  log.info("区域价格不存在");
                  continue;
                }

                boolean res = showService.initShow(show, showAreaList);
                log.info("同步场次成功 res: {}", res);

              } catch (Exception e) {
                log.error("同步场次失败", e);
              }
            }
          }
        }
      }
      log.info("同步场次结束");
    } catch (Exception e) {
      log.error("同步场次失败", e);
    }
  }

  @Override
  public void syncTest() {
//    try {
//
//      Cinema cinema = new Cinema();
//      cinema.setId(1876528643580997632L);
//      cinema.setTpCinemaId(123256L);
//
//      // 发送请求并获取响应
//      String responseBody = "{\"code\":200,\"message\":\"OK\",\"data\":{\"longitude\":117.313898,\"latitude\":38.361086,\"cinemaId\":123256,\"cinemaName\":\"万达影城（金宝方圆荟PRIME店）\",\"cinemaAddress\":\"黄骅市昌骅大街与沧海路交叉口金宝方圆荟三层万达影城\",\"showInfor\":[{\"showId\":\"533077f607906bdc0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 09:45:00\",\"stopSellTime\":\"2025-01-21 08:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"a8180d5531fe9e3a0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 11:50:00\",\"stopSellTime\":\"2025-01-21 10:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"d48824b3838577000175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 13:10:00\",\"stopSellTime\":\"2025-01-21 11:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"c308cc3db4c75b5d0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 13:55:00\",\"stopSellTime\":\"2025-01-21 12:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"a05504aa7162bb330175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 16:00:00\",\"stopSellTime\":\"2025-01-21 14:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"29244aaee6df53c70175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 18:05:00\",\"stopSellTime\":\"2025-01-21 16:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"d59c6cc9c4d0a1590175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 20:10:00\",\"stopSellTime\":\"2025-01-21 18:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"1216f6db5597aaec0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 21:15:00\",\"stopSellTime\":\"2025-01-21 20:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"6d3dfdd38d9f1d4e0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-21 22:15:00\",\"stopSellTime\":\"2025-01-21 21:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"e025de28e3692dc30175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":205058,\"duration\":106,\"showTime\":\"2025-01-21 14:25:00\",\"stopSellTime\":\"2025-01-21 13:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"d6c76c642f3527a40175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-21 09:35:00\",\"stopSellTime\":\"2025-01-21 08:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"acb5f5e2475a34730175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-21 12:05:00\",\"stopSellTime\":\"2025-01-21 10:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"c4cefd2762f56dbe0175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-21 14:35:00\",\"stopSellTime\":\"2025-01-21 13:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"dbb004f4332c92300175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-21 17:00:00\",\"stopSellTime\":\"2025-01-21 15:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"83cec6ae15455c190175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-21 19:30:00\",\"stopSellTime\":\"2025-01-21 18:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"04be9471ede1973b0175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-21 22:00:00\",\"stopSellTime\":\"2025-01-21 20:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"31064e2eb1a485790175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-21 12:30:00\",\"stopSellTime\":\"2025-01-21 11:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":25.80,\"userPrice\":23.2,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":32.8,\"userPrice\":29.5},{\"area\":\"33\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"1\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"36\",\"showPrice\":30.8,\"userPrice\":27.7}]},{\"showId\":\"841e442d3f35d5cc0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-21 15:55:00\",\"stopSellTime\":\"2025-01-21 14:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":26.80,\"userPrice\":24.1,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":33.8,\"userPrice\":30.4},{\"area\":\"33\",\"showPrice\":26.8,\"userPrice\":24.1},{\"area\":\"1\",\"showPrice\":26.8,\"userPrice\":24.1},{\"area\":\"36\",\"showPrice\":31.8,\"userPrice\":28.6}]},{\"showId\":\"2082623fe1e496f50175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-21 21:40:00\",\"stopSellTime\":\"2025-01-21 20:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":25.80,\"userPrice\":23.2,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":32.8,\"userPrice\":29.5},{\"area\":\"33\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"1\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"36\",\"showPrice\":30.8,\"userPrice\":27.7}]},{\"showId\":\"e85a2fcdc723c88c0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":222810,\"duration\":108,\"showTime\":\"2025-01-21 09:30:00\",\"stopSellTime\":\"2025-01-21 08:15:00\",\"showVersionType\":\"日语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":42.9,\"userPrice\":38.6},{\"area\":\"33\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"1\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"36\",\"showPrice\":40.9,\"userPrice\":36.8}]},{\"showId\":\"51a5a9ab59a1f6560175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":138055,\"duration\":107,\"showTime\":\"2025-01-21 10:25:00\",\"stopSellTime\":\"2025-01-21 09:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":42.9,\"userPrice\":38.6},{\"area\":\"33\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"1\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"36\",\"showPrice\":40.9,\"userPrice\":36.8}]},{\"showId\":\"edd667e8467dc4830175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":138055,\"duration\":107,\"showTime\":\"2025-01-21 17:15:00\",\"stopSellTime\":\"2025-01-21 16:00:00\",\"showVersionType\":\"英语 2D\",\"showPrice\":36.90,\"userPrice\":33.2,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":43.9,\"userPrice\":39.5},{\"area\":\"33\",\"showPrice\":36.9,\"userPrice\":33.2},{\"area\":\"1\",\"showPrice\":36.9,\"userPrice\":33.2},{\"area\":\"36\",\"showPrice\":41.9,\"userPrice\":37.7}]},{\"showId\":\"6c1063ed9faa471e0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":210894,\"duration\":90,\"showTime\":\"2025-01-21 20:40:00\",\"stopSellTime\":\"2025-01-21 19:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"1a9121117f863ae50175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-21 11:25:00\",\"stopSellTime\":\"2025-01-21 10:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"493ec62c3c5387130175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-21 13:40:00\",\"stopSellTime\":\"2025-01-21 12:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"8fddee00672b404b0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-21 19:00:00\",\"stopSellTime\":\"2025-01-21 17:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"ce9565966f8936f40175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-21 21:10:00\",\"stopSellTime\":\"2025-01-21 19:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"cfc137cb9665205c0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-21 22:25:00\",\"stopSellTime\":\"2025-01-21 21:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"83272e3880d2acfe0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222673,\"duration\":107,\"showTime\":\"2025-01-21 16:30:00\",\"stopSellTime\":\"2025-01-21 15:15:00\",\"showVersionType\":\"日语 2D\",\"showPrice\":36.90,\"userPrice\":33.2,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":43.9,\"userPrice\":39.5},{\"area\":\"33\",\"showPrice\":36.9,\"userPrice\":33.2},{\"area\":\"1\",\"showPrice\":36.9,\"userPrice\":33.2},{\"area\":\"36\",\"showPrice\":41.9,\"userPrice\":37.7}]},{\"showId\":\"50b6a7b7fcb2bbad0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-21 15:15:00\",\"stopSellTime\":\"2025-01-21 14:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"0a30dba81566a1d00175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222705,\"duration\":111,\"showTime\":\"2025-01-21 18:35:00\",\"stopSellTime\":\"2025-01-21 17:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":36.90,\"userPrice\":33.2,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":43.9,\"userPrice\":39.5},{\"area\":\"33\",\"showPrice\":36.9,\"userPrice\":33.2},{\"area\":\"1\",\"showPrice\":36.9,\"userPrice\":33.2},{\"area\":\"36\",\"showPrice\":41.9,\"userPrice\":37.7}]},{\"showId\":\"0904bb75c7333d970175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":222798,\"duration\":82,\"showTime\":\"2025-01-21 11:30:00\",\"stopSellTime\":\"2025-01-21 10:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"9691e24637d9b2a90175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222798,\"duration\":82,\"showTime\":\"2025-01-21 23:20:00\",\"stopSellTime\":\"2025-01-21 22:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"aecc6efd5e4d48c00175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":213140,\"duration\":85,\"showTime\":\"2025-01-21 23:20:00\",\"stopSellTime\":\"2025-01-21 22:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"f062fc6af3c00e820175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 09:45:00\",\"stopSellTime\":\"2025-01-22 08:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"7f1e30e5af1a85850175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 11:50:00\",\"stopSellTime\":\"2025-01-22 10:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"12aad42a6fa28a3c0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 13:10:00\",\"stopSellTime\":\"2025-01-22 11:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"8ac0c67d626108c80175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 13:55:00\",\"stopSellTime\":\"2025-01-22 12:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"f8c8476fc5f6d1850175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 16:00:00\",\"stopSellTime\":\"2025-01-22 14:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"439129cfb7baae6c0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 18:05:00\",\"stopSellTime\":\"2025-01-22 16:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"37e9839af60211e20175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 20:10:00\",\"stopSellTime\":\"2025-01-22 18:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"26ee5d07f6d75a1a0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 21:15:00\",\"stopSellTime\":\"2025-01-22 20:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"507eb55a57366f460175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 22:15:00\",\"stopSellTime\":\"2025-01-22 21:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"a11b4dfd2d776cd60175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-22 16:45:00\",\"stopSellTime\":\"2025-01-22 15:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"f8c2faa1466a98450175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-22 19:00:00\",\"stopSellTime\":\"2025-01-22 17:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"fcc29086a67b1cc60175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-22 21:10:00\",\"stopSellTime\":\"2025-01-22 19:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"c4a50ee29aa803bf0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-22 22:25:00\",\"stopSellTime\":\"2025-01-22 21:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"f125ab78aba90a710175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-22 09:35:00\",\"stopSellTime\":\"2025-01-22 08:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"fd4f4252d550bd750175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-22 12:05:00\",\"stopSellTime\":\"2025-01-22 10:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"5ccd4038c0f4b0b00175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-22 14:35:00\",\"stopSellTime\":\"2025-01-22 13:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"7e54c69f07b3e6310175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-22 17:00:00\",\"stopSellTime\":\"2025-01-22 15:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"71fd9f01c19616310175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-22 19:30:00\",\"stopSellTime\":\"2025-01-22 18:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":48.9,\"userPrice\":44.0},{\"area\":\"33\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"1\",\"showPrice\":41.9,\"userPrice\":37.7},{\"area\":\"36\",\"showPrice\":46.9,\"userPrice\":42.2}]},{\"showId\":\"e822d2ebdf81930a0175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-22 22:00:00\",\"stopSellTime\":\"2025-01-22 20:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":47.9,\"userPrice\":43.1},{\"area\":\"33\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"1\",\"showPrice\":40.9,\"userPrice\":36.8},{\"area\":\"36\",\"showPrice\":45.9,\"userPrice\":41.3}]},{\"showId\":\"650446388b9ffefd0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":222798,\"duration\":82,\"showTime\":\"2025-01-22 11:30:00\",\"stopSellTime\":\"2025-01-22 10:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"1181a5cb9139d2750175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222798,\"duration\":82,\"showTime\":\"2025-01-22 23:20:00\",\"stopSellTime\":\"2025-01-22 22:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"d5860bde3c392e9f0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-22 13:00:00\",\"stopSellTime\":\"2025-01-22 11:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"4f39f0c7b97adaa90175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-22 15:15:00\",\"stopSellTime\":\"2025-01-22 14:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"f8f331d6acd620d30175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-22 17:50:00\",\"stopSellTime\":\"2025-01-22 16:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"bd778165e8dd36550175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-22 12:30:00\",\"stopSellTime\":\"2025-01-22 11:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":25.80,\"userPrice\":23.2,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":32.8,\"userPrice\":29.5},{\"area\":\"33\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"1\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"36\",\"showPrice\":30.8,\"userPrice\":27.7}]},{\"showId\":\"5696c9aac44ea5da0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-22 21:40:00\",\"stopSellTime\":\"2025-01-22 20:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":25.80,\"userPrice\":23.2,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":32.8,\"userPrice\":29.5},{\"area\":\"33\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"1\",\"showPrice\":25.8,\"userPrice\":23.2},{\"area\":\"36\",\"showPrice\":30.8,\"userPrice\":27.7}]},{\"showId\":\"5d30de7f86a8ab8f0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222705,\"duration\":111,\"showTime\":\"2025-01-22 10:50:00\",\"stopSellTime\":\"2025-01-22 09:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":42.9,\"userPrice\":38.6},{\"area\":\"33\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"1\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"36\",\"showPrice\":40.9,\"userPrice\":36.8}]},{\"showId\":\"fa41393d7d0308400175\",\"hallName\":\"口味王-2号厅\",\"movieId\":210894,\"duration\":90,\"showTime\":\"2025-01-22 14:55:00\",\"stopSellTime\":\"2025-01-22 13:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"d0b2f8320773f25c0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":210894,\"duration\":90,\"showTime\":\"2025-01-22 20:40:00\",\"stopSellTime\":\"2025-01-22 19:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":38.9,\"userPrice\":35.0},{\"area\":\"33\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"1\",\"showPrice\":31.9,\"userPrice\":28.7},{\"area\":\"36\",\"showPrice\":36.9,\"userPrice\":33.2}]},{\"showId\":\"308a5274681823940175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":222810,\"duration\":108,\"showTime\":\"2025-01-22 09:30:00\",\"stopSellTime\":\"2025-01-22 08:15:00\",\"showVersionType\":\"日语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":42.9,\"userPrice\":38.6},{\"area\":\"33\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"1\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"36\",\"showPrice\":40.9,\"userPrice\":36.8}]},{\"showId\":\"dc44322f8f640f9b0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":213140,\"duration\":85,\"showTime\":\"2025-01-22 23:20:00\",\"stopSellTime\":\"2025-01-22 22:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":37.9,\"userPrice\":34.1},{\"area\":\"33\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"1\",\"showPrice\":30.9,\"userPrice\":27.8},{\"area\":\"36\",\"showPrice\":35.9,\"userPrice\":32.3}]},{\"showId\":\"73824caabb33e1b80175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":138055,\"duration\":107,\"showTime\":\"2025-01-22 10:25:00\",\"stopSellTime\":\"2025-01-22 09:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":42.9,\"userPrice\":38.6},{\"area\":\"33\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"1\",\"showPrice\":35.9,\"userPrice\":32.3},{\"area\":\"36\",\"showPrice\":40.9,\"userPrice\":36.8}]},{\"showId\":\"ef00e64c35159fd80175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-29 09:10:00\",\"stopSellTime\":\"2025-01-29 07:55:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":44.90,\"userPrice\":40.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"33\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"1\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"36\",\"showPrice\":54.9,\"userPrice\":49.4}]},{\"showId\":\"8dda22c2a42ca6760175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-29 11:50:00\",\"stopSellTime\":\"2025-01-29 10:35:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"131619493fd0722b0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-29 14:30:00\",\"stopSellTime\":\"2025-01-29 13:15:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"c463d8ea1e7dcfcd0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":190497,\"duration\":146,\"showTime\":\"2025-01-29 09:20:00\",\"stopSellTime\":\"2025-01-29 08:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":50.00,\"userPrice\":45.0,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":50.0,\"userPrice\":45.0},{\"area\":\"33\",\"showPrice\":50.0,\"userPrice\":45.0},{\"area\":\"1\",\"showPrice\":50.0,\"userPrice\":45.0},{\"area\":\"36\",\"showPrice\":54.0,\"userPrice\":48.6}]},{\"showId\":\"6ff4bd273a63bece0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":190497,\"duration\":146,\"showTime\":\"2025-01-29 12:00:00\",\"stopSellTime\":\"2025-01-29 10:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"5fa8298d56e478340175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":223002,\"duration\":146,\"showTime\":\"2025-01-29 10:00:00\",\"stopSellTime\":\"2025-01-29 08:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"e17a594a5a2ad15c0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":223002,\"duration\":146,\"showTime\":\"2025-01-29 12:45:00\",\"stopSellTime\":\"2025-01-29 11:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"c7d95e8c2ba15fff0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-29 09:25:00\",\"stopSellTime\":\"2025-01-29 08:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":55.00,\"userPrice\":49.5,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":65.0,\"userPrice\":58.5},{\"area\":\"33\",\"showPrice\":55.0,\"userPrice\":49.5},{\"area\":\"1\",\"showPrice\":60.0,\"userPrice\":54.0},{\"area\":\"36\",\"showPrice\":65.0,\"userPrice\":58.5}]},{\"showId\":\"ef45aa88ddeb38ad0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-29 14:10:00\",\"stopSellTime\":\"2025-01-29 12:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"01f9a85c29e105ea0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-29 16:45:00\",\"stopSellTime\":\"2025-01-29 15:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"1eb248fc93a2db7a0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-29 19:20:00\",\"stopSellTime\":\"2025-01-29 18:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"9eae6b69f528457f0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":190550,\"duration\":144,\"showTime\":\"2025-01-29 10:35:00\",\"stopSellTime\":\"2025-01-29 09:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"451cd8aae17f70fb0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":190550,\"duration\":144,\"showTime\":\"2025-01-29 13:20:00\",\"stopSellTime\":\"2025-01-29 12:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":59.90,\"userPrice\":53.9,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":69.9,\"userPrice\":62.9},{\"area\":\"33\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"1\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"36\",\"showPrice\":69.9,\"userPrice\":62.9}]},{\"showId\":\"1981a531e5e0c3f40175\",\"hallName\":\"口味王-2号厅\",\"movieId\":213234,\"duration\":108,\"showTime\":\"2025-01-29 12:05:00\",\"stopSellTime\":\"2025-01-29 10:50:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":59.90,\"userPrice\":53.9,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":69.9,\"userPrice\":62.9},{\"area\":\"33\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"1\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"36\",\"showPrice\":69.9,\"userPrice\":62.9}]},{\"showId\":\"dcb630ae1a3098d20175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":190497,\"duration\":146,\"showTime\":\"2025-01-30 10:05:00\",\"stopSellTime\":\"2025-01-30 08:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.00,\"userPrice\":48.6,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":93.8,\"userPrice\":84.4},{\"area\":\"33\",\"showPrice\":54.0,\"userPrice\":48.6},{\"area\":\"1\",\"showPrice\":59.0,\"userPrice\":53.1},{\"area\":\"36\",\"showPrice\":93.8,\"userPrice\":84.4}]},{\"showId\":\"b9b66800d8db985c0175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":213234,\"duration\":108,\"showTime\":\"2025-01-29 09:05:00\",\"stopSellTime\":\"2025-01-29 07:50:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":49.90,\"userPrice\":44.9,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"1\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":59.9,\"userPrice\":53.9}]},{\"showId\":\"588de7b2daba70210175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-29 17:10:00\",\"stopSellTime\":\"2025-01-29 15:55:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"1e986d1a2e1752260175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-29 19:50:00\",\"stopSellTime\":\"2025-01-29 18:35:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"02b07ad10baa74a40175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":190497,\"duration\":146,\"showTime\":\"2025-01-29 22:50:00\",\"stopSellTime\":\"2025-01-29 21:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":49.90,\"userPrice\":44.9,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"1\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":59.9,\"userPrice\":53.9}]},{\"showId\":\"39a4e2595a8891420175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":190497,\"duration\":146,\"showTime\":\"2025-01-30 01:35:00\",\"stopSellTime\":\"2025-01-30 00:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":44.90,\"userPrice\":40.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"33\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"1\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"36\",\"showPrice\":49.9,\"userPrice\":44.9}]},{\"showId\":\"68cf12998abdbe7e0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-29 21:55:00\",\"stopSellTime\":\"2025-01-29 20:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":null},{\"showId\":\"17063d3e8b8eab830175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-30 00:30:00\",\"stopSellTime\":\"2025-01-29 23:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":44.90,\"userPrice\":40.4,\"areaPrice\":null},{\"showId\":\"0d64aa7dc25ced4a0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":190550,\"duration\":144,\"showTime\":\"2025-01-29 16:05:00\",\"stopSellTime\":\"2025-01-29 14:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"2fbd255dcf419ee20175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":190550,\"duration\":144,\"showTime\":\"2025-01-29 18:45:00\",\"stopSellTime\":\"2025-01-29 17:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"593457f45d50cee00175\",\"hallName\":\"口味王-2号厅\",\"movieId\":213234,\"duration\":108,\"showTime\":\"2025-01-30 11:00:00\",\"stopSellTime\":\"2025-01-30 09:45:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"0dfd3b949a1d2b230175\",\"hallName\":\"口味王-2号厅\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-30 08:20:00\",\"stopSellTime\":\"2025-01-30 07:05:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":44.90,\"userPrice\":40.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"33\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"1\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"36\",\"showPrice\":49.9,\"userPrice\":44.9}]},{\"showId\":\"c3e62fabbf9d48300175\",\"hallName\":\"口味王-2号厅\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-30 13:05:00\",\"stopSellTime\":\"2025-01-30 11:50:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"f5c1c5e0fbd6f1fb0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":190497,\"duration\":146,\"showTime\":\"2025-01-30 12:15:00\",\"stopSellTime\":\"2025-01-30 11:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"0564f55ffa4741c60175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-30 09:55:00\",\"stopSellTime\":\"2025-01-30 08:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":49.90,\"userPrice\":44.9,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"1\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":59.9,\"userPrice\":53.9}]},{\"showId\":\"52c69d8e1e1c768e0175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-30 12:30:00\",\"stopSellTime\":\"2025-01-30 11:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"f0ebfe67bac4052e0175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-30 15:05:00\",\"stopSellTime\":\"2025-01-30 13:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"24307c5e61015ef70175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":223002,\"duration\":146,\"showTime\":\"2025-01-30 08:50:00\",\"stopSellTime\":\"2025-01-30 07:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":44.90,\"userPrice\":40.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"33\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"1\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"36\",\"showPrice\":49.9,\"userPrice\":44.9}]},{\"showId\":\"ef0c9a844ec6f19e0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":190550,\"duration\":144,\"showTime\":\"2025-01-30 09:30:00\",\"stopSellTime\":\"2025-01-30 08:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":49.90,\"userPrice\":44.9,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"1\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":59.9,\"userPrice\":53.9}]},{\"showId\":\"87d4bbdaa17017480175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":190550,\"duration\":144,\"showTime\":\"2025-01-30 15:00:00\",\"stopSellTime\":\"2025-01-30 13:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"a6556cace84469910175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":213234,\"duration\":108,\"showTime\":\"2025-01-31 16:50:00\",\"stopSellTime\":\"2025-01-31 15:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"88bee7e4177e87350175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":190890,\"duration\":144,\"showTime\":\"2025-01-31 11:35:00\",\"stopSellTime\":\"2025-01-31 10:20:00\",\"showVersionType\":\"国语 3D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"098d369870ae1c350175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":190497,\"duration\":146,\"showTime\":\"2025-01-31 11:55:00\",\"stopSellTime\":\"2025-01-31 10:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"68adf0d15f948a080175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-31 14:15:00\",\"stopSellTime\":\"2025-01-31 13:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"7d57eff91abb4faf0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222762,\"duration\":136,\"showTime\":\"2025-01-31 19:00:00\",\"stopSellTime\":\"2025-01-31 17:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":54.90,\"userPrice\":49.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":64.9,\"userPrice\":58.4},{\"area\":\"1\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"33\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"36\",\"showPrice\":64.9,\"userPrice\":58.4}]},{\"showId\":\"91ec479ecf1e3f790175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":223002,\"duration\":146,\"showTime\":\"2025-01-31 09:10:00\",\"stopSellTime\":\"2025-01-31 07:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":49.90,\"userPrice\":44.9,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":59.9,\"userPrice\":53.9},{\"area\":\"1\",\"showPrice\":54.9,\"userPrice\":49.4},{\"area\":\"33\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"36\",\"showPrice\":59.9,\"userPrice\":53.9}]},{\"showId\":\"e3fbb36006a313a40175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":190550,\"duration\":144,\"showTime\":\"2025-01-31 08:55:00\",\"stopSellTime\":\"2025-01-31 07:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":44.90,\"userPrice\":40.4,\"areaPrice\":[{\"area\":\"32\",\"showPrice\":49.9,\"userPrice\":44.9},{\"area\":\"1\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"33\",\"showPrice\":44.9,\"userPrice\":40.4},{\"area\":\"36\",\"showPrice\":49.9,\"userPrice\":44.9}]},{\"showId\":\"e2ceded1597817d40175\",\"hallName\":\"口味王-2号厅\",\"movieId\":210894,\"duration\":90,\"showTime\":\"2025-01-23 14:55:00\",\"stopSellTime\":\"2025-01-23 13:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"49fa53aae52abbfe0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":210894,\"duration\":90,\"showTime\":\"2025-01-23 20:35:00\",\"stopSellTime\":\"2025-01-23 19:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"26dfee9890bb42810175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-21 14:40:00\",\"stopSellTime\":\"2025-01-21 13:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"a48d3cff848bd5720175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-21 16:50:00\",\"stopSellTime\":\"2025-01-21 15:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"c7dc7602fb642dc00175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-22 11:15:00\",\"stopSellTime\":\"2025-01-22 10:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"3becb319fed4bb510175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-22 13:30:00\",\"stopSellTime\":\"2025-01-22 12:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"014279c380be9f7f0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-22 14:25:00\",\"stopSellTime\":\"2025-01-22 13:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"60f344973857863d0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-23 11:20:00\",\"stopSellTime\":\"2025-01-23 10:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"5a4df367db5805450175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-23 13:30:00\",\"stopSellTime\":\"2025-01-23 12:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"7f36d17ac7a32ef30175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-23 14:55:00\",\"stopSellTime\":\"2025-01-23 13:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"9db2ea5224058f2b0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-23 16:45:00\",\"stopSellTime\":\"2025-01-23 15:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"39904c2d8424e7f00175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-23 19:00:00\",\"stopSellTime\":\"2025-01-23 17:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"5ca30dd608ae8df60175\",\"hallName\":\"口味王-2号厅\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-23 21:10:00\",\"stopSellTime\":\"2025-01-23 19:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"a8b981e1907df7b30175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":205054,\"duration\":114,\"showTime\":\"2025-01-23 22:20:00\",\"stopSellTime\":\"2025-01-23 21:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"3dbc1f493d49a1c10175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-22 17:10:00\",\"stopSellTime\":\"2025-01-22 15:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"ec5ca5ed535f891f0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 09:45:00\",\"stopSellTime\":\"2025-01-23 08:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"a830b42078bfbc0a0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 11:50:00\",\"stopSellTime\":\"2025-01-23 10:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"2fee0bf05b60d9090175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 12:50:00\",\"stopSellTime\":\"2025-01-23 11:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"9ef39c377bf124b20175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 13:55:00\",\"stopSellTime\":\"2025-01-23 12:40:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"5994fca7d012f01c0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 16:00:00\",\"stopSellTime\":\"2025-01-23 14:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"89a46c372c8e2e600175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 17:05:00\",\"stopSellTime\":\"2025-01-23 15:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"84fde8bc91bdaeae0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 18:05:00\",\"stopSellTime\":\"2025-01-23 16:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"375266d2f6cf1ab20175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 20:10:00\",\"stopSellTime\":\"2025-01-23 18:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"acf939bbc3bfd0800175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 21:05:00\",\"stopSellTime\":\"2025-01-23 19:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"4beb99014303fccd0175\",\"hallName\":\"7号厅PRIME厅（儿童需购票）\",\"movieId\":211627,\"duration\":109,\"showTime\":\"2025-01-23 22:15:00\",\"stopSellTime\":\"2025-01-23 21:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"00e45253bb5e01b80175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-21 19:20:00\",\"stopSellTime\":\"2025-01-21 18:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":26.80,\"userPrice\":24.1,\"areaPrice\":null},{\"showId\":\"6eb5ee41d4a3b7e60175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-22 16:40:00\",\"stopSellTime\":\"2025-01-22 15:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":26.80,\"userPrice\":24.1,\"areaPrice\":null},{\"showId\":\"5ce28fda04d996fc0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-22 19:20:00\",\"stopSellTime\":\"2025-01-22 18:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":26.80,\"userPrice\":24.1,\"areaPrice\":null},{\"showId\":\"f9a89fa9bb6585220175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-23 14:30:00\",\"stopSellTime\":\"2025-01-23 13:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":26.80,\"userPrice\":24.1,\"areaPrice\":null},{\"showId\":\"25660ae05baeb6b40175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-23 17:35:00\",\"stopSellTime\":\"2025-01-23 16:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":26.80,\"userPrice\":24.1,\"areaPrice\":null},{\"showId\":\"fcae12f3f15e76520175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-23 19:25:00\",\"stopSellTime\":\"2025-01-23 18:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":26.80,\"userPrice\":24.1,\"areaPrice\":null},{\"showId\":\"8e18a16e254493360175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":222708,\"duration\":96,\"showTime\":\"2025-01-23 23:10:00\",\"stopSellTime\":\"2025-01-23 21:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":25.80,\"userPrice\":23.2,\"areaPrice\":null},{\"showId\":\"3f6f53ed507366650175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-23 09:35:00\",\"stopSellTime\":\"2025-01-23 08:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"a937f2967c4bcca70175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-23 12:05:00\",\"stopSellTime\":\"2025-01-23 10:50:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"b5232427748c9e480175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-23 14:35:00\",\"stopSellTime\":\"2025-01-23 13:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"cdfc842f2452d0920175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-23 17:00:00\",\"stopSellTime\":\"2025-01-23 15:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"d008b45b8c0dbef50175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-23 19:30:00\",\"stopSellTime\":\"2025-01-23 18:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"fdeb60393b7d8a2b0175\",\"hallName\":\"1号厅（哈根达斯买一赠一）\",\"movieId\":190501,\"duration\":131,\"showTime\":\"2025-01-23 22:00:00\",\"stopSellTime\":\"2025-01-23 20:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":40.90,\"userPrice\":36.8,\"areaPrice\":null},{\"showId\":\"29d579b194ebb02a0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":213140,\"duration\":85,\"showTime\":\"2025-01-23 23:10:00\",\"stopSellTime\":\"2025-01-23 21:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"1872c47df3581bff0175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":222798,\"duration\":82,\"showTime\":\"2025-01-23 11:10:00\",\"stopSellTime\":\"2025-01-23 09:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"9087b224a048455b0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222798,\"duration\":82,\"showTime\":\"2025-01-23 23:20:00\",\"stopSellTime\":\"2025-01-23 22:05:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"325330842e67980b0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":138055,\"duration\":107,\"showTime\":\"2025-01-23 10:25:00\",\"stopSellTime\":\"2025-01-23 09:10:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":null},{\"showId\":\"d694eaa59c65fe6f0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222705,\"duration\":111,\"showTime\":\"2025-01-21 10:35:00\",\"stopSellTime\":\"2025-01-21 09:20:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":null},{\"showId\":\"191033246f61e37c0175\",\"hallName\":\"口味王-2号厅\",\"movieId\":222705,\"duration\":111,\"showTime\":\"2025-01-23 10:50:00\",\"stopSellTime\":\"2025-01-23 09:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":null},{\"showId\":\"786b410f1a26d9510175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222705,\"duration\":111,\"showTime\":\"2025-01-23 18:30:00\",\"stopSellTime\":\"2025-01-23 17:15:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":36.90,\"userPrice\":33.2,\"areaPrice\":null},{\"showId\":\"af4f986fdc6a35a70175\",\"hallName\":\"口味王-2号厅\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-21 12:45:00\",\"stopSellTime\":\"2025-01-21 11:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"4a03371ce4077f850175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-21 17:50:00\",\"stopSellTime\":\"2025-01-21 16:35:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"ffa60daf6e2ddcec0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-21 19:45:00\",\"stopSellTime\":\"2025-01-21 18:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"c71212e915cac3db0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-22 19:45:00\",\"stopSellTime\":\"2025-01-22 18:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"c6286b6b232911710175\",\"hallName\":\"口味王-2号厅\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-23 13:00:00\",\"stopSellTime\":\"2025-01-23 11:45:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"097c62301ec9e74d0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-23 15:40:00\",\"stopSellTime\":\"2025-01-23 14:25:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"b52aaafd989e00680175\",\"hallName\":\"6号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-23 19:10:00\",\"stopSellTime\":\"2025-01-23 17:55:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":31.90,\"userPrice\":28.7,\"areaPrice\":null},{\"showId\":\"ffd98f817e9bcc240175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":212936,\"duration\":98,\"showTime\":\"2025-01-23 21:15:00\",\"stopSellTime\":\"2025-01-23 20:00:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":30.90,\"userPrice\":27.8,\"areaPrice\":null},{\"showId\":\"a809523703f699ea0175\",\"hallName\":\"3号厅（哈根达斯买一赠一）\",\"movieId\":205058,\"duration\":106,\"showTime\":\"2025-01-22 15:45:00\",\"stopSellTime\":\"2025-01-22 14:30:00\",\"showVersionType\":\"国语 2D\",\"showPrice\":41.90,\"userPrice\":37.7,\"areaPrice\":null},{\"showId\":\"4132180d7f5082320175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222673,\"duration\":107,\"showTime\":\"2025-01-22 18:35:00\",\"stopSellTime\":\"2025-01-22 17:20:00\",\"showVersionType\":\"日语 2D\",\"showPrice\":36.90,\"userPrice\":33.2,\"areaPrice\":null},{\"showId\":\"288c358fe3595b260175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":222673,\"duration\":107,\"showTime\":\"2025-01-23 16:25:00\",\"stopSellTime\":\"2025-01-23 15:10:00\",\"showVersionType\":\"日语 2D\",\"showPrice\":36.90,\"userPrice\":33.2,\"areaPrice\":null},{\"showId\":\"4d0cb75a824b2ebe0175\",\"hallName\":\"5号厅（哈根达斯买一赠一）\",\"movieId\":211639,\"duration\":99,\"showTime\":\"2025-01-23 12:30:00\",\"stopSellTime\":\"2025-01-23 11:15:00\",\"showVersionType\":\"日语 2D\",\"showPrice\":35.90,\"userPrice\":32.3,\"areaPrice\":null}]}}";
//
//      // 将 JSON 字符串解析为 JsonNode 对象
//      ObjectMapper objectMapper = new ObjectMapper();
//
//      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
//      Integer code = (Integer) responseMap.getOrDefault("code", 0);
//      String message = (String) responseMap.getOrDefault("message", "未知错误");
//      if(code != ResultCode.SUCCESS.getCode()) {
//        log.info("code: {}, message: {}", code, message);
//        return;
//      }
//      Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
//      if (data == null) {
//        return;
//      }
//      List<Map<String, Object>> showInforList = (List<Map<String, Object>>) data.get("showInfor");
//      if (showInforList != null && !showInforList.isEmpty()) {
//        for (Map<String, Object> showInfor : showInforList) {
//          try {
//            String showId = (String) showInfor.get("showId");
//            String hallName = (String) showInfor.get("hallName");
//            Integer duration = (Integer) showInfor.get("duration");
//            String showTime = (String) showInfor.get("showTime");
//            String stopSellTime = (String) showInfor.get("stopSellTime");
//            String showVersionType = (String) showInfor.get("showVersionType");
//            BigDecimal showPrice = Convert.toBigDecimal(data.get("showPrice"));
//            BigDecimal userPrice = Convert.toBigDecimal(data.get("userPrice"));
//            // 需要用这个去找影片信息
//            Long tpMovieId = Convert.toLong(showInfor.get("movieId"));
//            Movie movie = movieMapper.findByTpMovieId(tpMovieId);
//            if (movie == null) {
//              log.info("tpMovieId: {}, 不存在", tpMovieId);
//              continue;
//            }
//            // 补充电影id和影院id
//            log.info("tpCinemaId: {}, tpMovieId: {}, showId: {}, hallName: {}, duration: {}, showTime: {}, stopSellTime: {}, showVersionType: {}, showPrice: {}, userPrice: {}",
//                cinema.getTpCinemaId(), tpMovieId, showId, hallName, duration, showTime, stopSellTime, showVersionType, showPrice, userPrice);
//            // 查找影片是否存在
//            Show oldShow = showMapper.selectByTpShowId(showId);
//            if (oldShow != null) {
//              log.info("showId: {}, 已存在", showId);
//              continue;
//            }
//            Show show = new Show();
//            Long id = IdUtil.getSnowflakeNextId();
//            show.setId(id);
//            show.setTpShowId(showId);
//            show.setSupplierId(1869799230973227008L);
//            show.setCinemaId(cinema.getId());
//            show.setMovieId(movie.getId());
//            show.setHallName(hallName);
//            show.setDuration(duration);
//            show.setShowTime(LocalDateTime.parse(showTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            show.setStopSellTime(LocalDateTime.parse(stopSellTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            show.setShowVersionType(showVersionType);
//            show.setShowPrice(showPrice);
//            show.setUserPrice(userPrice);
//
//            // 区间定价
//            List<ShowArea> showAreaList = new ArrayList<>();
//            List<Map<String, Object>> areaPriceList = (List<Map<String, Object>>) showInfor.get("areaPrice");
//
//            if (areaPriceList != null && !areaPriceList.isEmpty()) {
//              for (Map<String, Object> areaPrice : areaPriceList) {
//                String area = (String) areaPrice.get("area");
//                BigDecimal areaShowPrice = Convert.toBigDecimal(areaPrice.get("showPrice"));
//                BigDecimal areaUserPrice = Convert.toBigDecimal(areaPrice.get("userPrice"));
//
//                log.info("showId: {}, area: {}, areaShowPrice: {}, areaUserPrice: {}", id, area, areaShowPrice, areaUserPrice);
//
//                ShowArea showArea = new ShowArea();
//                showArea.setId(IdUtil.getSnowflakeNextId());
//                showArea.setShowId(id);
//                showArea.setArea(area);
//                showArea.setShowPrice(areaShowPrice);
//                showArea.setUserPrice(areaUserPrice);
//                showArea.setCreatedBy(0L);
//
//                showAreaList.add(showArea);
//              }
//            } else {
//              log.info("区域价格不存在");
//              continue;
//            }
//
//            boolean res = showService.initShow(show, showAreaList);
//            log.info("同步场次成功 res: {}", res);
//
//          } catch (Exception e) {
//            log.error("同步场次失败", e);
//          }
//        }
//      }
//
//      log.info("同步场次结束");
//    } catch (Exception e) {
//      log.error("同步场次失败", e);
//    }
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncPendingPaymentOrder() {
    try {
      orderMapper.cancelPendingPayments();
      log.info("同步待支付订单结束");
    } catch (Exception e) {
      log.error("同步待支付订单失败", e);
    }
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void syncPendingTicketOrder() {
    try {

      List<Order> orderList = orderMapper.getPendingTicketOrders();

      if(orderList != null && !orderList.isEmpty()){
        for (Order orderItem : orderList) {
          // 准备请求参数
          Map<String, String> params = new HashMap<>();
          params.put("tradeNo", orderItem.getOrderSn());
          SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
          // 发送请求并获取响应
          String responseBody = supplierService.sendRequest("/order/query", params);

          // 将 JSON 字符串解析为 JsonNode 对象
//          log.info("responseBody: {}", responseBody);

          // 将 JSON 字符串解析为 JsonNode 对象
          ObjectMapper objectMapper = new ObjectMapper();

          Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
          Integer code = (Integer) responseMap.getOrDefault("code", 0);
          String message = (String) responseMap.getOrDefault("message", "未知错误");
          if(code != ResultCode.SUCCESS.getCode()) {
            log.info("code: {}, message: {}", code, message);
            continue;
          }
          Object data = responseMap.get("data");
          if(data == null){
            continue;
          }
          HttpOrder httpOrder = objectMapper.convertValue(data, HttpOrder.class);
          if(httpOrder == null || !ORDER_STATUS_GENERATE_SUCCESS.equals(httpOrder.getOrderStatus())){
            continue;
          }
          boolean res = orderService.supplierOrder(httpOrder);
          if(res) {
            log.info("同步出票中订单成功", httpOrder);
          } else {
            log.info("同步出票中订单失败", httpOrder);
          }
        }
      }
      log.info("同步出票中订单结束");
    } catch (Exception e) {
      log.error("同步出票中订单失败", e);
    }
  }



}
