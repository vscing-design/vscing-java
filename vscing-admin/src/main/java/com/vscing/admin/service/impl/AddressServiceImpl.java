package com.vscing.admin.service.impl;

import com.vscing.admin.service.AddressService;
import com.vscing.common.service.RedisService;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.common.utils.RedisKeyConstants;
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

    String redisKey = RedisKeyConstants.CACHE_KEY_PREFIX_ADMIN + RedisKeyConstants.KEY_SEPARATOR + "addressList";

    List<ProvinceVo> provinceList;

    // redisService.hasKey(redisKey)
    if(false) {
      provinceList = (List<ProvinceVo>) redisService.get(redisKey);
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

//      redisService.set(redisKey, provinceList);
    }

    return provinceList;
  }

}
