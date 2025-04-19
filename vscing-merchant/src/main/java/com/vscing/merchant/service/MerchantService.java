package com.vscing.merchant.service;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.vo.MerchantDetailVo;

/**
 * MerchantService
 *
 * @author vscing
 * @date 2025/4/18 22:47
 */
public interface MerchantService {

  /**
   * 搭配springSecurity权限
   */
  VscingUserDetails merchantInfo(long id);

  /**
   * 商户信息
   */
  MerchantDetailVo self(long id);

}
