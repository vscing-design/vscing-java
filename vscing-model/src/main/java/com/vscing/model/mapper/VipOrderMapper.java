package com.vscing.model.mapper;

import com.vscing.model.dto.AdminVipOrderDto;
import com.vscing.model.entity.VipGroup;
import com.vscing.model.vo.AdminVipOrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * VipOrderMapper
 *
 * @author vscing
 * @date 2025/6/2 17:41
 */
@Mapper
public interface VipOrderMapper {

  /**
   * 管理端查询列表
   */
  List<AdminVipOrderVo> getAdminList(AdminVipOrderDto record);

  /**
   * 插入数据源
   */
  int insert(VipGroup record);

  /**
   * 修改数据源
   */
  int update(VipGroup record);

}
