package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.VipOrderService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.AdminVipOrderDto;
import com.vscing.model.entity.Merchant;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.entity.VipOrder;
import com.vscing.model.mapper.MerchantBillMapper;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.mapper.VipOrderMapper;
import com.vscing.model.vo.AdminVipOrderVo;
import com.vscing.model.vo.OrderPriceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * VipOrderServiceImpl
 *
 * @author vscing
 * @date 2025/6/2 17:39
 */
@Service
public class VipOrderServiceImpl implements VipOrderService {

  @Autowired
  private VipOrderMapper vipOrderMapper;

  @Autowired
  private MerchantMapper merchantMapper;

  @Autowired
  private MerchantBillMapper merchantBillMapper;

  @Override
  public List<AdminVipOrderVo> getList(AdminVipOrderDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return vipOrderMapper.getAdminList(record);
  }

  @Override
  public OrderPriceVo getCountAmount(AdminVipOrderDto record) {
    return vipOrderMapper.getCountAmount(record);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public void closeOrder(Long id, Long by) {
    try {
      int rowsAffected = 0;
      // 根据订单id获取订单
      VipOrder vipOrder = vipOrderMapper.selectById(id);
      if(vipOrder == null || vipOrder.getMerchantId() == null || vipOrder.getStatus() > 3) {
        throw new ServiceException("订单数据异常");
      }
      // 根据商户id查询商户信息
      Merchant merchant = merchantMapper.selectById(vipOrder.getMerchantId());
      // 修改订单状态到取消
      VipOrder updateVipOrder = new VipOrder();
      updateVipOrder.setId(vipOrder.getId());
      updateVipOrder.setStatus(4);
      rowsAffected = vipOrderMapper.update(updateVipOrder);
      if (rowsAffected <= 0) {
        throw new ServiceException("改变订单状态失败");
      }
      // 计算商户余额
      BigDecimal merchantBalance = merchant.getBalance();
      merchantBalance = merchantBalance.add(vipOrder.getTotalPrice());
      // 创建商户账单
      MerchantBill merchantBill = new MerchantBill();
      merchantBill.setId(IdUtil.getSnowflakeNextId());
      merchantBill.setMerchantId(vipOrder.getMerchantId());
      merchantBill.setPlatformOrderNo(vipOrder.getOrderSn());
      merchantBill.setExternalOrderNo(vipOrder.getExtOrderSn());
      merchantBill.setChangeAmount(vipOrder.getTotalPrice());
      merchantBill.setChangeAfterBalance(merchantBalance);
      merchantBill.setStatus(2);
      merchantBill.setChangeType(2);
      merchantBill.setProductType(2);
      rowsAffected = merchantBillMapper.insert(merchantBill);
      if (rowsAffected < 0) {
        throw new ServiceException("创建商户账单失败");
      }
      // 商户余额修改
      merchant.setBalance(merchantBalance);
      merchant.setVersion(merchant.getVersion());
      rowsAffected = merchantMapper.updateVersion(merchant);
      if (rowsAffected < 0) {
        throw new ServiceException("商户扣款失败");
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }
}
