package com.vscing.api.service;

import com.vscing.model.request.CouponDetailsRequest;
import com.vscing.model.request.CouponRequest;
import com.vscing.model.vo.CouponApiDetailsVo;

/**
 * CouponService
 *
 * @author vscing
 * @date 2025/3/22 17:07
 */
public interface CouponService {

  /**
   * 新增优惠券
  */
  boolean save(CouponRequest data);

  /**
   * 查询优惠券详情
  */
  CouponApiDetailsVo details(CouponDetailsRequest data);

}
