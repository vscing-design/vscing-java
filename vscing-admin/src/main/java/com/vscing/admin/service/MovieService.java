package com.vscing.admin.service;

import com.vscing.model.dto.MovieListDto;
import com.vscing.model.entity.Movie;
import com.vscing.model.entity.MovieProducer;

import java.util.List;

/**
 * MovieService
 *
 * @author vscing
 * @date 2024/12/28 19:18
 */
public interface MovieService {

  /**
   * 批量插入三方数据源
   * @param movieList 电影数据
   * @param movieProducerList 电影导演、演员数据
   */
  boolean initMovie(List<Movie> movieList, List<MovieProducer> movieProducerList);

  /**
   * 列表
   */
  List<Movie> getList(MovieListDto data, Integer pageSize, Integer pageNum);

  /**
   * 置顶
   */
  boolean updatedTop(Movie data);

}
