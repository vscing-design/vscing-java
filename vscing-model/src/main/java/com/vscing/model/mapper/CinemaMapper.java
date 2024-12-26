package com.vscing.model.mapper;

import com.vscing.model.dto.CinemaListDto;
import com.vscing.model.entity.Cinema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CinemaMapper
 *
 * @author vscing
 * @date 2024/12/26 00:44
 */
@Mapper
public interface CinemaMapper {

  List<Cinema> getList(CinemaListDto record);

  Cinema selectById(long id);

  int insert(Cinema record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Cinema record);
  
}
