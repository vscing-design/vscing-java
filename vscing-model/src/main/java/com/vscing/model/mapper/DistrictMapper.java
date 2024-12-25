package com.vscing.model.mapper;

import com.vscing.model.dto.AddressListDto;
import com.vscing.model.entity.District;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * DistrictMapper
 *
 * @author vscing
 * @date 2024/12/23 01:02
 */
@Mapper
public interface DistrictMapper {

  @Select("SELECT * FROM vscing_district WHERE id = #{code}")
  District findByCode(@Param("code") String code);

  @Select("SELECT * FROM vscing_district WHERE name = #{name} and city_id = #{cityId} limit 1")
  District findByName(@Param("name") String name, @Param("cityId") Long cityId);

  @Update("UPDATE vscing_district SET s1_city_id = #{cityId} WHERE id = #{id}")
  void updateCity(@Param("id") Long id, @Param("cityId") Long cityId);

  @Update("UPDATE vscing_district SET s1_region_id = #{regionId} WHERE id = #{id}")
  void updateRegion(@Param("id") Long id, @Param("regionId") Long regionId);

  List<District> getList(@Param("record") AddressListDto record);

  District selectById(long id);

  int insert(District record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(District record);
}
