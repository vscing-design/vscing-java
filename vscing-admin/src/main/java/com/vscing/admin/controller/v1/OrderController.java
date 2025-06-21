package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.OrderService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.OrderListDto;
import com.vscing.model.request.OrderChangeRequest;
import com.vscing.model.request.OrderSaveRequest;
import com.vscing.model.vo.OrderDetailVo;
import com.vscing.model.vo.OrderPriceVo;
import com.vscing.model.vo.OrderVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public CommonResult<CommonPage<OrderVo>> lists(@ParameterObject OrderListDto queryParam,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<OrderVo> list = orderService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/{id}")
  @Operation(summary = "详情")
  public CommonResult<OrderDetailVo> details(@PathVariable("id") Long id) {
    OrderDetailVo orderSave = orderService.getDetails(id);
    if (orderSave == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(orderSave);
  }

  @GetMapping("/amount")
  @Operation(summary = "金额统计")
  public CommonResult<OrderPriceVo> amount(@ParameterObject OrderListDto queryParam) {
    return CommonResult.success(orderService.getOrderPrice(queryParam));
  }

  @PostMapping
  @Operation(summary = "手动下单")
  public CommonResult<Object> add(@Validated @RequestBody OrderSaveRequest orderSave,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      boolean is = orderService.verifyOrderSeat(orderSave);
      if(is) {
        return CommonResult.failed("当前选中座位已被选中");
      }
      boolean res = orderService.createOrder(orderSave, by);
      if(res) {
        return CommonResult.success("下单成功");
      }
      return CommonResult.failed("下单失败");
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/cancel/{id}")
  @Operation(summary = "取消订单")
  public CommonResult<Object> cancel(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal AdminUserDetails userInfo) {
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      boolean res = orderService.cancelOrder(id, by);
      if (res) {
        return CommonResult.success("取消成功");
      } else {
        return CommonResult.failed("取消失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/refund/{id}")
  @Operation(summary = "退款")
  public CommonResult<Object> refund(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal AdminUserDetails userInfo) {
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      boolean res = orderService.refundOrder(id, by);
      if (res) {
        return CommonResult.success("退款成功");
      } else {
        return CommonResult.failed("退款失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/ticket/{id}")
  @Operation(summary = "出票")
  public CommonResult<Object> ticket(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal AdminUserDetails userInfo) {
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      boolean res = orderService.ticketOrder(id, by);
      if (res) {
        return CommonResult.success("取票接口返回成功");
      } else {
        return CommonResult.failed("取票接口返回失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/change/{id}")
  @Operation(summary = "调座")
  public CommonResult<Object> exchange(@Validated @RequestBody OrderChangeRequest data,
                                       BindingResult bindingResult,
                                       @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      boolean res = orderService.changeOrder(data, by);
      if (res) {
        return CommonResult.success("调座接口返回成功");
      } else {
        return CommonResult.failed("调座接口返回失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/close/{id}")
  @Operation(summary = "订单关闭-仅支持商户订单")
  public CommonResult<Object> close(@PathVariable("id") Long id,
                                    @AuthenticationPrincipal AdminUserDetails userInfo) {
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      orderService.closeOrder(id, by);
      return CommonResult.success("关闭成功");
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("关闭失败");
    }
  }



}
