package com.vscing.model.mapper;

import com.vscing.model.dto.CouponListDto;
import com.vscing.model.entity.Coupon;
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
   * 创建
  */
  int insert(Coupon record);

  /**
   * 查询详情
  */
  CouponApiDetailsVo selectByPhoneCode(@Param("phone") String phone, @Param("code") String code);

}
