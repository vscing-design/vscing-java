package com.vscing.admin.service;

import com.vscing.model.dto.UserEarnListDto;
import com.vscing.model.vo.UserEarnListVo;

import java.util.List;

/**
 * UserEarnService
 *
 * @author vscing
 * @date 2025/3/2 23:23
 */
public interface UserEarnService {

  /**
   * 管理后台推广费用明细列表
   */
  List<UserEarnListVo> getList(UserEarnListDto data, Integer pageSize, Integer pageNum);

}
