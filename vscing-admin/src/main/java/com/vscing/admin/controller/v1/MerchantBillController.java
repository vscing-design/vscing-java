package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.MerchantBillService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.dto.MerchantBillRechargeListDto;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.vo.MerchantBillRechargeListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MerchantBillController
 *
 * @author vscing
 * @date 2025/4/19 16:32
 */
@Slf4j
@RestController
@RequestMapping("/v1/merchantBill")
@Tag(name = "商户账单接口", description = "商户账单接口")
public class MerchantBillController {

  @Autowired
  private MerchantBillService merchantBillService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<MerchantBill>> lists(@ParameterObject MerchantBillListDto queryParam,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<MerchantBill> list = merchantBillService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/recharge")
  @Operation(summary = "充值列表")
  public CommonResult<CommonPage<MerchantBillRechargeListVo>> rechargeLists(@ParameterObject MerchantBillRechargeListDto queryParam,
                                                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<MerchantBillRechargeListVo> list = merchantBillService.getRechargeList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PutMapping
  @Operation(summary = "审批编辑")
  public CommonResult<Object> save(@Validated @RequestBody MerchantBill record,
                                   @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (record.getId() == null) {
      return CommonResult.validateFailed("ID不能为空");
    }
    if (record.getMerchantId() == null) {
      return CommonResult.validateFailed("商户ID不能为空");
    }
    if (record.getStatus() == null) {
      return CommonResult.validateFailed("状态不能为空");
    }
    if(userInfo != null) {
      // 操作人ID
      record.setUpdatedBy(userInfo.getUserId());
    }
    try {
      int rowsAffected = merchantBillService.updated(record);
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

}
