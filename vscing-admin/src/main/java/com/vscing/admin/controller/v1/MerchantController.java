package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.MerchantService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.MerchantListDto;
import com.vscing.model.entity.Menu;
import com.vscing.model.entity.Merchant;
import com.vscing.model.request.MerchantRefundRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MerchantController
 *
 * @author vscing
 * @date 2025/4/19 00:26
 */
@Slf4j
@RestController
@RequestMapping("/v1/merchant")
@Tag(name = "商户接口", description = "商户接口")
public class MerchantController {

  @Autowired
  private MerchantService merchantService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<Merchant>> lists(@ParameterObject MerchantListDto queryParam,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<Merchant> list = merchantService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/{id}")
  @Operation(summary = "详情")
  public CommonResult<Merchant> details(@PathVariable("id") Long id) {
    Merchant merchant = merchantService.getDetails(id);
    if (merchant == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(merchant);
  }

  @PostMapping
  @Operation(summary = "新增")
  public CommonResult<Object> add(@Validated @RequestBody Merchant merchant,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    if(userInfo != null) {
      // 操作人ID
      merchant.setCreatedBy(userInfo.getUserId());
    }
    try {
      int rowsAffected = merchantService.created(merchant);
      if (rowsAffected <= 0) {
        return CommonResult.failed("新增失败");
      } else {
        return CommonResult.success("新增成功");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PutMapping
  @Operation(summary = "编辑")
  public CommonResult<Object> save(@Validated @RequestBody Merchant merchant,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (merchant.getId() == null) {
      return CommonResult.validateFailed("ID不能为空");
    }
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if(userInfo != null) {
      // 操作人ID
      merchant.setUpdatedBy(userInfo.getUserId());
    }
    try {
      int rowsAffected = merchantService.updated(merchant);
      if (rowsAffected <= 0) {
        return CommonResult.failed("编辑失败");
      } else {
        return CommonResult.success("编辑成功");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PostMapping("/refund")
  @Operation(summary = "退款")
  public CommonResult<Object> refund(@Validated @RequestBody MerchantRefundRequest record,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    if(userInfo != null && userInfo.getUserId() != null) {
      record.setUpdatedBy(userInfo.getUserId());
    }
    try {
      merchantService.refund(record);
      return CommonResult.success("退款成功");
    } catch (Exception e) {
      log.error("退款失败: {}", e.getMessage());
      return CommonResult.failed("退款失败");
    }
  }

}
