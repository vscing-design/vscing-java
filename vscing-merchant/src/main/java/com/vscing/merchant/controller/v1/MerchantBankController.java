package com.vscing.merchant.controller.v1;

import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.merchant.po.MerchantDetails;
import com.vscing.merchant.service.MerchantBankService;
import com.vscing.model.entity.MerchantBank;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MerchantBankController
 *
 * @author vscing
 * @date 2025/4/19 16:24
 */
@Slf4j
@RestController
@RequestMapping("/v1/merchantBank")
@Tag(name = "商户对公户接口", description = "商户对公户接口")
public class MerchantBankController {

  @Autowired
  private MerchantBankService merchantBankService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<MerchantBank>> lists(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @AuthenticationPrincipal MerchantDetails userInfo) {
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    List<MerchantBank> list = merchantBankService.getBankList(merchantId, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
