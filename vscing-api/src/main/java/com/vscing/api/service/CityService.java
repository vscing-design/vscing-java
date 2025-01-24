package com.vscing.api.service;

import com.vscing.model.dto.CurrentCityApiDto;
import com.vscing.model.vo.AppletCityVo;

import java.util.List;

/**
 * CityService
 *
 * @author vscing
 * @date 2025/1/11 18:51
 */
public interface CityService {

  /**
   * 获取城市列表，以及直辖市
  */
  List<AppletCityVo> getAllList();

  /**
   * 获取当前城市
  */
  AppletCityVo currentCity(CurrentCityApiDto requestData);

}
