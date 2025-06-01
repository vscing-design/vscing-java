package com.vscing.model.mapper;

import com.vscing.model.entity.VipGoods;
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
