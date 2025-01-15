package com.vscing.api.service.impl;

import com.vscing.api.service.MovieService;
import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.mapper.MovieMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.vo.MovieApiVo;
import com.vscing.model.vo.MovieBannersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MovieServiceImpl
 *
 * @author vscing
 * @date 2025/1/13 10:44
 */
@Service
public class MovieServiceImpl implements MovieService {

  @Autowired
  private MovieMapper movieMapper;

  @Autowired
  private ShowMapper showMapper;

  public List<MovieBannersVo> getBanners() {
    return movieMapper.selectBanners();
  }

  public List<MovieApiVo> getList(MovieApiListDto data) {

    List<MovieApiVo> list = showMapper.selectByMovieApiList(data);

    return list;
  }

}
