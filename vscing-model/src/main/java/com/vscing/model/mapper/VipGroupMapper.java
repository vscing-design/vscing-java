package com.vscing.model.mapper;

import com.vscing.model.entity.VipGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VipGroupMapper
 *
 * @author vscing
 * @date 2025/6/1 19:14
 */
@Mapper
public interface VipGroupMapper {

  /**
   * 批量插入数据源
   */
  int batchInsert(@Param("list") List<VipGroup> list);

  /**
   * 插入数据源
   */
  int insert(VipGroup record);

  /**
   * 修改数据源
   */
  int update(VipGroup record);

  /**
   * 查询
  */
  VipGroup selectByTpGroupId(Long tpGroupId);

}
