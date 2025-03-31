package com.vscing.model.mapper;

import com.vscing.model.dto.CouponApiListDto;
import com.vscing.model.dto.CouponListDto;
import com.vscing.model.entity.Coupon;
import com.vscing.model.request.CouponCancelRequest;
import com.vscing.model.vo.CouponApiDetailsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CouponMapper
 *
 * @author vscing
 * @date 2025/3/23 00:15
 */
@Mapper
public interface CouponMapper {

  /**
   * 列表
   */
  List<Coupon> getList(CouponListDto record);

  /**
   * 详情
   */
  Coupon selectById(@Param("id") Long id);

  /**
   * 校验优惠券
   */
  Coupon verifyCoupon(@Param("userId") Long userId, @Param("couponId") Long couponId);

  /**
   * 作废
   */
  int updateCouponCancel(CouponCancelRequest record);

  /**
   * 创建
   */
  int insert(Coupon record);

  /**
   * 批量创建
   */
  int batchInsert(List<Coupon> list);

  /**
   * 修改优惠券状态
   */
  int updateStatus(Coupon record);

  /**
   * 批量取消优惠券
   */
  int batchCancel();

  /**
   * 查询详情
   */
  CouponApiDetailsVo selectByPhoneCode(@Param("phone") String phone, @Param("code") String code);

  /**
   * 我的列表
   */
  List<Coupon> selectApiList(CouponApiListDto record);


}
