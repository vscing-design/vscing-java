package com.vscing.model.mapper;

import com.vscing.model.dto.CinemaApiDetailsDto;
import com.vscing.model.dto.CinemaListDto;
import com.vscing.model.entity.Cinema;
import com.vscing.model.vo.CinemaApiDetailsVo;
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

  CinemaApiDetailsVo selectByIdWithDistance(CinemaApiDetailsDto record);

  int insert(Cinema record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Cinema record);
  
}
