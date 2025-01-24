package com.vscing.api.service;

import com.vscing.model.dto.CinemaApiDetailsDto;
import com.vscing.model.dto.CinemaApiDistrictDto;
import com.vscing.model.dto.CinemaApiListDto;
import com.vscing.model.vo.CinemaApiDetailsVo;
import com.vscing.model.vo.CinemaApiDistrictVo;
import com.vscing.model.vo.CinemaApiVo;

import java.util.List;

/**
 * CinemaService
 *
 * @author vscing
 * @date 2025/1/16 23:15
 */
public interface CinemaService {

  /**
   * 列表城市筛选项
   */
  List<CinemaApiDistrictVo> getDistrictList(CinemaApiDistrictDto data);

  /**
   * 列表
   */
  List<CinemaApiVo> getList(CinemaApiListDto data, Integer pageSize, Integer pageNum);

  /**
   * 详情
   */
  CinemaApiDetailsVo getDetails(CinemaApiDetailsDto data);

}
