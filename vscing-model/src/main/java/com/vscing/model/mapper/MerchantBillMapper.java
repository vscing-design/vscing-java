package com.vscing.model.mapper;

import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.dto.MerchantBillRechargeListDto;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.vo.MerchantBillRechargeListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MerchantBillMapper
 *
 * @author vscing
 * @date 2025/4/19 15:43
 */
@Mapper
public interface MerchantBillMapper {

  /**
   * 管理端查询列表
   */
  List<MerchantBill> getList(MerchantBillListDto record);

  /**
   * 管理端查询充值列表
   */
  List<MerchantBillRechargeListVo> getRechargeList(MerchantBillRechargeListDto record);

  /**
   * 公用查询商户信息
   */
  MerchantBill selectById(long id);

  /**
   * 公用插入商户对公户信息
   */
  int insert(MerchantBill merchant);

  /**
   * 公用编辑商户对公户信息
   */
  int update(MerchantBill merchant);

  /**
   * 软删除商户对公户信息
   */
  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

}
