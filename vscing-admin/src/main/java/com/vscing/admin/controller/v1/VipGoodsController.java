package com.vscing.admin.controller.v1;

import com.alibaba.excel.EasyExcel;
import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.VipGoodsService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.AdminVipGoodsDto;
import com.vscing.model.dto.AdminVipGoodsPricingDto;
import com.vscing.model.dto.AdminVipGroupDto;
import com.vscing.model.request.AdminVipGoodsPricingRequest;
import com.vscing.model.vo.AdminVipGoodsPricingVo;
import com.vscing.model.vo.AdminVipGoodsVo;
import com.vscing.model.vo.AdminVipGroupVo;
import com.vscing.model.vo.ExcelVipGoodsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * VipGoodsController
 *
 * @author vscing
 * @date 2025/6/2 15:43
 */
@Slf4j
@RestController
@RequestMapping("/v1/vipGoods")
@Tag(name = "VIP会员卡商品管理接口", description = "VIP会员卡商品管理接口")
public class VipGoodsController {

  @Autowired
  private VipGoodsService vipGoodsService;

  @GetMapping
  @Operation(summary = "会员商品列表")
  public CommonResult<CommonPage<AdminVipGoodsVo>> lists(@ParameterObject AdminVipGoodsDto queryParam,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminVipGoodsVo> list = vipGoodsService.getGoodsList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/group")
  @Operation(summary = "会员商品分组列表")
  public CommonResult<CommonPage<AdminVipGroupVo>> groupList(@ParameterObject AdminVipGroupDto queryParam,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminVipGroupVo> list = vipGoodsService.getGroupList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/pricing")
  @Operation(summary = "会员商品定价列表")
  public CommonResult<CommonPage<AdminVipGoodsPricingVo>> pricingLists(@ParameterObject AdminVipGoodsPricingDto queryParam,
                                                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminVipGoodsPricingVo> list = vipGoodsService.getGoodsPricingList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PostMapping("/pricing")
  @Operation(summary = "会员商品定价")
  public CommonResult<Object> pricingRule(@Validated @RequestBody List<AdminVipGoodsPricingRequest> record,
                                  @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (record.isEmpty()) {
      return CommonResult.validateFailed("参数错误");
    }
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      vipGoodsService.vipGoodsPricing(record, by);
      return CommonResult.success("设置成功");
    } catch (Exception e) {
      log.error("请求异常: {}", e.getMessage());
      return CommonResult.failed("设置失败");
    }
  }

  @GetMapping("/export")
  @Operation(summary = "导出会员商品")
  public void exportGoodsToExcel(@ParameterObject AdminVipGoodsDto queryParam, HttpServletResponse response) throws Exception {
    try {
      List<ExcelVipGoodsVo> list = vipGoodsService.exportGoodsList(queryParam);

      // 设置 Excel 文件名和表格名
      String dateStr = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
      String fileName = "商品列表" + dateStr + ".xlsx";

      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

      EasyExcel.write(response.getOutputStream(), ExcelVipGoodsVo.class)
          .autoCloseStream(true)
          .sheet("商品信息")
          .doWrite(list);
    } catch (Exception e) {
      throw new Exception("导出失败：" + e.getMessage());
    }

  }

}
