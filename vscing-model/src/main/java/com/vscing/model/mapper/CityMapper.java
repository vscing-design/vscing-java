package com.vscing.model.mapper;

import com.vscing.model.dto.AddressListDto;
import com.vscing.model.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ProvinceMapper
 *
 * @author vscing
 * @date 2024/12/23 00:47
 */
@Mapper
public interface CityMapper {

  @Select("SELECT * FROM vscing_city WHERE id = #{code}")
  City findByCode(@Param("code") String code);

  @Update("UPDATE vscing_city SET s1_city_id = #{cityId} WHERE id = #{id}")
  void updateCityId(@Param("id") Long id, @Param("cityId") Long cityId);

  List<City> getList(@Param("record") AddressListDto record);

  City selectById(long id);

  int insert(City record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(City record);

}
