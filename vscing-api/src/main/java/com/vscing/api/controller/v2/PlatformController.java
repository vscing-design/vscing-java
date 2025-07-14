package com.vscing.api.controller.v2;

import com.github.pagehelper.PageInfo;
import com.vscing.api.service.PlatformService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.utils.RequestUtil;
import com.vscing.model.entity.Merchant;
import com.vscing.model.platform.QueryCinema;
import com.vscing.model.platform.QueryCinemaDto;
import com.vscing.model.platform.QueryCinemaShow;
import com.vscing.model.platform.QueryCity;
import com.vscing.model.platform.QueryCityDto;
import com.vscing.model.platform.QueryMovie;
import com.vscing.model.platform.QueryMovieDto;
import com.vscing.model.platform.QueryOrder;
import com.vscing.model.platform.QueryOrderTicket;
import com.vscing.model.platform.QueryOrderTicketDto;
import com.vscing.model.platform.QuerySeat;
import com.vscing.model.platform.QuerySeatDto;
import com.vscing.model.platform.QueryShowDto;
import com.vscing.model.platform.QuerySubmitOrderDto;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    List<QueryCity> list = platformService.city(record);
    return CommonResult.success(list);
  }

  @PostMapping("/cinema")
  @Operation(summary = "影院列表")
  public CommonResult<Map<String, Object>> cinema(@Validated @RequestBody QueryCinemaDto record,
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
    List<QueryCinema> list = platformService.cinema(record);
    // 返回封装
    Map<String, Object> map = new HashMap<>(2);
    // 获取总数
    PageInfo<QueryCinema> pageInfo = new PageInfo<>(list);
    long total = pageInfo.getTotal();
    // 结果返回
    map.put("list", list);
    map.put("total", total);
    return CommonResult.success(map);
  }

  @PostMapping("/movie")
  @Operation(summary = "影片列表")
  public CommonResult<Map<String, Object>> movie(@Validated @RequestBody QueryMovieDto record,
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
    List<QueryMovie> list = platformService.movie(record);
    // 返回封装
    Map<String, Object> map = new HashMap<>(2);
    // 获取总数
    PageInfo<QueryMovie> pageInfo = new PageInfo<>(list);
    long total = pageInfo.getTotal();
    // 结果返回
    map.put("list", list);
    map.put("total", total);
    return CommonResult.success(map);
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
    return CommonResult.success(platformService.show(record, merchant));
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
      QuerySeat querySeat = platformService.seat(record);
      return CommonResult.success(querySeat);
    } catch (ServiceException e) {
      return CommonResult.failed(e.getMessage());
    } catch (RuntimeException e) {
      return CommonResult.failed("服务异常，请联系平台");
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
      QueryOrder queryOrder = platformService.submitOrder(record, merchant);
      return CommonResult.success(queryOrder);
    } catch (ServiceException e) {
      return CommonResult.failed(e.getMessage());
    } catch (RuntimeException e) {
      return CommonResult.failed("服务异常，请联系平台");
    }
  }

  @PostMapping("/order/ticket")
  @Operation(summary = "查询订单")
  public CommonResult<QueryOrderTicket> orderTicket(@Validated @RequestBody QueryOrderTicketDto record) {
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
      QueryOrderTicket queryOrder = platformService.orderTicket(record);
      return CommonResult.success(queryOrder);
    } catch (ServiceException e) {
      return CommonResult.failed(e.getMessage());
    } catch (RuntimeException e) {
      return CommonResult.failed("服务异常，请联系平台");
    }
  }

}
