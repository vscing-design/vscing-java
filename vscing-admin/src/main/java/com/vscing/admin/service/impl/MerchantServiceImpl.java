package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MerchantService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.MerchantListDto;
import com.vscing.model.entity.Merchant;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.mapper.MerchantBillMapper;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.mapper.MerchantPriceMapper;
import com.vscing.model.request.MerchantRefundRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * MerchantServiceImpl
 *
 * @author vscing
 * @date 2025/4/19 00:27
 */
@Service
public class MerchantServiceImpl implements MerchantService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MerchantMapper merchantMapper;

  @Autowired
  private MerchantPriceMapper merchantPriceMapper;

  @Autowired
  private MerchantBillMapper merchantBillMapper;

  @Override
  public List<Merchant> getList(MerchantListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantMapper.getList(record);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public int created(Merchant merchant) {
    try {
      int rowsAffected = 1;
      merchant.setId(IdUtil.getSnowflakeNextId());
      merchant.setPassword(passwordEncoder.encode(merchant.getPassword()));
      rowsAffected = merchantMapper.insert(merchant);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建商户失败");
      }
      MerchantPrice merchantPrice = new MerchantPrice();
      merchantPrice.setId(IdUtil.getSnowflakeNextId());
      merchantPrice.setMerchantId(merchant.getId());
      merchantPrice.setSupplierId(1869799230973227008L);
      merchantPrice.setMarkupAmount(BigDecimal.ZERO);
      rowsAffected = merchantPriceMapper.insert(merchantPrice);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建商户价格失败");
      }
      return rowsAffected;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int updated(Merchant merchant) {
    return merchantMapper.update(merchant);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public int refund(MerchantRefundRequest record) {
    try {
      Merchant merchant = merchantMapper.selectById(record.getId());
      if (merchant == null) {
        throw new ServiceException("商户不存在");
      }
      if (merchant.getBalance().compareTo(record.getRefundAmount()) < 0) {
        throw new ServiceException("商户余额不够");
      }
      int rowsAffected = 1;
      // 变更商户余额
      BigDecimal newBalance = merchant.getBalance().subtract(record.getRefundAmount());
      merchant.setBalance(newBalance);
      rowsAffected = merchantMapper.update(merchant);
      if (rowsAffected <= 0) {
        throw new ServiceException("变更商户余额失败");
      }
      MerchantBill merchantBill = new MerchantBill();
      merchantBill.setId(IdUtil.getSnowflakeNextId());
      merchantBill.setMerchantId(merchant.getId());
      merchantBill.setChangeType(1);
      merchantBill.setChangeAmount(record.getRefundAmount());
      merchantBill.setChangeAfterBalance(newBalance);
      merchantBill.setStatus(2);
      merchantBill.setPictureVoucher(record.getPictureVoucher());
      merchantBill.setRemark(record.getRemark());
      rowsAffected = merchantBillMapper.insert(merchantBill);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建商户账单失败");
      }
      return rowsAffected;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
