package com.vscing.api.controller.v1;

import com.vscing.api.po.UserDetails;
import com.vscing.api.service.UserWithdrawService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.UserWithdrawApiListDto;
import com.vscing.model.request.InitiateWithdrawRequest;
import com.vscing.model.vo.TransferVo;
import com.vscing.model.vo.UserAmountVo;
import com.vscing.model.vo.UserWithdrawAmountVo;
import com.vscing.model.vo.UserWithdrawApiListVo;
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
 * UserWithdrawController
 *
 * @author vscing
 * @date 2025/3/7 01:11
 */
@Slf4j
@RestController
@RequestMapping("/v1/userWithdraw")
@Tag(name = "佣金提现接口", description = "佣金提现接口")
public class UserWithdrawController {

  @Autowired
  UserWithdrawService userWithdrawService;

  @GetMapping
  @Operation(summary = "用户提现列表")
  public CommonResult<CommonPage<UserWithdrawApiListVo>> lists(@ParameterObject UserWithdrawApiListDto queryParam,
                                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                               @AuthenticationPrincipal UserDetails userInfo) {
    List<UserWithdrawApiListVo> list = userWithdrawService.getApilist(userInfo.getUserId(), queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/amount")
  @Operation(summary = "用户提现总金额")
  public CommonResult<UserAmountVo> amount(@AuthenticationPrincipal UserDetails userInfo) {
    UserAmountVo userAmountVo = userWithdrawService.getTotalAmount(userInfo);
    return CommonResult.success(userAmountVo);
  }

  @PostMapping("/withdraw")
  @Operation(summary = "用户发起提现")
  public CommonResult<List<UserWithdrawAmountVo>> withdraw(@Validated @RequestBody InitiateWithdrawRequest initiateWithdrawRequest,
                                                           BindingResult bindingResult,
                                                           @AuthenticationPrincipal UserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    try {
      boolean res = userWithdrawService.initiateApiWithdraw(userInfo.getUserId(), initiateWithdrawRequest);
      if (res) {
        return CommonResult.success("提现操作成功");
      } else {
        return CommonResult.failed("提现操作失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @GetMapping("/withdraw/{id}")
  @Operation(summary = "用户提现确认")
  public CommonResult<TransferVo> withdrawConfirm(@PathVariable("id") Long id) {
    if (id == null) {
      return CommonResult.validateFailed("参数错误");
    }
    try {
      return CommonResult.success(userWithdrawService.getTransfer(id));
    } catch (Exception e) {
      log.error("请求错误: {}", e.getMessage());
      throw new RuntimeException("请求错误");
    }
  }

  @GetMapping("/withdrawFail/{id}")
  @Operation(summary = "用户确认异常")
  public CommonResult<Boolean> withdrawFail(@PathVariable("id") Long id) {
    if (id == null) {
      return CommonResult.validateFailed("参数错误");
    }
    try {
      return CommonResult.success(userWithdrawService.transferFail(id));
    } catch (Exception e) {
      log.error("请求错误: {}", e.getMessage());
      throw new RuntimeException("请求错误");
    }
  }

}
