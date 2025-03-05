package com.vscing.model.mapper;

import com.vscing.model.dto.UserWithdrawApproveDto;
import com.vscing.model.dto.UserWithdrawListDto;
import com.vscing.model.vo.UserWithdrawAmountVo;
import com.vscing.model.vo.UserWithdrawListVo;
import org.apache.ibatis.annotations.Mapper;

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

}
