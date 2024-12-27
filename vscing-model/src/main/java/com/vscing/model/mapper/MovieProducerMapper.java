package com.vscing.model.mapper;

import com.vscing.model.dto.MovieProducerListDto;
import com.vscing.model.entity.MovieProducer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MovieProducerMapper
 *
 * @author vscing
 * @date 2024/12/27 22:10
 */
@Mapper
public interface MovieProducerMapper {

  List<MovieProducer> getList(MovieProducerListDto record);

  MovieProducer selectById(long id);

  int insert(MovieProducer record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(MovieProducer record);

}
