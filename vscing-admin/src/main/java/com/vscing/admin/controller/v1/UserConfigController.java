package com.vscing.admin.controller.v1;

import com.vscing.admin.service.UserConfigService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.entity.UserConfig;
import com.vscing.model.vo.UserConfigPricingRuleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserConfigController
 *
 * @author vscing
 * @date 2025/3/2 20:48
 */
@Slf4j
@RestController
@RequestMapping("/v1/userConfig")
@Tag(name = "推广费用规则接口", description = "推广费用规则接口")
public class UserConfigController {

  @Autowired
  UserConfigService userConfigService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<List<UserConfig>> lists() {
    List<UserConfig> list = userConfigService.selectAllList();
    return CommonResult.success(list);
  }

  @PutMapping
  @Operation(summary = "更新配置")
  public CommonResult<String> save(@RequestBody List<UserConfig> list) {
    if (list.isEmpty()) {
      return CommonResult.validateFailed("参数错误");
    }
    try {
      boolean result = userConfigService.batchUpsert(list);
      if (!result) {
        return CommonResult.failed("编辑失败");
      } else {
        return CommonResult.success("编辑成功");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @GetMapping("/pricingRule")
  @Operation(summary = "规则列表")
  public CommonResult<List<UserConfigPricingRuleVo>> pricingRule() {
    List<UserConfigPricingRuleVo> list = userConfigService.pricingRule();
    return CommonResult.success(list);
  }

}
