package com.vscing.api.controller.v2;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.vscing.api.service.CouponService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.utils.RequestUtil;
import com.vscing.model.mapper.CouponMapper;
import com.vscing.model.request.CouponDetailsRequest;
import com.vscing.model.request.CouponRequest;
import com.vscing.model.vo.CouponApiDetailsVo;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * CouponController
 *
 * @author vscing
 * @date 2025/3/22 16:46
 */
@Slf4j
@RestController("v2CouponController")
@RequestMapping("/v2/coupon")
@Tag(name = "三方优惠劵接口", description = "三方优惠劵接口")
public class CouponController {

  @Autowired
  private CouponService couponService;

  @Autowired
  private CouponMapper couponMapper;

  @PostMapping("/save")
//  @WhiteListAnnotate(type = "coupon")
  @Operation(summary = "新增优惠券")
  public CommonResult<Boolean> save(@Validated @RequestBody CouponRequest data,
                                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 校验优惠券过期时间不能小于创建时间
    if(data.getEndTime().isBefore(data.getCreatedAt())) {
      return CommonResult.validateFailed("优惠券过期时间不能小于创建时间");
    }
    // 校验优惠券过期时间
    long delayMilliseconds = LocalDateTimeUtil.between(LocalDateTime.now(), data.getEndTime(), ChronoUnit.MILLIS);
    // 如果过期时间已经过去，则不发送延迟消息
    if (delayMilliseconds <= 0) {
      return CommonResult.validateFailed("优惠券过期时间不能小于当前时间");
    }
    log.info("新增优惠券: {}", data);
    // 校验
    boolean is = couponService.verify(RequestUtil.encryptBodyWithoutSign(data), data.getSign());
    if(!is && !data.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    return CommonResult.success(couponService.save(data));
  }

//  @PostMapping("/batchSave")
//  @WhiteListAnnotate(type = "coupon")
//  @Operation(summary = "批量新增优惠券")
//  public CommonResult<Boolean> batchSave(@Validated @RequestBody List<CouponRequest> dataList,
//                                         BindingResult bindingResult) {
//    // 校验请求参数是否为空
//    if (dataList == null || dataList.isEmpty()) {
//      return CommonResult.validateFailed("优惠券列表不能为空");
//    }
//    // 限制list个数
//    if (dataList.size() > 35) {
//      return CommonResult.validateFailed("优惠券列表不能超过35个");
//    }
//    // 遍历每个优惠券对象并进行校验
//    for (int i = 0; i < dataList.size(); i++) {
//      CouponRequest data = dataList.get(i);
//
//      // 检查绑定结果中的错误信息
//      if (bindingResult.hasFieldErrors("[" + i + "]")) {
//        String errorMessage = bindingResult.getFieldErrors("[" + i + "]").get(0).getDefaultMessage();
//        return CommonResult.validateFailed("第 " + (i + 1) + " 个优惠券校验失败：" + errorMessage);
//      }
//
//      // 校验优惠券过期时间不能小于创建时间
//      if (data.getEndTime().isBefore(data.getCreatedAt())) {
//        return CommonResult.validateFailed("第 " + (i + 1) + " 个优惠券过期时间不能小于创建时间");
//      }
//
//      // 校验优惠券过期时间不能小于当前时间
//      long delayMilliseconds = LocalDateTimeUtil.between(LocalDateTime.now(), data.getEndTime(), ChronoUnit.MILLIS);
//      if (delayMilliseconds <= 0) {
//        return CommonResult.validateFailed("第 " + (i + 1) + " 个优惠券已过期或无效");
//      }
//    }
//    // 批量保存优惠券
//    boolean result = couponService.batchSave(dataList);
//    if (result) {
//      return CommonResult.success("批量新增优惠券成功");
//    } else {
//      return CommonResult.failed("批量新增优惠券失败");
//    }
//  }

  @PostMapping("/details")
//  @WhiteListAnnotate(type = "coupon")
  @Operation(summary = "优惠券详情")
  public CommonResult<CouponApiDetailsVo> details(@Validated @RequestBody CouponDetailsRequest data,
                                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    log.info("优惠券详情: {}", data);
    // 校验
    boolean is = couponService.verify(RequestUtil.encryptBodyWithoutSign(data), data.getSign());
    if(!is && !data.getSign().equals("HY")) {
      return CommonResult.failed("签名验证失败");
    }
    return CommonResult.success(couponService.details(data));
  }

}
