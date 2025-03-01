package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MovieService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.MovieListDto;
import com.vscing.model.entity.Movie;
import com.vscing.model.entity.MovieProducer;
import com.vscing.model.mapper.MovieMapper;
import com.vscing.model.mapper.MovieProducerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MovieServiceImpl
 *
 * @author vscing
 * @date 2024/12/28 19:19
 */
@Service
public class MovieServiceImpl implements MovieService {

  @Autowired
  private MovieMapper movieMapper;

  @Autowired
  private MovieProducerMapper movieProducerMapper;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean initMovie(List<Movie> movieList, List<MovieProducer> movieProducerList) {
    try {
      int rowsAffected;
      if(!movieList.isEmpty()) {
        // 创建
        rowsAffected = movieMapper.batchUpsert(movieList);
        if (rowsAffected <= 0) {
          throw new ServiceException("新增失败");
        }
      }
      if(!movieProducerList.isEmpty()) {
        // 增加机构
        rowsAffected = movieProducerMapper.batchUpsert(movieProducerList);
        if (rowsAffected <= 0) {
          throw new ServiceException("新增关联失败");
        }
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  public List<Movie> getList(MovieListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return movieMapper.getList(data);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean updatedTop(Movie data) {
    try {
      int rowsAffected;
      movieMapper.deletedTop(data.getTop());
      rowsAffected = movieMapper.updatedTop(data);
      if (rowsAffected <= 0) {
        throw new ServiceException("置顶失败");
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

}
