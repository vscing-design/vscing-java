package com.vscing.model.mapper;

import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.dto.MovieListDto;
import com.vscing.model.dto.MovieTopDto;
import com.vscing.model.entity.Movie;
import com.vscing.model.vo.MovieApiVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * MovieMapper
 *
 * @author vscing
 * @date 2024/12/27 21:21
 */
@Mapper
public interface MovieMapper {

  /**
   * 管理端三方同步
  */
  @Select("SELECT id FROM vscing_movie WHERE tp_movie_id = #{tpMovieId}")
  Movie findByTpMovieId(@Param("tpMovieId") Long tpMovieId);

  /**
   * 管理端三方同步批量新增或更新。INSERT ... ON DUPLICATE KEY UPDATE 语句来实现 UPSERT 操作
   */
  int batchUpsert(@Param("list") List<Movie> list);

  /**
   * 管理端三方同步
   */
  Movie selectByTpMovieId(long tpMovieId);

  /**
   * 管理端查询影片列表
  */
  List<Movie> getList(MovieListDto record);

  /**
   * 管理端删除之前置顶
   * @param top 置顶
   */
  int deletedTop(MovieTopDto record);

  /**
   * 管理端编辑置顶
   * @param record 入参
   */
  int updatedTop(MovieTopDto record);

  /**
   * 小程序端查询列表
  */
  List<MovieApiVo> getApiList(MovieApiListDto record);

  /**
   * 公用查询影片详情
  */
  Movie selectById(long id);

  int insert(Movie record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Movie record);
  
}
