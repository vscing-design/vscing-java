package com.vscing.api.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.api.service.CouponService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.CouponApiListDto;
import com.vscing.model.entity.Coupon;
import com.vscing.model.entity.User;
import com.vscing.model.mapper.CouponMapper;
import com.vscing.model.mapper.UserMapper;
import com.vscing.model.request.CouponDetailsRequest;
import com.vscing.model.request.CouponRequest;
import com.vscing.model.vo.CouponApiDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CouponServiceImpl
 *
 * @author vscing
 * @date 2025/3/22 17:07
 */
@Service
public class CouponServiceImpl implements CouponService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private CouponMapper couponMapper;

  @Override
  public List<Coupon> selectApiList(CouponApiListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return couponMapper.selectApiList(data);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean save(CouponRequest data) {
    // 创建数据
    try {
      // 创建用户
      Long userId = IdUtil.getSnowflakeNextId();
      User userInfo = userMapper.selectByPhone(data.getPhone());
      int rowsAffected = 0;
      // 处理用户逻辑
      if(userInfo == null) {
        User user = new User();
        user.setId(userId);
        user.setSource(25);
        String username = "HY_" + RandomUtil.randomString(8);
        user.setUsername(username);
        user.setCreatedBy(0L);
        user.setPhone(data.getPhone());
        rowsAffected = userMapper.insert(user);
        if (rowsAffected <= 0) {
          throw new ServiceException("创建用户失败");
        }
      } else {
        userId = userInfo.getId();
      }
      // 创建订单数据
      Coupon coupon = new Coupon();
      coupon.setId(IdUtil.getSnowflakeNextId());
      coupon.setUserId(userId);
      coupon.setTitle(data.getTitle());
      coupon.setPhone(data.getPhone());
      coupon.setCode(data.getCode());
      coupon.setSalesAmount(data.getSalesAmount());
      coupon.setFaceAmount(data.getFaceAmount());
      coupon.setAttr(data.getAttr());
      coupon.setSource(data.getSource());
      coupon.setCondition(data.getCondition());
      coupon.setStartTime(data.getStartTime());
      coupon.setEndTime(data.getEndTime());
      coupon.setCreatedAt(data.getCreatedAt());
      rowsAffected = couponMapper.insert(coupon);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建优惠券失败");
      }
      return true;
    } catch (Exception e) {
      throw new ServiceException("创建用户失败");
    }
  }

  @Override
  public CouponApiDetailsVo details(CouponDetailsRequest data) {
    return couponMapper.selectByPhoneCode(data.getPhone(), data.getCode());
  }
}
