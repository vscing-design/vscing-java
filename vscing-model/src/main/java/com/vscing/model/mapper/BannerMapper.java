package com.vscing.model.mapper;

import com.vscing.model.dto.BannerListDto;
import com.vscing.model.entity.Banner;
import com.vscing.model.vo.BannerListVo;
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

  /**
   * 管理端查询banner列表
  */
  List<BannerListVo> getList(BannerListDto record);

  /**
   * 管理端新增banner
  */
  int insert(Banner record);

  /**
   * 管理端编辑banner
  */
  int update(Banner record);

  /**
   * api获取banner列表
  */
  List<Banner> selectAllList();

}
