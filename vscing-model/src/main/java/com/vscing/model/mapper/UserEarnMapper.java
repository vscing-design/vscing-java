package com.vscing.model.mapper;

import com.vscing.model.dto.UserEarnApiInviteDto;
import com.vscing.model.dto.UserEarnApiListDto;
import com.vscing.model.dto.UserEarnListDto;
import com.vscing.model.entity.UserEarn;
import com.vscing.model.vo.UserEarnApiInviteNoticeVo;
import com.vscing.model.vo.UserEarnApiInviteVo;
import com.vscing.model.vo.UserEarnApiListVo;
import com.vscing.model.vo.UserEarnListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

  /**
   * 用户收益明细列表
   * @param userId 用户ID
   * @param record 筛选参数
   */
  List<UserEarnApiListVo> selectApiList(@Param("userId") long userId, @Param("record") UserEarnApiListDto record);

  /**
   * 用户邀请列表
   * @param userId 用户ID
   * @param record 筛选参数
   */
  List<UserEarnApiInviteVo> selectApiInvite(@Param("userId") long userId, @Param("record") UserEarnApiInviteDto record);

  /**
   * 插入收益记录
  */
  int insert(UserEarn record);

  /**
   * 邀请列表通知
  */
  List<UserEarnApiInviteNoticeVo> selectApiInviteNotice();

}
