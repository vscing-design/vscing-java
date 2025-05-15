package com.vscing.model.mapper;

import com.vscing.model.dto.AddressListDto;
import com.vscing.model.dto.CinemaApiDistrictDto;
import com.vscing.model.entity.District;
import com.vscing.model.platform.QueryDistrict;
import com.vscing.model.vo.CinemaApiDistrictVo;
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

  @Select("SELECT * FROM vscing_district WHERE s1_city_id IS NOT NULL")
  List<District> getTaskList();

  @Select("SELECT * FROM vscing_district WHERE id = #{code}")
  District findByCode(@Param("code") String code);

  @Select("SELECT * FROM vscing_district WHERE name = #{name} and city_id = #{cityId} limit 1")
  District findByName(@Param("name") String name, @Param("cityId") Long cityId);

  @Update("UPDATE vscing_district SET s1_city_id = #{cityId} WHERE id = #{id}")
  void updateCity(@Param("id") Long id, @Param("cityId") Long cityId);

  @Update("UPDATE vscing_district SET s1_region_id = #{regionId} WHERE id = #{id}")
  void updateRegion(@Param("id") Long id, @Param("regionId") Long regionId);

  List<District> getAllList();

  List<District> getList(@Param("record") AddressListDto record);

  List<CinemaApiDistrictVo> selectByCityId(CinemaApiDistrictDto record);

  District selectById(long id);

  int insert(District record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(District record);

  /**
   * 开放平台查区县列表
   */
  List<QueryDistrict> getPlatformList();
}
