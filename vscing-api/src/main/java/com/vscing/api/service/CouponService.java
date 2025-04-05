package com.vscing.api.service;

import com.vscing.model.dto.CouponApiListDto;
import com.vscing.model.entity.Coupon;
import com.vscing.model.request.CouponDetailsRequest;
import com.vscing.model.request.CouponRequest;
import com.vscing.model.vo.CouponApiDetailsVo;

import java.util.List;

/**
 * CouponService
 *
 * @author vscing
 * @date 2025/3/22 17:07
 */
public interface CouponService {

  /**
   * 优惠券列表
   */
  List<Coupon> selectApiList(CouponApiListDto data, Integer pageSize, Integer pageNum);

  /**
   * 新增优惠券
  */
  boolean save(CouponRequest data);

  /**
   * 批量新增优惠券
   */
  boolean batchSave(List<CouponRequest> list);

  /**
   * 查询优惠券详情
  */
  CouponApiDetailsVo details(CouponDetailsRequest data);

  /**
   * 校验
   */
  boolean verify(String builderStr, String sign);
}
