package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MerchantBillService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.dto.MerchantBillRechargeListDto;
import com.vscing.model.entity.Merchant;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.mapper.MerchantBillMapper;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.vo.MerchantBillRechargeListVo;
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
 * @date 2025/4/19 16:34
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
  public List<MerchantBillRechargeListVo> getRechargeList(MerchantBillRechargeListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantBillMapper.getRechargeList(record);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public int updated(MerchantBill record) {
    try {
      Merchant merchant = merchantMapper.selectById(record.getMerchantId());
      if (merchant == null) {
        throw new ServiceException("商户不存在");
      }
      MerchantBill merchantBill = merchantBillMapper.selectById(record.getId());
      if(merchantBill == null) {
        throw new ServiceException("商户账单不存在");
      }
      int rowsAffected;
      // 充值成功
      if(record.getStatus() == 2){
        // 变更商户余额
        BigDecimal newBalance = merchant.getBalance().add(merchantBill.getChangeAmount());
        merchant.setBalance(newBalance);
        merchant.setVersion(merchant.getVersion());
        rowsAffected = merchantMapper.updateVersion(merchant);
        if (rowsAffected <= 0) {
          throw new ServiceException("变更商户余额失败");
        }
      }
      rowsAffected = merchantBillMapper.update(record);
      if (rowsAffected <= 0) {
        throw new ServiceException("变更商户账单失败");
      }
      return rowsAffected;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
