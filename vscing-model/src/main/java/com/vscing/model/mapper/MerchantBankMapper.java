package com.vscing.model.mapper;

import com.vscing.model.dto.MerchantBankListDto;
import com.vscing.model.entity.MerchantBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MerchantBank
 *
 * @author vscing
 * @date 2025/4/19 10:08
 */
@Mapper
public interface MerchantBankMapper {

  /**
   * 管理端查询列表
   */
  List<MerchantBank> getList(MerchantBankListDto record);

  /**
   * 商户管理端查询列表
   */
  List<MerchantBank> getBankList(@Param("merchantId") long merchantId);

  /**
   * 公用查询商户信息
   */
  MerchantBank selectById(long id);

  /**
   * 公用插入商户对公户信息
   */
  int insert(MerchantBank merchant);

  /**
   * 公用编辑商户对公户信息
   */
  int update(MerchantBank merchant);

  /**
   * 软删除商户对公户信息
  */
  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

}
