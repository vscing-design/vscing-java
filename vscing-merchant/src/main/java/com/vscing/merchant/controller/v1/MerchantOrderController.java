package com.vscing.merchant.controller.v1;

import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.merchant.po.MerchantDetails;
import com.vscing.merchant.service.MerchantOrderService;
import com.vscing.model.dto.MerchantOrderCountDto;
import com.vscing.model.dto.MerchantOrderListDto;
import com.vscing.model.dto.MerchantVipOrderCountDto;
import com.vscing.model.dto.MerchantVipOrderListDto;
import com.vscing.model.vo.MerchantOrderCountVo;
import com.vscing.model.vo.MerchantOrderListVo;
import com.vscing.model.vo.MerchantVipOrderCountVo;
import com.vscing.model.vo.MerchantVipOrderListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MerchantController
 *
 * @author vscing
 * @date 2025/4/19 00:26
 */
@Slf4j
@RestController
@RequestMapping("/v1/merchantOrder")
@Tag(name = "商户订单接口", description = "商户订单接口")
public class MerchantOrderController {

  @Autowired
  private MerchantOrderService merchantOrderService;

  @GetMapping
  @Operation(summary = "电影票订单列表")
  public CommonResult<CommonPage<MerchantOrderListVo>> lists(@ParameterObject MerchantOrderListDto queryParam,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @AuthenticationPrincipal MerchantDetails userInfo) {
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    queryParam.setMerchantId(merchantId);
    List<MerchantOrderListVo> list = merchantOrderService.getOrderList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/count")
  @Operation(summary = "电影票订单统计")
  public CommonResult<CommonPage<MerchantOrderCountVo>> count(@ParameterObject MerchantOrderCountDto queryParam,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                              @AuthenticationPrincipal MerchantDetails userInfo) {
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    queryParam.setMerchantId(merchantId);
    List<MerchantOrderCountVo> list = merchantOrderService.getCountList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/vip")
  @Operation(summary = "会员卡商品订单列表")
  public CommonResult<CommonPage<MerchantVipOrderListVo>> vipLists(@ParameterObject MerchantVipOrderListDto queryParam,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @AuthenticationPrincipal MerchantDetails userInfo) {
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    queryParam.setMerchantId(merchantId);
    List<MerchantVipOrderListVo> list = merchantOrderService.getVipOrderList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/vipCount")
  @Operation(summary = "会员卡商品订单统计")
  public CommonResult<CommonPage<MerchantVipOrderCountVo>> vipCount(@ParameterObject MerchantVipOrderCountDto queryParam,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                    @AuthenticationPrincipal MerchantDetails userInfo) {
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    queryParam.setMerchantId(merchantId);
    List<MerchantVipOrderCountVo> list = merchantOrderService.getVipCountList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
