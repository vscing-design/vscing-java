package com.vscing.merchant.po;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.vo.MerchantDetailVo;

/**
 * UserDetails
 *
 * @author vscing
 * @date 2025/1/6 11:52
 */
public interface MerchantDetails extends VscingUserDetails {

  MerchantDetailVo getMerchant();

}
