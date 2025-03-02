package com.vscing.admin.service;

import com.vscing.model.entity.UserConfig;
import com.vscing.model.vo.UserConfigPricingRuleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserConfigService
 *
 * @author vscing
 * @date 2025/3/2 22:15
 */
public interface UserConfigService {

  /**
   * 管理端查询所有配置信息
   */
  List<UserConfig> selectAllList();

  /**
   * 管理端更新所有配置信息
   */
  boolean batchUpsert(@Param("list") List<UserConfig> list);

  /**
   * 管理端阶段推广金额
  */
  List<UserConfigPricingRuleVo> pricingRule();

}
