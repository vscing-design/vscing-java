package com.vscing.model.mapper;

import com.vscing.model.dto.AdminVipGroupDto;
import com.vscing.model.entity.VipGroup;
import com.vscing.model.platform.QueryVipGroup;
import com.vscing.model.platform.QueryVipGroupDto;
import com.vscing.model.vo.AdminVipGroupVo;
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
   * 开放平台查询列表
   */
  List<QueryVipGroup> getPlatformList(QueryVipGroupDto record);

  /**
   * 管理端查询列表
   */
  List<AdminVipGroupVo> getAdminList(AdminVipGroupDto record);

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
