package com.vscing.model.mapper;

import com.vscing.model.dto.MerchantListDto;
import com.vscing.model.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MerchantMapper
 *
 * @author vscing
 * @date 2025/4/18 23:37
 */
@Mapper
public interface MerchantMapper {

  /**
   * 管理端查询列表
   */
  List<Merchant> getList(MerchantListDto record);

  /**
   * 公用id查询信息
  */
  Merchant selectById(long id);

  /**
   * 公用merchantName查询信息
  */
  Merchant selectByMerchantName(String merchantName);

  /**
   * 公用插入信息
   */
  int insert(Merchant merchant);

  /**
   * 公用编辑信息
  */
  int update(Merchant merchant);

}
