package com.vscing.admin.controller.v1;

import com.vscing.admin.service.OrderService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.OrderListDto;
import com.vscing.model.request.OrderSaveRequest;
import com.vscing.model.vo.OrderVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OrderController
 *
 * @author vscing
 * @date 2024/12/31 01:28
 */
@Slf4j
@RestController
@RequestMapping("/v1/order")
@Tag(name = "订单接口", description = "订单接口")
public class OrderController {

  @Autowired
  OrderService orderService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<OrderVo>> lists(OrderListDto queryParam,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<OrderVo> list = orderService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PostMapping
  @Operation(summary = "手动下单")
  public CommonResult<Object> add(@Validated @RequestBody OrderSaveRequest orderSave,
                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }

//    orderService.createOrder(orderSave);
    return CommonResult.failed("下单逻辑处理中");
//    try {
//      boolean res = roleService.createdMenuList(roleMenus);
//      if (res) {
//        return CommonResult.success("下单成功");
//      }
//      return CommonResult.failed("下单失败");
//    } catch (Exception e) {
//      log.error("请求错误: ", e);
//      return CommonResult.failed("请求错误");
//    }
  }


}
