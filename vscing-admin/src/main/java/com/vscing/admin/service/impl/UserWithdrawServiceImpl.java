package com.vscing.admin.service.impl;

import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.UserWithdrawService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.OrderUtils;
import com.vscing.model.dto.UserWithdrawApproveDto;
import com.vscing.model.dto.UserWithdrawListDto;
import com.vscing.model.entity.UserAuth;
import com.vscing.model.entity.UserWithdraw;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.mapper.UserAuthMapper;
import com.vscing.model.mapper.UserMapper;
import com.vscing.model.mapper.UserWithdrawMapper;
import com.vscing.model.mq.TransferMq;
import com.vscing.model.vo.UserWithdrawAmountVo;
import com.vscing.model.vo.UserWithdrawListVo;
import com.vscing.mq.config.FanoutRabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserWithdrawServiceImpl
 *
 * @author vscing
 * @date 2025/3/2 20:53
 */
@Slf4j
@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {

  @Autowired
  private UserWithdrawMapper userWithdrawMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserAuthMapper userAuthMapper;

  @Autowired
  private RabbitMQService rabbitMQService;

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Override
  public List<UserWithdrawListVo> getList(UserWithdrawListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return userWithdrawMapper.getList(data);
  }

  @Override
  public List<UserWithdrawAmountVo> getTotalAmount() {
    return userWithdrawMapper.getUserWithdrawAmount();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void approve(UserWithdrawApproveDto data) {
    try {
      UserWithdraw userWithdraw = userWithdrawMapper.selectById(data.getId());
      int status = data.getStatus();
      String withdrawSn = OrderUtils.generateOrderSn("HY-TX", 1);
      if(status == 2) {
        data.setWithdrawSn(withdrawSn);
        data.setWithdrawStatus(1);
      }
      // 改变提现状态
      int rowsAffected = userWithdrawMapper.approve(data);
      if (rowsAffected <= 0) {
        throw new ServiceException("删除用户失败");
      }
      if (status == 2) {
        // 更新用户余额
        rowsAffected = userMapper.updateReduceAmount(userWithdraw.getUserId(),userWithdraw.getWithdrawAmount());
        if (rowsAffected <= 0) {
          throw new ServiceException("更新用户余额失败");
        }
        if (userWithdraw.getPlatform() == 2) {
          // 支付宝发起转账、微信手动触发转账
          TransferMq transferMq = new TransferMq();
          transferMq.setUserId(userWithdraw.getUserId());
          transferMq.setPlatform(userWithdraw.getPlatform());
          transferMq.setAmount(userWithdraw.getWithdrawAmount());
          transferMq.setWithdrawSn(withdrawSn);
          String msg = JsonUtils.toJsonString(transferMq);
          rabbitMQService.sendFanoutMessage(FanoutRabbitMQConfig.TRANSFER_ROUTING_KEY, msg);
        }
      }
    } catch (Exception e) {
      log.error("提现请求异常：", e);
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public void transfer(TransferMq transferMq) {
    try {
      UserAuth userAuth = userAuthMapper.findOpenid(transferMq.getUserId(), transferMq.getPlatform());
      if(userAuth == null) {
        throw new ServiceException("转账用户不存在");
      }
      int platform = transferMq.getPlatform();
      if (platform == 2) {
        throw new ServiceException("转账平台错误");
      }
      AppletService appletService = appletServiceFactory.getAppletService(AppletTypeEnum.findByCode(platform));
      Map<String, Object> transferData = new HashMap<>(3);
      transferData.put("openid", userAuth.getOpenid());
      transferData.put("amount", transferMq.getAmount());
      transferData.put("withdrawSn", transferMq.getWithdrawSn());
      AlipayFundTransUniTransferResponse response = (AlipayFundTransUniTransferResponse) appletService.transferOrder(transferData);
      UserWithdraw userWithdraw = userWithdrawMapper.selectByWithdrawSn(transferMq.getWithdrawSn());
      // 默认打款失败
      userWithdraw.setWithdrawStatus(3);
      if (response.getStatus() != null && response.getStatus().equals("SUCCESS")) {
        userWithdraw.setWithdrawStatus(2);
        userWithdraw.setTransferNo(response.getOrderId());
      }
      log.info("转账更新类 userWithdraw: {}", userWithdraw);
      int rowsAffected = userWithdrawMapper.updateTransfer(userWithdraw);
      if (rowsAffected <= 0) {
        throw new ServiceException("转账更新提现表失败");
      }
    } catch (Exception e) {
      log.error("转账请求异常：", e);
      throw new ServiceException(e.getMessage());
    }
  }

}
