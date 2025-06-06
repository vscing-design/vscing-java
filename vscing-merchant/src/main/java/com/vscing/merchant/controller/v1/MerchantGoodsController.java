package com.vscing.merchant.controller.v1;

import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.merchant.po.MerchantDetails;
import com.vscing.merchant.service.MerchantGoodsService;
import com.vscing.model.dto.MerchantGoodsListDto;
import com.vscing.model.vo.MerchantGoodsListVo;
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
 * MerchantGoodsController
 *
 * @author vscing
 * @date 2025/6/2 21:44
 */
@Slf4j
@RestController
@RequestMapping("/v1/merchantGoods")
@Tag(name = "商户商品接口", description = "商户商品接口")
public class MerchantGoodsController {

  @Autowired
  private MerchantGoodsService merchantGoodsService;

  @GetMapping("/vip")
  @Operation(summary = "会员卡商品列表")
  public CommonResult<CommonPage<MerchantGoodsListVo>> vipLists(@ParameterObject MerchantGoodsListDto queryParam,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @AuthenticationPrincipal MerchantDetails userInfo) {
    // 获取商户Id
    Long merchantId = userInfo.getUserId();
    if(merchantId == null) {
      return CommonResult.unauthorized("商户不存在");
    }
    queryParam.setMerchantId(merchantId);
    List<MerchantGoodsListVo> list = merchantGoodsService.getVipGoodsList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
