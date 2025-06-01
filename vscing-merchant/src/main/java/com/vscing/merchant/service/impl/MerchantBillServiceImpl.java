package com.vscing.merchant.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.common.exception.ServiceException;
import com.vscing.merchant.service.MerchantBillService;
import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.dto.MerchantBillRechargeDto;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.mapper.MerchantBillMapper;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.vo.MerchantDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * MerchantBillServiceImpl
 *
 * @author vscing
 * @date 2025/4/26 00:35
 */
@Service
public class MerchantBillServiceImpl implements MerchantBillService {

  @Autowired
  private MerchantMapper merchantMapper;

  @Autowired
  private MerchantBillMapper merchantBillMapper;

  @Override
  public List<MerchantBill> getList(MerchantBillListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantBillMapper.getList(record);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public void recharge(MerchantBillRechargeDto record, MerchantDetailVo merchant) {
    try {
      int rowsAffected;
      BigDecimal newBalance = merchant.getBalance().add(record.getChangeAmount());
      MerchantBill merchantBill = new MerchantBill();
      merchantBill.setId(IdUtil.getSnowflakeNextId());
      merchantBill.setMerchantId(merchant.getId());
      merchantBill.setBankId(record.getBankId());
      merchantBill.setChangeType(3);
      BigDecimal changeAmount = record.getChangeAmount();
      merchantBill.setChangeAmount(changeAmount);
      merchantBill.setChangeAfterBalance(newBalance);
      merchantBill.setStatus(1);
      merchantBill.setBranchName(record.getBranchName());
      merchantBill.setBankAccount(record.getBankAccount());
      rowsAffected = merchantBillMapper.insert(merchantBill);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建商户账单失败");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
