package com.vscing.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.api.service.CinemaService;
import com.vscing.model.dto.CinemaApiDetailsDto;
import com.vscing.model.dto.CinemaApiDistrictDto;
import com.vscing.model.dto.CinemaApiListDto;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.mapper.CinemaMapper;
import com.vscing.model.mapper.DistrictMapper;
import com.vscing.model.mapper.PricingRuleMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.utils.PricingUtil;
import com.vscing.model.vo.CinemaApiDetailsShowVo;
import com.vscing.model.vo.CinemaApiDetailsVo;
import com.vscing.model.vo.CinemaApiDistrictVo;
import com.vscing.model.vo.CinemaApiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CinemaServiceImpl
 *
 * @author vscing
 * @date 2025/1/16 23:25
 */
@Service
public class CinemaServiceImpl implements CinemaService {

  @Autowired
  private CinemaMapper cinemaMapper;

  @Autowired
  private DistrictMapper districtMapper;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  @Override
  public List<CinemaApiDistrictVo> getDistrictList(CinemaApiDistrictDto data) {
    // 如果是区县级，就返回空list
    if(data.getDistrictId() != null) {
      return Collections.emptyList();
    }
    return districtMapper.selectByCityId(data);
  }

  @Override
  public List<CinemaApiVo> getList(CinemaApiListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<CinemaApiVo> cinemaApiVoList = showMapper.selectByCinemaApiList(data);

    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());

    // 循环计算实际售价
    cinemaApiVoList.forEach(cinemaApiVo -> {
      // 实际销售价格
      BigDecimal price = PricingUtil.calculateActualPrice(cinemaApiVo.getMinShowPrice(), cinemaApiVo.getMinUserPrice(), pricingRules);
      // 实际最小出售价格
      cinemaApiVo.setMinPrice(price);
      // 实际最大出售价格
      cinemaApiVo.setMaxPrice(cinemaApiVo.getMinShowPrice());
      // 重置其他数据
//      cinemaApiVo.setMinShowPrice(null);
//      cinemaApiVo.setMinUserPrice(null);
    });

    return cinemaApiVoList;
  }

  @Override
  public CinemaApiDetailsVo getDetails(CinemaApiDetailsDto data) {
    // 查询影院信息
    CinemaApiDetailsVo cinemaApiDetailsVo = cinemaMapper.selectByIdWithDistance(data);
    if (cinemaApiDetailsVo == null) {
      return null;
    }
    // 根据影院id，查询出所有影片场次列表
    List<CinemaApiDetailsShowVo> cinemaApiDetailsShowVoList = showMapper.selectByCinemaId(data.getId());
    if (cinemaApiDetailsShowVoList == null || cinemaApiDetailsShowVoList.isEmpty()) {
      cinemaApiDetailsVo.setShowList(Collections.emptyList());
      return cinemaApiDetailsVo;
    }
    // 根据场次id，查询场次区域价格列表
    List<Long> showIds = cinemaApiDetailsShowVoList.stream()
        .map(CinemaApiDetailsShowVo::getShowId)
        .collect(Collectors.toList());
    if (showIds.isEmpty()) {
      cinemaApiDetailsVo.setShowList(cinemaApiDetailsShowVoList);
      return cinemaApiDetailsVo;
    }
    // 场次列表
    List<ShowArea> showAreaList = showAreaMapper.selectByShowIds(showIds);
    if (showAreaList == null || showAreaList.isEmpty()) {
      cinemaApiDetailsVo.setShowList(cinemaApiDetailsShowVoList);
      return cinemaApiDetailsVo;
    }
    // 使用 Stream API 创建映射，将每个 showId 映射到其最低价格对应的 ShowArea 对象
    Map<Long, ShowArea> ShowAreaMinPrice = showAreaList.stream()
        .collect(Collectors.toMap(
            ShowArea::getShowId,
            Function.identity(),
            (existing, replacement) -> existing.getShowPrice().compareTo(replacement.getShowPrice()) <= 0 ? existing : replacement
        ));
    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());
    // 循环影片场次列表，并计算出最低价格和最高价格
    cinemaApiDetailsShowVoList.forEach(cinemaApiDetailsShowVo -> {
      ShowArea showArea = ShowAreaMinPrice.get(cinemaApiDetailsShowVo.getShowId());
      BigDecimal showPrice = cinemaApiDetailsShowVo.getMinShowPrice();
      BigDecimal userPrice = cinemaApiDetailsShowVo.getMinUserPrice();
      if(showArea != null) {
        showPrice = showArea.getShowPrice();
        userPrice = showArea.getUserPrice();
      }
      // 置空
//      cinemaApiDetailsShowVo.setMinShowPrice(null);
//      cinemaApiDetailsShowVo.setMinUserPrice(null);
      if(showPrice == null || userPrice == null) {
        return;
      }
      // 实际销售价格
      BigDecimal price = PricingUtil.calculateActualPrice(showPrice, userPrice, pricingRules);
      // 官方价格
      cinemaApiDetailsShowVo.setMaxPrice(showPrice);
      // 实际售价
      cinemaApiDetailsShowVo.setMinPrice(price);
      // 优惠金额
      cinemaApiDetailsShowVo.setDiscount(showPrice.subtract(price));
    });
    // 赋值
    cinemaApiDetailsVo.setShowList(cinemaApiDetailsShowVoList);

    return cinemaApiDetailsVo;
  }

}
