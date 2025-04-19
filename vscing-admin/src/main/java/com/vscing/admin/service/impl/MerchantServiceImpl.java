package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MerchantService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.MerchantListDto;
import com.vscing.model.entity.Merchant;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.mapper.MerchantPriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
  private MerchantMapper merchantMapper;

  @Autowired
  private MerchantPriceMapper merchantPriceMapper;

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
}
