package com.vscing.model.mapper;

import com.vscing.model.dto.MovieListDto;
import com.vscing.model.entity.Movie;
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

  @Select("SELECT id FROM vscing_movie WHERE tp_movie_id = #{tpMovieId}")
  Movie findByTpMovieId(@Param("tpMovieId") Long tpMovieId);

  List<Movie> getList(MovieListDto record);

  Movie selectById(long id);

  int insert(Movie record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Movie record);
  
}
