package com.vscing.admin.service;

import com.vscing.model.dto.CouponListDto;
import com.vscing.model.request.CouponCancelRequest;
import com.vscing.model.vo.CouponListVo;

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
  List<CouponListVo> getList(CouponListDto data, Integer pageSize, Integer pageNum);

  /**
   * 优惠劵作废
   */
  boolean couponCancel(CouponCancelRequest data, Long by);

}
