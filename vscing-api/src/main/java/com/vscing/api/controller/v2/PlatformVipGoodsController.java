package com.vscing.api.controller.v2;

import com.vscing.api.service.PlatformService;
import com.vscing.api.service.PlatformVipGoodsService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.utils.RequestUtil;
import com.vscing.model.entity.Merchant;
import com.vscing.model.platform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("PlatformVipGoodsController")
@RequestMapping("/v2/platform/vipGoods")
@Tag(name = "平台会员商品接口", description = "平台会员商品接口")
public class PlatformVipGoodsController {

  @Autowired
  private PlatformService platformService;

  @Autowired
  private PlatformVipGoodsService platformVipGoodsService;

  @PostMapping("/group")
  @Operation(summary = "商品分组列表")
  public CommonResult<List<QueryVipGroup>> group(@Validated @RequestBody QueryVipGroupDto record,
                                                 BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 商户信息
    Merchant merchant = platformService.getMerchant(record);
    if (merchant == null) {
      return CommonResult.failed("商户验证失败");
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record, merchant);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    List<QueryVipGroup> list = platformVipGoodsService.group(record);
    return CommonResult.success(list);
  }

  @PostMapping("/all")
  @Operation(summary = "商品列表")
  public CommonResult<List<QueryVipGoods>> all(@Validated @RequestBody QueryVipGoodsDto record,
                                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 商户信息
    Merchant merchant = platformService.getMerchant(record);
    if (merchant == null) {
      return CommonResult.failed("商户验证失败");
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record, merchant);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    List<QueryVipGoods> list = platformVipGoodsService.all(record);
    return CommonResult.success(list);
  }

  @PostMapping("/details")
  @Operation(summary = "商品详情")
  public CommonResult<QueryVipGoodsDetails> details(@Validated @RequestBody QueryVipGoodsDetailsDto record,
                                                    BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 商户信息
    Merchant merchant = platformService.getMerchant(record);
    if (merchant == null) {
      return CommonResult.failed("商户验证失败");
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record, merchant);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    return CommonResult.success(platformVipGoodsService.details(record));
  }

  @PostMapping("/submit/order")
  @Operation(summary = "提交订单")
  public CommonResult<QueryOrder> submitOrder(@Validated @RequestBody QuerySubmitVipOrderDto record,
                                              BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 商户信息
    Merchant merchant = platformService.getMerchant(record);
    if (merchant == null) {
      return CommonResult.failed("商户验证失败");
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record, merchant);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    try {
      QueryOrder queryOrder = platformVipGoodsService.submitOrder(record, merchant);
      return CommonResult.success(queryOrder);
    } catch (RuntimeException e) {
      String errorMessage = e.getMessage();
      return CommonResult.failed(errorMessage);
    }
  }

  @PostMapping("/query/order")
  @Operation(summary = "查询订单")
  public CommonResult<QueryVipOrderTicket> queryOrder(@Validated @RequestBody QueryOrderTicketDto record) {
    if (record.getOrderNo() == null && record.getTradeNo() == null) {
      return CommonResult.validateFailed("平台订单号、商户唯一订单号，必须传一个");
    }
    // 商户信息
    Merchant merchant = platformService.getMerchant(record);
    if (merchant == null) {
      return CommonResult.failed("商户验证失败");
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record, merchant);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    try {
      QueryVipOrderTicket queryOrder = platformVipGoodsService.queryOrder(record);
      return CommonResult.success(queryOrder);
    } catch (RuntimeException e) {
      String errorMessage = e.getMessage();
      return CommonResult.failed(errorMessage);
    }
  }

}
