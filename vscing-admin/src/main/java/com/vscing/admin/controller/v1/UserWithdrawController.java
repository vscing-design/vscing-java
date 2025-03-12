package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.UserWithdrawService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.UserWithdrawApproveDto;
import com.vscing.model.dto.UserWithdrawListDto;
import com.vscing.model.vo.UserWithdrawAmountVo;
import com.vscing.model.vo.UserWithdrawListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
 * UserWithdrawController
 *
 * @author vscing
 * @date 2025/3/2 20:51
 */
@Slf4j
@RestController
@RequestMapping("/v1/userWithdraw")
@Tag(name = "佣金提现审核接口", description = "佣金提现审核接口")
public class UserWithdrawController {

  @Autowired
  UserWithdrawService userWithdrawService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<UserWithdrawListVo>> lists(@ParameterObject UserWithdrawListDto queryParam,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<UserWithdrawListVo> list = userWithdrawService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/amount")
  @Operation(summary = "金额统计")
  public CommonResult<List<UserWithdrawAmountVo>> amount() {
    return CommonResult.success(userWithdrawService.getTotalAmount());
  }

  @PostMapping("/approve")
  @Operation(summary = "审核")
  public CommonResult<Object> approve(@Validated @RequestBody UserWithdrawApproveDto userWithdrawApprove,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal AdminUserDetails userInfo) {
    // 获取第一个错误信息，如果需要所有错误信息
    if (bindingResult.hasErrors()) {
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    userWithdrawApprove.setUpdatedBy(by);
    try {
      userWithdrawService.approve(userWithdrawApprove);
      return CommonResult.success("审核成功");
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

}
