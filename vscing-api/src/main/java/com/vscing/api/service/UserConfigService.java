package com.vscing.api.service;

import com.vscing.model.vo.UserConfigPricingRuleVo;

import java.util.List;
import java.util.Map;

/**
 * UserConfigService
 *
 * @author vscing
 * @date 2025/3/2 22:15
 */
public interface UserConfigService {

  /**
   * 缓存配置数据
   */
  Map<String, String> getConfig();

  /**
   * 推广金额列表
  */
  List<UserConfigPricingRuleVo> pricingRule();

}
