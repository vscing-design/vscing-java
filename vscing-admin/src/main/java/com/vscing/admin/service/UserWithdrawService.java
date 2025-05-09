package com.vscing.admin.service;

import com.vscing.model.dto.UserWithdrawApproveDto;
import com.vscing.model.dto.UserWithdrawListDto;
import com.vscing.model.mq.TransferMq;
import com.vscing.model.vo.UserWithdrawAmountVo;
import com.vscing.model.vo.UserWithdrawListVo;

import java.util.List;

/**
 * UserWithdrawService
 *
 * @author vscing
 * @date 2025/3/2 20:52
 */
public interface UserWithdrawService {

  /**
   * 管理后台佣金提现列表
   */
  List<UserWithdrawListVo> getList(UserWithdrawListDto data, Integer pageSize, Integer pageNum);

  /**
   * 管理后台佣金提现金额统计
   */
  List<UserWithdrawAmountVo> getTotalAmount();

  /**
   * 管理后台佣金提现审核
  */
  void approve(UserWithdrawApproveDto userWithdrawApprove);

  /**
   * 管理后台佣金提现转账
  */
  void transfer(TransferMq transferMq);

}
