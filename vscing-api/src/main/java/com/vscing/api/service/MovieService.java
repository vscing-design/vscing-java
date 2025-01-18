package com.vscing.api.service;

import com.vscing.model.dto.MovieApiCinemaDto;
import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.entity.MovieProducer;
import com.vscing.model.vo.MovieApiCinemaVo;
import com.vscing.model.vo.MovieApiDetailsVo;
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
  List<MovieApiVo> getList(MovieApiListDto data, Integer pageSize, Integer pageNum);

  /**
   * 电影详情
   */
  MovieApiDetailsVo getDetails(Long id);

  /**
   * 电影演员、导演表
  */
  List<MovieProducer> getMovieProducerList(Long id);

  /**
   * 影片详情以及影院列表
   */
  MovieApiCinemaVo getMovieCinemaList(MovieApiCinemaDto data);

}
