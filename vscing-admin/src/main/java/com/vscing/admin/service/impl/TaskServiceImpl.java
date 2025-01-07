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
  private DistrictMapper areaMapper;

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
            District area = areaMapper.findByCode(cityCode);
            if (area != null) {
              log.info("areaCity");
              // 如果找到，则更新 area 表中的 cityId
              areaMapper.updateCity(area.getId(), cityId);
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
                District areaByRegionName = areaMapper.findByName(regionName, cityId);
                if (areaByRegionName != null) {
                  log.info("areaRegion", areaByRegionName.getId());
                  areaMapper.updateRegion(areaByRegionName.getId(), regionId);
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
  public void syncCinema() {
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

        log.info("responseBody: {}", responseBody);

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
      log.info("responseBody: {}", responseBody);

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
          // 获取当天的 LocalDate 对象
          LocalDateTime endOfDayPrecise = LocalDate.now().atTime(23, 59, 59);
          // 格式化为字符串
          String startTimeString = endOfDayPrecise.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  //      params.put("time", startTimeString);

          SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
          // 发送请求并获取响应
          String responseBody = supplierService.sendRequest("/show/preferential/query", params);

          // 将 JSON 字符串解析为 JsonNode 对象
          log.info("responseBody: {}", responseBody);

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

          List<Map<String, Object>> showInforList = (List<Map<String, Object>>) data.get("showInfor");
          log.info("showInforList: {}", showInforList);
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
                  continue;
                }
                // 补充电影id和影院id
                log.info("tpMovieId: {}, showId: {}, hallName: {}, duration: {}, showTime: {}, stopSellTime: {}, showVersionType: {}, showPrice: {}, userPrice: {}",
                    tpMovieId, showId, hallName, duration, showTime, stopSellTime, showVersionType, showPrice, userPrice);
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
                }

                boolean res = showService.initShow(show, showAreaList);
                log.info("res: {}", res);

              } catch (Exception e) {
                log.error("同步场次失败", e);
                continue;
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

//  @Async("threadPoolTaskExecutor")
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
          log.info("responseBody: {}", responseBody);

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
