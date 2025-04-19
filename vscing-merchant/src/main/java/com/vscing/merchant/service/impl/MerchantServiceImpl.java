package com.vscing.merchant.service.impl;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.merchant.po.impl.MerchantDetailsImpl;
import com.vscing.merchant.service.MerchantService;
import com.vscing.model.entity.Merchant;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.vo.MerchantDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MerchantServiceImpl
 *
 * @author vscing
 * @date 2025/4/18 22:47
 */
@Service
public class MerchantServiceImpl implements MerchantService {

  @Autowired
  private MerchantMapper merchantMapper;

  @Override
  public VscingUserDetails merchantInfo(long id) {
    // 获取用户信息
    MerchantDetailVo merchantDetail = this.self(id);
    if (merchantDetail != null) {
      return new MerchantDetailsImpl(merchantDetail);
    }
    return null;
  }

  @Override
  public MerchantDetailVo self(long id) {
    // 获取用户信息
    Merchant merchant = merchantMapper.selectById(id);
    // 直接调用改进后的 Mapper 方法进行转换
    return MapstructUtils.convert(merchant, MerchantDetailVo.class);
  }

}
