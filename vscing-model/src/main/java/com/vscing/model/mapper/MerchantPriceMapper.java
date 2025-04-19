package com.vscing.model.mapper;

import com.vscing.model.dto.MerchantPriceListDto;
import com.vscing.model.entity.MerchantPrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MerchantPriceMapper
 *
 * @author vscing
 * @date 2025/4/19 09:53
 */
@Mapper
public interface MerchantPriceMapper {

  /**
   * 管理端查询列表
   */
  List<MerchantPrice> getList(MerchantPriceListDto record);

  /**
   * 公用查询信息
   */
  MerchantPrice selectById(long id);

  /**
   * 公用插入信息
   */
  int insert(MerchantPrice merchantPrice);

  /**
   * 公用编辑信息
   */
  int update(MerchantPrice merchantPrice);

}
