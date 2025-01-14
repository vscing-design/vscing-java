package com.vscing.admin.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vscing.admin.service.AddressService;
import com.vscing.common.service.RedisService;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.AddressListDto;
import com.vscing.model.mapper.CityMapper;
import com.vscing.model.mapper.DistrictMapper;
import com.vscing.model.mapper.ProvinceMapper;
import com.vscing.model.vo.CityVo;
import com.vscing.model.vo.DistrictVo;
import com.vscing.model.vo.ProvinceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * AddressServiceImpl
 *
 * @author vscing
 * @date 2024/12/23 00:34
 */
@Service
public class AddressServiceImpl implements AddressService {

  private static final String REDIS_KEY = "addressList";

  @Autowired
  private ProvinceMapper provinceMapper;

  @Autowired
  private CityMapper cityMapper;

  @Autowired
  private DistrictMapper districtMapper;

  @Autowired
  private RedisService redisService;

  @Override
  public List<ProvinceVo> getList() {

    String redisKey = "addressList";

    List<ProvinceVo> provinceList;

    if(redisService.hasKey(redisKey)) {
      // 使用自定义工具类
      String redisValue = (String) redisService.get(redisKey);
      provinceList = JsonUtils.parseObject(redisValue, new TypeReference<List<ProvinceVo>>() {});
    } else {
      provinceList = Optional.ofNullable(provinceMapper.getList(new AddressListDto()))
          .orElse(Collections.emptyList())
          .stream()
          .map(base -> MapstructUtils.convert(base, ProvinceVo.class))
          .toList();

      List<CityVo> cityList = Optional.ofNullable(cityMapper.getList(new AddressListDto()))
          .orElse(Collections.emptyList())
          .stream()
          .map(base -> MapstructUtils.convert(base, CityVo.class))
          .toList();

      List<DistrictVo> districtList = Optional.ofNullable(districtMapper.getList(new AddressListDto()))
          .orElse(Collections.emptyList())
          .stream()
          .map(base -> MapstructUtils.convert(base, DistrictVo.class))
          .toList();

      for (CityVo city : cityList) {
        city.setChildren(districtList.stream()
            .filter(district -> district.getCityId().equals(city.getId()))
            .toList());
      }

      for (ProvinceVo province : provinceList) {
        province.setChildren(cityList.stream()
            .filter(city -> city.getProvinceId().equals(province.getId()))
            .toList());
      }

      // 以字符串形式存入
      redisService.set(redisKey, JsonUtils.toJsonString(provinceList));
    }

    return provinceList;
  }

}
