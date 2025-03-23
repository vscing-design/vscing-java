package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.CouponService;
import com.vscing.model.dto.CouponListDto;
import com.vscing.model.entity.Coupon;
import com.vscing.model.mapper.CouponMapper;
import com.vscing.model.request.CouponCancelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    int rowsAffected = couponMapper.updateCouponCancel(data, by);
    return rowsAffected > 0;
  }

}
