package com.vscing.api.service.impl;

import com.vscing.api.service.CityService;
import com.vscing.model.entity.City;
import com.vscing.model.entity.District;
import com.vscing.model.mapper.CityMapper;
import com.vscing.model.mapper.DistrictMapper;
import com.vscing.model.vo.AppletCityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Autowired
  private CityMapper cityMapper;

  @Autowired
  private DistrictMapper districtMapper;

  @Override
  public List<AppletCityVo> getAllList() {
    // 城市列表
    List<City> cityList = cityMapper.getAllList();
    // 区县级城市列表
    List<District> districtList = districtMapper.getAllList();
    // 将两个列表合并为一个 AppletCityVo 列表
    List<AppletCityVo> appletCityVoList = Stream.concat(
            cityList.stream(),
            districtList.stream()
        )
        .map(item -> {
          AppletCityVo vo = new AppletCityVo();
          if (item instanceof City) {
            City city = (City) item;
            vo.setCityId(city.getId());
            vo.setCityName(city.getName());
            vo.setFirstLetter(city.getFirstLetter());
          } else if (item instanceof District) {
            District district = (District) item;
            vo.setDistrictId(district.getId());
            vo.setCityId(district.getCityId());
            vo.setCityName(district.getName());
            vo.setFirstLetter(district.getFirstLetter());
          }
          return vo;
        })
        .collect(Collectors.toList());

    return appletCityVoList;
  }

}
