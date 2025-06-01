package com.vscing.admin.controller.v1;

import com.vscing.admin.service.VipSyncService;
import com.vscing.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * VipSyncController
 *
 * @author vscing
 * @date 2025/6/1 16:42
 */
@Slf4j
@RestController
@RequestMapping("/v1/vipSync")
@Tag(name = "vip会员卡信息同步接口", description = "vip会员卡信息同步接口")
public class VipSyncController {

  @Autowired
  private VipSyncService vipSyncService;

  @GetMapping("/group")
  @Operation(summary = "获取分组数据")
  public CommonResult<Object> group() {
    vipSyncService.queryGroup();
    return CommonResult.success();
  }

  @GetMapping("/goods")
  @Operation(summary = "获取所有商品数据")
  public CommonResult<Object> goods() {
    vipSyncService.queryGoods(1);
    return CommonResult.success();
  }

}
