package com.vscing.model.mapper;

import com.vscing.model.dto.MovieShowAreaListDto;
import com.vscing.model.entity.ShowArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MovieShowAreaMapper
 *
 * @author vscing
 * @date 2024/12/27 22:28
 */
@Mapper
public interface ShowAreaMapper {

  List<ShowArea> getList(MovieShowAreaListDto record);

  ShowArea selectById(long id);

  int insert(ShowArea record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(ShowArea record);

}
