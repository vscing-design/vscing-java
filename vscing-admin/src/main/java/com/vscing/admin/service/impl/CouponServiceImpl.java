package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.CouponService;
import com.vscing.model.dto.CouponListDto;
import com.vscing.model.entity.Coupon;
import com.vscing.model.mapper.CouponMapper;
import com.vscing.model.request.CouponCancelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CouponServiceImpl
 *
 * @author vscing
 * @date 2025/3/23 01:14
 */
@Service
public class CouponServiceImpl implements CouponService {

  @Autowired
  private CouponMapper couponMapper;

  @Override
  public List<Coupon> getList(CouponListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return couponMapper.getList(data);
  }

  @Override
  public boolean couponCancel(CouponCancelRequest data, Long by) {
    // 设置作废时间
    data.setCancelAt(LocalDateTime.now());
    // 设置更新人
    data.setUpdatedBy(by);
    int rowsAffected = couponMapper.updateCouponCancel(data);
    return rowsAffected > 0;
  }

}
