package com.vscing.api.controller.v1;

import com.vscing.api.po.UserDetails;
import com.vscing.api.service.UserEarnService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.UserEarnApiInviteDto;
import com.vscing.model.dto.UserEarnApiListDto;
import com.vscing.model.vo.UserEarnApiInviteNoticeVo;
import com.vscing.model.vo.UserEarnApiInviteVo;
import com.vscing.model.vo.UserEarnApiListVo;
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
 * UserEarnController
 *
 * @author vscing
 * @date 2025/3/7 01:10
 */
@Slf4j
@RestController
@RequestMapping("/v1/userEarn")
@Tag(name = "推广费用明细接口", description = "推广费用明细接口")
public class UserEarnController {

  @Autowired
  UserEarnService userEarnService;

  @GetMapping
  @Operation(summary = "收益明细列表")
  public CommonResult<CommonPage<UserEarnApiListVo>> lists(@ParameterObject UserEarnApiListDto queryParam,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @AuthenticationPrincipal UserDetails userInfo) {
    List<UserEarnApiListVo> list = userEarnService.getApilist(userInfo.getUserId(), queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/inviteNotice")
  @Operation(summary = "邀请通知列表")
  public CommonResult<List<UserEarnApiInviteNoticeVo>> inviteNotice() {
    List<UserEarnApiInviteNoticeVo> list = userEarnService.getApiInviteNotice();
    return CommonResult.success(list);
  }

  @GetMapping("/invite")
  @Operation(summary = "邀请列表")
  public CommonResult<CommonPage<UserEarnApiInviteVo>> invite(@ParameterObject UserEarnApiInviteDto queryParam,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                              @AuthenticationPrincipal UserDetails userInfo) {
    List<UserEarnApiInviteVo> list = userEarnService.getApiInvite(userInfo.getUserId(), queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
