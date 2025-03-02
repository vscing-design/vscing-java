package com.vscing.model.mapper;

import com.vscing.model.dto.UserEarnListDto;
import com.vscing.model.vo.UserEarnListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * UserEarnMapper
 *
 * @author vscing
 * @date 2025/3/2 17:05
 */
@Mapper
public interface UserEarnMapper {

  /**
   * 管理后台推广费用明细
   */
  List<UserEarnListVo> getList(UserEarnListDto record);

}
