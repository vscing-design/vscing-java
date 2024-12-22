package com.vscing.model.mapper;

import com.vscing.model.dto.AddressListDto;
import com.vscing.model.entity.District;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DistrictMapper
 *
 * @author vscing
 * @date 2024/12/23 01:02
 */
@Mapper
public interface DistrictMapper {

  List<District> getList(@Param("record") AddressListDto record);

  District selectById(long id);

  int insert(District record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(District record);
}
