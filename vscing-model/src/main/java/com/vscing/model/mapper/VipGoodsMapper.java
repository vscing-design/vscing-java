package com.vscing.model.mapper;

import com.vscing.model.dto.AdminVipGoodsDto;
import com.vscing.model.entity.VipGoods;
import com.vscing.model.vo.AdminVipGoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VipGoodsMapper
 *
 * @author vscing
 * @date 2025/6/1 19:10
 */
@Mapper
public interface VipGoodsMapper {

  /**
   * 管理端查询列表
   */
  List<AdminVipGoodsVo> getAdminList(AdminVipGoodsDto record);

  /**
   * 批量插入数据源
   */
  int batchInsert(@Param("list") List<VipGoods> list);

  /**
   * 插入数据源
  */
  int insert(VipGoods record);

  /**
   * 修改数据源
  */
  int update(VipGoods record);

  /**
   * 查询
   */
  VipGoods selectByTpGoodsId(Long tpGoodsId);

}
