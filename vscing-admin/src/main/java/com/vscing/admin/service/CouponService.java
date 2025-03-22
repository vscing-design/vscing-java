package com.vscing.admin.service;

import com.vscing.model.dto.CouponListDto;
import com.vscing.model.entity.Coupon;

import java.util.List;

/**
 * CouponService
 *
 * @author vscing
 * @date 2025/3/23 01:13
 */
public interface CouponService {

  /**
   * 列表
   */
  List<Coupon> getList(CouponListDto data, Integer pageSize, Integer pageNum);

}
