package com.vscing.model.mapper;

import com.vscing.model.dto.AddressListDto;
import com.vscing.model.entity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProvinceMapper
 *
 * @author vscing
 * @date 2024/12/23 00:47
 */
@Mapper
public interface ProvinceMapper {

  List<Province> getList(@Param("record") AddressListDto record);

  Province selectById(long id);

  int insert(Province record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Province record);

}
