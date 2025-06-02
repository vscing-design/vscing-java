package com.vscing.admin.controller.v1;

import com.vscing.admin.service.VipGoodsService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.AdminVipGoodsDto;
import com.vscing.model.dto.AdminVipGroupDto;
import com.vscing.model.vo.AdminVipGoodsVo;
import com.vscing.model.vo.AdminVipGroupVo;
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
 * VipGoodsController
 *
 * @author vscing
 * @date 2025/6/2 15:43
 */
@Slf4j
@RestController
@RequestMapping("/v1/vipGoods")
@Tag(name = "商品管理接口", description = "商品管理接口")
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

}
