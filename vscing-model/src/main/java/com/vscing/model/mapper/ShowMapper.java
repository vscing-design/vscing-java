package com.vscing.model.mapper;

import com.vscing.model.dto.MovieShowListDto;
import com.vscing.model.entity.Show;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MovieShowMapper
 *
 * @author vscing
 * @date 2024/12/27 22:17
 */
@Mapper
public interface ShowMapper {

  List<Show> getList(MovieShowListDto record);

  Show selectById(long id);

  int insert(Show record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Show record);

}
