package com.vscing.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.api.service.CinemaService;
import com.vscing.model.dto.CinemaApiListDto;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.mapper.PricingRuleMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.vo.CinemaApiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CinemaServiceImpl
 *
 * @author vscing
 * @date 2025/1/16 23:25
 */
@Service
public class CinemaServiceImpl implements CinemaService {

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  public List<CinemaApiVo> getList(CinemaApiListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<CinemaApiVo> cinemaApiVoList = showMapper.selectByCinemaApiList(data);

    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());

    return cinemaApiVoList;
  }

}
