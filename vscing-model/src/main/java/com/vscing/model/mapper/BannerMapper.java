package com.vscing.model.mapper;

import com.vscing.model.entity.Banner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BannerMapper
 *
 * @author vscing
 * @date 2025/1/23 23:36
 */
@Mapper
public interface BannerMapper {

  List<Banner> selectAllList();

}
