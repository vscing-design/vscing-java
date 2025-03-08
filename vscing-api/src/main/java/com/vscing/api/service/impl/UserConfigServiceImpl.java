package com.vscing.api.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vscing.api.service.UserConfigService;
import com.vscing.common.service.RedisService;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.entity.UserConfig;
import com.vscing.model.mapper.PricingRuleMapper;
import com.vscing.model.mapper.UserConfigMapper;
import com.vscing.model.vo.UserConfigPricingRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserConfigServiceImpl
 *
 * @author vscing
 * @date 2025/3/2 22:16
 */
@Service
public class UserConfigServiceImpl implements UserConfigService {

  private static final String USER_CONFIG_LIST = "userConfigList";

  @Autowired
  private RedisService redisService;

  @Autowired
  private UserConfigMapper userConfigMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  @Override
  public Map<String, String> getConfig() {
    Map<String, String> config = new HashMap<>(2);
    String json = (String) redisService.get(USER_CONFIG_LIST);
    if(json == null) {
      List<UserConfig> list = userConfigMapper.selectAllList();
      list.forEach(userConfig -> config.put(userConfig.getKey(), userConfig.getValue()));
      redisService.set(USER_CONFIG_LIST, JsonUtils.toJsonString(config));
    } else {
      return JsonUtils.parseObject(json, new TypeReference<Map<String, String>>() {});
    }
    return config;
  }

  @Override
  public List<UserConfigPricingRuleVo> pricingRule() {
    // 佣金比例
    String commissionRateStr = getConfig().get("commissionRate");
    BigDecimal commissionRate = new BigDecimal(commissionRateStr);
    // 判断commissionRate是否大于0
    if(commissionRate.compareTo(BigDecimal.ZERO) > 0) {
      // 如果大于0，则除以100
      commissionRate = commissionRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
    // 查询规则表
    List<PricingRule> pricingRuleList = pricingRuleMapper.getList(new PricingRuleListDto());
    List<UserConfigPricingRuleVo> userConfigPricingRuleVoList =MapstructUtils.convert(pricingRuleList, UserConfigPricingRuleVo.class);
    if (userConfigPricingRuleVoList != null && userConfigPricingRuleVoList.size() > 0) {
      // 遍历列表并更新b属性
      for (UserConfigPricingRuleVo vo : userConfigPricingRuleVoList) {
        BigDecimal markupAmount = vo.getMarkupAmount();
        markupAmount = markupAmount.multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);
        vo.setEarnAmount(markupAmount);
      }
    }
    return userConfigPricingRuleVoList;
  }
}
