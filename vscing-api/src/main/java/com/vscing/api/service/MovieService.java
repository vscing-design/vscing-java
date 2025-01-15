package com.vscing.api.service;

import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.vo.MovieApiVo;
import com.vscing.model.vo.MovieBannersVo;

import java.util.List;

/**
 * MovieService
 *
 * @author vscing
 * @date 2025/1/13 10:23
 */
public interface MovieService {

  /**
   * 轮播图
   */
  List<MovieBannersVo> getBanners();

  /**
   * 列表
   */
  List<MovieApiVo> getList(MovieApiListDto data);

}
