package com.vscing.model.mapper;

import com.vscing.model.entity.VipGoodsAttach;
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
public interface VipGoodsAttachMapper {

  /**
   * 批量插入数据源
  */
  int batchInsert(@Param("list") List<VipGoodsAttach> list);

  /**
   * 修改数据源
  */
  int update(VipGoodsAttach record);

}
