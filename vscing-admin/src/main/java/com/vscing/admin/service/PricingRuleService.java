package com.vscing.admin.service;

import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.PricingRule;

import java.util.List;

/**
 * PricingRuleService
 *
 * @author vscing
 * @date 2024/12/29 23:20
 */
public interface PricingRuleService {

  /**
   * 列表
   */
  List<PricingRule> getList(PricingRuleListDto data, Integer pageSize, Integer pageNum);

  /**
   * 详情
   */
  PricingRule getDetails(long id);

  /**
   * 新增
   */
  long created(PricingRule data);

  /**
   * 编辑
   */
  long updated(PricingRule data);

  /**
   * 删除
   */
  long deleted(long id, long deleterId);
  
}
