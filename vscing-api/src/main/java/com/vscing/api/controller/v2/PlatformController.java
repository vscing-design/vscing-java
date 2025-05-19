package com.vscing.api.controller.v2;

import com.vscing.api.service.PlatformService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.utils.RequestUtil;
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
@RestController("PlatformController")
@RequestMapping("/v2/platform")
@Tag(name = "平台接口", description = "平台接口")
public class PlatformController {

  @Autowired
  private PlatformService platformService;

  @PostMapping("/city")
  @Operation(summary = "城市列表")
  public CommonResult<List<QueryCity>> city(@Validated @RequestBody QueryCityDto record,
                                            BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    List<QueryCity> list = platformService.city(record);
    return CommonResult.success(list);
  }

  @PostMapping("/cinema")
  @Operation(summary = "影院列表")
  public CommonResult<List<QueryCinema>> cinema(@Validated @RequestBody QueryCinemaDto record,
                                                BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    List<QueryCinema> list = platformService.cinema(record);
    return CommonResult.success(list);
  }

  @PostMapping("/movie")
  @Operation(summary = "影片列表")
  public CommonResult<List<QueryMovie>> movie(@Validated @RequestBody QueryMovieDto record,
                                              BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    List<QueryMovie> list = platformService.movie(record);
    return CommonResult.success(list);
  }

  @PostMapping("/show")
  @Operation(summary = "场次列表")
  public CommonResult<QueryCinemaShow> show(@Validated @RequestBody QueryShowDto record,
                                            BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    return CommonResult.success(platformService.show(record));
  }

  @PostMapping("/seat")
  @Operation(summary = "座位图")
  public CommonResult<QuerySeat> seat(@Validated @RequestBody QuerySeatDto record,
                                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    try {
      QuerySeat querySeat = platformService.seat(record);
      return CommonResult.success(querySeat);
    } catch (RuntimeException e) {
      String errorMessage = e.getMessage();
      return CommonResult.failed(errorMessage);
    }
  }

  @PostMapping("/submit/order")
  @Operation(summary = "提交订单")
  public CommonResult<QueryOrder> submitOrder(@Validated @RequestBody QuerySubmitOrderDto record,
                                              BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    try {
      QueryOrder queryOrder = platformService.submitOrder(record);
      return CommonResult.success(queryOrder);
    } catch (RuntimeException e) {
      String errorMessage = e.getMessage();
      return CommonResult.failed(errorMessage);
    }
  }

  @PostMapping("/order/ticket")
  @Operation(summary = "查询订单")
  public CommonResult<QueryOrderTicket> orderTicket(@Validated @RequestBody QueryOrderTicketDto record) {
    if (record.getOrderNo() == null && record.getTradeNo() == null) {
      return CommonResult.validateFailed("平台订单号、商户唯一订单号，必须传一个");
    }
    // 签名校验
    String builderStr = RequestUtil.encryptBodyWithoutSign(record);
    boolean is = platformService.verify(builderStr, record);
    if(!is && !record.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    try {
      QueryOrderTicket queryOrder = platformService.orderTicket(record);
      return CommonResult.success(queryOrder);
    } catch (RuntimeException e) {
      String errorMessage = e.getMessage();
      return CommonResult.failed(errorMessage);
    }
  }

}
