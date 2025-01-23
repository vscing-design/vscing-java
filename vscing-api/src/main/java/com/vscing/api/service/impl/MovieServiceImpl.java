package com.vscing.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.api.service.MovieService;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.MovieApiCinemaDto;
import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.entity.Banner;
import com.vscing.model.entity.Movie;
import com.vscing.model.entity.MovieProducer;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.mapper.BannerMapper;
import com.vscing.model.mapper.MovieMapper;
import com.vscing.model.mapper.MovieProducerMapper;
import com.vscing.model.mapper.PricingRuleMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.utils.PricingUtil;
import com.vscing.model.vo.MovieApiCinemaVo;
import com.vscing.model.vo.MovieApiDetailsVo;
import com.vscing.model.vo.MovieApiVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
  private BannerMapper bannerMapper;

  @Autowired
  private MovieProducerMapper movieProducerMapper;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  @Override
  public List<Banner> getBanners() {
    // 获取轮播图
    return bannerMapper.selectAllList();
  }

  @Override
  public List<MovieApiVo> getList(MovieApiListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<MovieApiVo> movieApiVoList = showMapper.selectByMovieApiList(data);

    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());

    // 循环计算实际售价
    movieApiVoList.forEach(movieApiVo -> {
      // 实际销售价格
      BigDecimal price = PricingUtil.calculateActualPrice(movieApiVo.getMinShowPrice(), movieApiVo.getMinUserPrice(), pricingRules);
      // 实际售价
      movieApiVo.setMinPrice(price);
      // 重置其他数据
      movieApiVo.setMinShowPrice(null);
      movieApiVo.setMinUserPrice(null);
    });

    return movieApiVoList;
  }

  @Override
  public MovieApiDetailsVo getDetails(Long id) {
    // 获取影片的详情
    Movie movie = movieMapper.selectById(id);
    MovieApiDetailsVo movieApiDetailsVo = MapstructUtils.convert(movie, MovieApiDetailsVo.class);
    // 根据影片ID获取导演、演员列表
    if (movieApiDetailsVo != null) {
      movieApiDetailsVo.setMovieProducerList(movieProducerMapper.selectByMovieId(id));
    }
    return movieApiDetailsVo;
  }

  @Override
  public List<MovieProducer> getMovieProducerList(Long id) {
    // 根据影片ID获取导演、演员列表
    return movieProducerMapper.selectByMovieId(id);
  }

  @Override
  public MovieApiCinemaVo getMovieCinemaList(MovieApiCinemaDto data) {
    // 获取影片的详情
    Movie movie = movieMapper.selectById(data.getMovieId());
    MovieApiCinemaVo movieApiCinemaVo = MapstructUtils.convert(movie, MovieApiCinemaVo.class);
    // 获取影院列表
    if (movieApiCinemaVo != null) {
      movieApiCinemaVo.setCinemaList(showMapper.selectByMovieApiCinema(data));
    }
    return movieApiCinemaVo;
  }

}
