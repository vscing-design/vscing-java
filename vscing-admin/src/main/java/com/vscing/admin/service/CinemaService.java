package com.vscing.admin.service;

import com.vscing.model.dto.CinemaListDto;
import com.vscing.model.entity.Cinema;

import java.util.List;

/**
 * CinemaService
 *
 * @author vscing
 * @date 2024/12/26 22:18
 */
public interface CinemaService {

  /**
   * 列表
   */
  List<Cinema> getList(CinemaListDto data, Integer pageSize, Integer pageNum);

  /**
   * 详情
   */
  Cinema getDetails(long id);

  /**
   * 新增
   */
  long created(Cinema data);

  /**
   * 编辑
   */
  long updated(Cinema data);

  /**
   * 删除
   */
  long deleted(long id, long deleterId);
  
}
