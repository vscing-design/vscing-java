package com.vscing.model.mapper;

import com.vscing.model.dto.MovieListDto;
import com.vscing.model.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MovieMapper
 *
 * @author vscing
 * @date 2024/12/27 21:21
 */
@Mapper
public interface MovieMapper {

  List<Movie> getList(MovieListDto record);

  Movie selectById(long id);

  int insert(Movie record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Movie record);
  
}
