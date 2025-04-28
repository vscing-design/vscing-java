package com.vscing.model.mapper;

import com.vscing.model.dto.MerchantListDto;
import com.vscing.model.dto.MerchantOrderCountDto;
import com.vscing.model.dto.MerchantOrderListDto;
import com.vscing.model.entity.Merchant;
import com.vscing.model.vo.MerchantOrderCountVo;
import com.vscing.model.vo.MerchantOrderListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MerchantMapper
 *
 * @author vscing
 * @date 2025/4/18 23:37
 */
@Mapper
public interface MerchantMapper {

  /**
   * 管理端查询列表
   */
  List<Merchant> getList(MerchantListDto record);

  /**
   * 公用id查询信息
  */
  Merchant selectById(long id);

  /**
   * 公用merchantName查询信息
  */
  Merchant selectByMerchantName(String merchantName);

  /**
   * 公用phone查询信息
   */
  Merchant selectByMerchantPhone(String phone);

  /**
   * 公用插入信息
   */
  int insert(Merchant merchant);

  /**
   * 公用编辑信息
  */
  int update(Merchant merchant);

  /**
   * 公用编辑信息 乐观锁
   */
  int updateVersion(Merchant merchant);

  /**
   * 商户端查询订单列表
  */
  List<MerchantOrderListVo> selectByOrderList(MerchantOrderListDto record);

  /**
   * 商户端统计订单列表
   */
  List<MerchantOrderCountVo> selectByOrderCount(MerchantOrderCountDto record);

}
