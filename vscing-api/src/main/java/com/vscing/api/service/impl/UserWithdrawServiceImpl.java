package com.vscing.api.service.impl;

import cn.hutool.core.date.TemporalUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.api.service.UserWithdrawService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.applet.impl.wechat.extend.InitiateBillTransferResponse;
import com.vscing.common.utils.OrderUtils;
import com.vscing.model.dto.UserWithdrawApiListDto;
import com.vscing.model.entity.UserAuth;
import com.vscing.model.entity.UserWithdraw;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.mapper.UserAuthMapper;
import com.vscing.model.mapper.UserWithdrawMapper;
import com.vscing.model.request.InitiateWithdrawRequest;
import com.vscing.model.vo.TransferVo;
import com.vscing.model.vo.UserWithdrawApiListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Autowired
  private UserAuthMapper userAuthMapper;

  @Autowired
  private UserWithdrawMapper userWithdrawMapper;

  @Override
  public List<UserWithdrawApiListVo> getApilist(Long userId, UserWithdrawApiListDto queryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return userWithdrawMapper.selectApiList(userId, queryParam);
  }

  @Override
  public boolean initiateApiWithdraw(Long userId, InitiateWithdrawRequest initiateWithdrawRequest) {
    UserWithdraw userWithdraw = new UserWithdraw();
    userWithdraw.setId(IdUtil.getSnowflakeNextId());
    userWithdraw.setUserId(userId);
    userWithdraw.setPlatform(AppletTypeEnum.findByApplet(initiateWithdrawRequest.getPlatform()));
    userWithdraw.setWithdrawAmount(initiateWithdrawRequest.getWithdrawAmount());
    return userWithdrawMapper.insertInitiateWithdraw(userWithdraw) > 0;
  }

  @Override
  public TransferVo getTransfer(long id) {
    try {
      // 查询提现记录
      UserWithdraw userWithdraw = userWithdrawMapper.selectById(id);
      if (userWithdraw == null || userWithdraw.getStatus() != 2 || userWithdraw.getWithdrawStatus() != 1 || userWithdraw.getPlatform() != 1) {
        throw new ServiceException("提现记录不存在或状态异常");
      }
      // 查询用户信息
      UserAuth userAuth = userAuthMapper.findOpenid(userWithdraw.getUserId(), 1);
      if(userAuth == null) {
        throw new ServiceException("转账用户不存在");
      }
      // 声明结果
      TransferVo transferVo = new TransferVo();
      // 获取操作类
      AppletService appletService = appletServiceFactory.getAppletService(AppletTypeEnum.findByCode(1));
      Map<String, Object> transferData = new HashMap<>(5);
      transferData.put("status", 0);
      transferData.put("openid", userAuth.getOpenid());
      transferData.put("amount", userWithdraw.getWithdrawAmount());
      transferData.put("withdrawSn", userWithdraw.getWithdrawSn());
      // 使用 Hutool 的 TemporalUtil 计算两个 LocalDateTime 对象之间的时间差（毫秒）
      long betweenMs = TemporalUtil.between(userWithdraw.getUpdatedAt(), LocalDateTime.now(), ChronoUnit.MILLIS);
      // 如果存在平台号，且在一天内，则直接返回
      if (userWithdraw.getTransferNo() != null && betweenMs < 1000 * 60 * 60 * 24) {
        transferData.put("status", 1);
        transferData.put("transferNo", userWithdraw.getTransferNo());
      } else if (userWithdraw.getTransferNo() != null) {
        String withdrawSn = OrderUtils.generateOrderSn("HYT", 1);
        transferData.put("withdrawSn", withdrawSn);
        userWithdraw.setWithdrawSn(withdrawSn);
      }
      // 调用方法
      InitiateBillTransferResponse result = (InitiateBillTransferResponse)appletService.transferOrder(transferData);
      // 处理返回结果
      if (result.getState().equals("V") || result.getState().equals("WAIT_USER_CONFIRM")) {
        // 更新package_info
        userWithdraw.setTransferNo(result.getPackageInfo());
        // 输出结果
        transferVo.setAppId(result.getAppId());
        transferVo.setPackageInfo(result.getPackageInfo());
        transferVo.setMchId(result.getMchId());
      }
      // 更新提现记录
      int rowsAffected = userWithdrawMapper.updateTransfer(userWithdraw);
      log.info("转账更新提现表结果：{}", rowsAffected);
      return transferVo;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
