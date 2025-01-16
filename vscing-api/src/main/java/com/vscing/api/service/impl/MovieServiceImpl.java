package com.vscing.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.api.service.MovieService;
import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.mapper.MovieMapper;
import com.vscing.model.mapper.PricingRuleMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.vo.MinPriceVo;
import com.vscing.model.vo.MovieApiVo;
import com.vscing.model.vo.MovieBannersVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MovieServiceImpl
 *
 * @author vscing
 * @date 2025/1/13 10:44
 */
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

  @Autowired
  private MovieMapper movieMapper;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  public List<MovieBannersVo> getBanners() {
    return movieMapper.selectBanners();
  }

  public List<MovieApiVo> getList(MovieApiListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<MovieApiVo> movieApiVoList = showMapper.selectByMovieApiList(data);

    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());

    // 获取区域价格列表
    List<Long> movieIds = movieApiVoList.stream()
        .map(MovieApiVo::getId)
        .collect(Collectors.toList());
    // 获取区域价格列表
//    List<MinPriceVo> minPriceList = showAreaMapper.getMinPriceByMovieIds(movieIds);

//    log.error("minPriceList: {}", minPriceList);


//    // 遍历第一步的结果集
//    for (MovieApiVo movieApiVo : movieApiVoList) {
//      String showIds = movieApiVo.getShowIds();
//      // 获取价格
//      ShowArea showArea = showAreaMapper.getMinPriceByShowIds(showIds);
//      // 实际销售价格
//      BigDecimal price = PricingUtil.calculateActualPrice(showArea.getShowPrice(), showArea.getUserPrice(), pricingRules);
//      // 改变对象值
//      movieApiVo.setMinPrice(price);
//    }

    return movieApiVoList;
  }

}
