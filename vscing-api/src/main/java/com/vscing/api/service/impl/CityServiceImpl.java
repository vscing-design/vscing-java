package com.vscing.api.service.impl;

import com.vscing.api.service.CityService;
import com.vscing.common.service.RedisService;
import com.vscing.common.utils.GeoUtils;
import com.vscing.model.dto.CurrentCityApiDto;
import com.vscing.model.entity.City;
import com.vscing.model.entity.District;
import com.vscing.model.mapper.CityMapper;
import com.vscing.model.mapper.DistrictMapper;
import com.vscing.model.vo.AppletCityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CityServiceImpl
 *
 * @author vscing
 * @date 2025/1/11 18:52
 */
@Slf4j
@Service
public class CityServiceImpl implements CityService {

  private static final String REDIS_KEY = "applet_cityVo_list";

  @Autowired
  private RedisService redisService;

  @Autowired
  private CityMapper cityMapper;

  @Autowired
  private DistrictMapper districtMapper;

  @Override
  public List<AppletCityVo> getAllList() {
    List<AppletCityVo> appletCityVoList;
    if (redisService.hasKey(REDIS_KEY)) {
      appletCityVoList = (List<AppletCityVo>) redisService.get(REDIS_KEY);
    } else {
      // 城市列表
      List<City> cityList = cityMapper.getAllList();
      // 区县级城市列表
      List<District> districtList = districtMapper.getAllList();
      // 将两个列表合并为一个 AppletCityVo 列表
      appletCityVoList = Stream.concat(
              cityList.stream(),
              districtList.stream()
          )
          .map(item -> {
            AppletCityVo vo = new AppletCityVo();
            if (item instanceof City) {
              City city = (City) item;
              vo.setCityId(city.getId());
              vo.setCityName(city.getName());
              vo.setFirstLetter(city.getFullLetter());
              vo.setLat(city.getLat());
              vo.setLng(city.getLng());
              vo.setHot(city.getHot());
            } else if (item instanceof District) {
              District district = (District) item;
              vo.setDistrictId(district.getId());
              vo.setCityId(district.getCityId());
              vo.setCityName(district.getName());
              vo.setFirstLetter(district.getFullLetter());
              vo.setLat(district.getLat());
              vo.setLng(district.getLng());
            }
            return vo;
          })
          .collect(Collectors.toList());

      // 将 AppletCityVo 列表存储到 Redis 中
      redisService.set(REDIS_KEY, appletCityVoList);
    }

    return appletCityVoList;
  }

  @Override
  public AppletCityVo currentCity(CurrentCityApiDto requestData) {
    List<AppletCityVo> appletCityVoList = this.getAllList();

    // 遍历获取最近城市
    AppletCityVo currentCity = appletCityVoList.stream()
        .min(Comparator.comparingDouble(city ->
            GeoUtils.calculateDistance(requestData.getLat(), requestData.getLng(), city.getLat(), city.getLng())))
        .orElse(appletCityVoList.get(0));

    return currentCity;
  }

}
