package com.vscing.admin.service;

import com.vscing.model.dto.BannerListDto;
import com.vscing.model.entity.Banner;
import com.vscing.model.vo.BannerListVo;

import java.util.List;

/**
 * BannerService
 *
 * @author vscing
 * @date 2024/12/26 22:18
 */
public interface BannerService {

  /**
   * 列表
   */
  List<BannerListVo> getList(BannerListDto data, Integer pageSize, Integer pageNum);

  /**
   * 插入
   */
  int created(Banner data);

  /**
   * 插入
   */
  int updated(Banner data);

}
