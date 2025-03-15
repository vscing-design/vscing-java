package com.vscing.model.mapper;

import com.vscing.model.dto.UserWithdrawApiListDto;
import com.vscing.model.dto.UserWithdrawApproveDto;
import com.vscing.model.dto.UserWithdrawListDto;
import com.vscing.model.entity.UserWithdraw;
import com.vscing.model.vo.UserWithdrawAmountVo;
import com.vscing.model.vo.UserWithdrawApiListVo;
import com.vscing.model.vo.UserWithdrawListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserWithdrawMapper
 *
 * @author vscing
 * @date 2025/3/2 17:05
 */
@Mapper
public interface UserWithdrawMapper {

  /**
   * 管理后台佣金提现详情
  */
  UserWithdraw selectById(Long id);

  /**
   * 管理后台佣金提现列表
   */
  List<UserWithdrawListVo> getList(UserWithdrawListDto record);

  /**
   * 管理后台佣金提现列表总数
   */
  List<UserWithdrawAmountVo> getUserWithdrawAmount();

  /**
   * 管理后台佣金提现审核
   */
  int approve(UserWithdrawApproveDto record);

  /**
   * 公用佣金提现详情
   */
  UserWithdraw selectByWithdrawSn(@Param("withdrawSn") String withdrawSn);

  /**
   * 公用修改提现信息
  */
  int updateTransfer(UserWithdraw userWithdraw);

  /**
   * 用户佣金提现列表查询
   * @param userId 用户ID
   * @param record 筛选参数
   */
  List<UserWithdrawApiListVo> selectApiList(@Param("userId") long userId, @Param("record") UserWithdrawApiListDto record);

  /**
   * 用户发起提现
   * @param record 入参
   */
  int insertInitiateWithdraw(UserWithdraw record);

}
