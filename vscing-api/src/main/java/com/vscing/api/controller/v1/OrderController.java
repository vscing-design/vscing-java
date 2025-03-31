package com.vscing.api.controller.v1;

import com.vscing.api.po.UserDetails;
import com.vscing.api.service.OrderService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.OrderApiConfirmDetailsDto;
import com.vscing.model.dto.OrderApiCreatedDto;
import com.vscing.model.dto.OrderApiListDto;
import com.vscing.model.dto.OrderApiScoreDto;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.OrderApiConfirmDetailsVo;
import com.vscing.model.vo.OrderApiDetailsVo;
import com.vscing.model.vo.OrderApiListVo;
import com.vscing.model.vo.OrderApiPaymentVo;
import com.vscing.model.vo.SeatMapVo;
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
 * @date 2025/1/12 20:01
 */
@Slf4j
@RestController
@RequestMapping("/v1/order")
@Tag(name = "订单接口", description = "订单接口")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/selectSeat")
  @Operation(summary = "系统选中座位列表（不包含三方选中数据）")
  public CommonResult<List<SeatListDto>> seatList(@Validated @RequestBody ShowSeatRequest data,
                                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    return CommonResult.success(orderService.seatList(data));
  }

  @PostMapping("/seat")
  @Operation(summary = "座位列表和最多选择多少个")
  public CommonResult<SeatMapVo> seat(@Validated @RequestBody ShowSeatRequest data,
                                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    return CommonResult.success(orderService.getSeat(data));
  }

  @PostMapping("/details")
  @Operation(summary = "获取订单详情")
  public CommonResult<OrderApiConfirmDetailsVo> details(@Validated @RequestBody OrderApiConfirmDetailsDto orderApiConfirmDetailsDto,
                                                        BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    try {
      boolean is = orderService.verifyOrderSeat(orderApiConfirmDetailsDto);
      if(is) {
        return CommonResult.failed("当前选中座位已被选中");
      }
      return CommonResult.success(orderService.getConfirmDetails(orderApiConfirmDetailsDto));
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/create")
  @Operation(summary = "创建订单返回支付参数")
  public CommonResult<OrderApiPaymentVo> create(@Validated @RequestBody OrderApiCreatedDto orderApiCreatedDto,
                                                BindingResult bindingResult,
                                                @AuthenticationPrincipal UserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    try {
      OrderApiConfirmDetailsDto orderApiConfirmDetailsDto = new OrderApiConfirmDetailsDto();
      orderApiConfirmDetailsDto.setShowId(orderApiCreatedDto.getShowId());
      orderApiConfirmDetailsDto.setSeatList(orderApiCreatedDto.getSeatList());
      boolean is = orderService.verifyOrderSeat(orderApiConfirmDetailsDto);
      if(is) {
        return CommonResult.failed("当前选中座位已被选中");
      }
      return CommonResult.success(orderService.create(userInfo.getUserId(), orderApiCreatedDto));
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<OrderApiListVo>> lists(@ParameterObject OrderApiListDto queryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @AuthenticationPrincipal UserDetails userInfo) {
    List<OrderApiListVo> list = orderService.getList(userInfo.getUserId(), queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/{id}")
  @Operation(summary = "详情")
  public CommonResult<OrderApiDetailsVo> details(@PathVariable("id") Long id,
                                                 @AuthenticationPrincipal UserDetails userInfo) {
    OrderApiDetailsVo orderSave = orderService.getDetails(userInfo.getUserId(), id);
    if (orderSave == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(orderSave);
  }

  @PostMapping("/delete/{id}")
  @Operation(summary = "删除订单")
  public CommonResult<Object> delete(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal UserDetails userInfo) {
    try {
      boolean res = orderService.deleteOrder(userInfo.getUserId(), id);
      if (res) {
        return CommonResult.success("删除成功");
      } else {
        return CommonResult.failed("删除失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/cancel/{id}")
  @Operation(summary = "取消订单")
  public CommonResult<Object> cancel(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal UserDetails userInfo) {
    try {
      boolean res = orderService.cancelOrder(userInfo.getUserId(), id);
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

  @PostMapping("/payment/{id}")
  @Operation(summary = "去支付")
  public CommonResult<OrderApiPaymentVo> payment(@PathVariable("id") Long id,
                                                 @AuthenticationPrincipal UserDetails userInfo) {
    try {
      return CommonResult.success(orderService.paymentOrder(userInfo.getUserId(), id));
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/score")
  @Operation(summary = "评分")
  public CommonResult<Object> ticket(@Validated @RequestBody OrderApiScoreDto orderApiScoreDto,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal UserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    try {
      boolean res = orderService.insertScoreOrder(userInfo.getUserId(), orderApiScoreDto);
      if (res) {
        return CommonResult.success("评分成功");
      } else {
        return CommonResult.failed("评分失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

}
