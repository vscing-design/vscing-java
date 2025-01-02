package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.PricingRuleService;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.mapper.PricingRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PricingRuleServiceImpl
 *
 * @author vscing
 * @date 2024/12/29 23:21
 */
@Service
public class PricingRuleServiceImpl implements PricingRuleService {

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  @Override
  public List<PricingRule> getList(PricingRuleListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return pricingRuleMapper.getList(data);
  }

  @Override
  public PricingRule getDetails(long id) {
    return pricingRuleMapper.selectById(id);
  }

  @Override
  public long created(PricingRule pricingRule) {
    pricingRule.setId(IdUtil.getSnowflakeNextId());
    return pricingRuleMapper.insert(pricingRule);
  }

  @Override
  public long updated(PricingRule supplier) {
    return pricingRuleMapper.update(supplier);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return pricingRuleMapper.softDeleteById(id, deleterId);
  }


}
