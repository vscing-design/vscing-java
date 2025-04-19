package com.vscing.admin.controller.v1;

import com.vscing.admin.service.MerchantBillService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.entity.MerchantBill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
