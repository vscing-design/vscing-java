package com.vscing.admin.service;

import com.vscing.model.dto.AdminVipGoodsDto;
import com.vscing.model.dto.AdminVipGoodsPricingDto;
import com.vscing.model.dto.AdminVipGroupDto;
import com.vscing.model.request.AdminVipGoodsPricingRequest;
import com.vscing.model.vo.AdminVipGoodsPricingVo;
import com.vscing.model.vo.AdminVipGoodsVo;
import com.vscing.model.vo.AdminVipGroupVo;

import java.util.List;

/**
 * VipGoodsService
 *
 * @author vscing
 * @date 2025/6/2 15:46
 */
public interface VipGoodsService {

  /**
   * 会员商品分组列表
  */
  List<AdminVipGroupVo> getGroupList(AdminVipGroupDto record, Integer pageSize, Integer pageNum);

  /**
   * 会员商品列表
   */
  List<AdminVipGoodsVo> getGoodsList(AdminVipGoodsDto record, Integer pageSize, Integer pageNum);

  /**
   * 会员商品定价列表
   */
  List<AdminVipGoodsPricingVo> getGoodsPricingList(AdminVipGoodsPricingDto record, Integer pageSize, Integer pageNum);

  /**
   * 会员商品定价
  */
  void vipGoodsPricing(List<AdminVipGoodsPricingRequest> record, Long by);

}
