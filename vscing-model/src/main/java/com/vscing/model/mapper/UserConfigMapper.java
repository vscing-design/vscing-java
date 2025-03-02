package com.vscing.model.mapper;

import com.vscing.model.entity.UserConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserConfigMapper
 *
 * @author vscing
 * @date 2025/3/2 22:01
 */
@Mapper
public interface UserConfigMapper {

  /**
   * 管理端查询所有配置信息
  */
  List<UserConfig> selectAllList();

  /**
   * 管理端更新所有配置信息
  */
  int batchUpsert(@Param("list") List<UserConfig> list);

}
