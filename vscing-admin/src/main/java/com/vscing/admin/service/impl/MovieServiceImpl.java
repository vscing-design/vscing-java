package com.vscing.admin.service.impl;

import com.vscing.admin.service.MovieService;
import com.vscing.common.exception.ServiceException;
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
  public boolean initMovie(Movie movie, List<MovieProducer> movieProducerList) {
    try {

      // 创建
      int rowsAffected = movieMapper.insert(movie);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增失败");
      }
      // 增加机构
      if(movieProducerList != null && !movieProducerList.isEmpty()) {
        rowsAffected = movieProducerMapper.batchInsert(movieProducerList);
        if (rowsAffected != movieProducerList.size()) {
          throw new ServiceException("新增关联失败");
        }
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

}
