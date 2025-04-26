package com.vscing.merchant.controller.v1;

import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.merchant.po.MerchantDetails;
import com.vscing.merchant.service.MerchantBillService;
import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.dto.MerchantBillRechargeDto;
import com.vscing.model.entity.MerchantBill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MerchantBillController
 *
 * @author vscing
 * @date 2025/4/19 16:32
 */
@Slf4j
@RestController
@RequestMapping("/v1/merchantBill")
@Tag(name = "商户账单接口", description = "商户账单接口")
public class MerchantBillController {

  @Autowired
  private MerchantBillService merchantBillService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<MerchantBill>> lists(@ParameterObject MerchantBillListDto queryParam,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @AuthenticationPrincipal MerchantDetails userInfo) {
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    queryParam.setMerchantId(merchantId);
    List<MerchantBill> list = merchantBillService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PutMapping
  @Operation(summary = "充值")
  public CommonResult<Object> recharge(@Validated @RequestBody MerchantBillRechargeDto record,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal MerchantDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    try {
      merchantBillService.recharge(record, userInfo.getMerchant());
      return CommonResult.success("充值成功");
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("充值失败");
    }
  }

}
