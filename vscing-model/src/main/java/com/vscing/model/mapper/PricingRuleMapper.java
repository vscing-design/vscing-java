package com.vscing.model.mapper;

import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.PricingRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PricingRuleMapper
 *
 * @author vscing
 * @date 2024/12/29 23:38
 */
@Mapper
public interface PricingRuleMapper {

  List<PricingRule> getList(PricingRuleListDto record);

  PricingRule selectById(long id);

  int insert(PricingRule record);

  int update(PricingRule record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);
  
}
