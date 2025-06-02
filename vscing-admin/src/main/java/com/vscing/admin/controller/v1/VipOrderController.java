package com.vscing.admin.controller.v1;

import com.vscing.admin.service.VipOrderService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.AdminVipOrderDto;
import com.vscing.model.vo.AdminVipOrderVo;
import com.vscing.model.vo.OrderPriceVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * VipOrderController
 *
 * @author vscing
 * @date 2025/6/2 17:29
 */
@Slf4j
@RestController
@RequestMapping("/v1/vipOrder")
@Tag(name = "商品订单接口", description = "商品订单接口")
public class VipOrderController {

  @Autowired
  private VipOrderService vipOrderService;

  @GetMapping
  @Operation(summary = "会员卡商品订单列表")
  public CommonResult<CommonPage<AdminVipOrderVo>> lists(@ParameterObject AdminVipOrderDto queryParam,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminVipOrderVo> list = vipOrderService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/amount")
  @Operation(summary = "会员卡商品金额统计")
  public CommonResult<OrderPriceVo> amount(@ParameterObject AdminVipOrderDto queryParam) {
    return CommonResult.success(vipOrderService.getCountAmount(queryParam));
  }

}
