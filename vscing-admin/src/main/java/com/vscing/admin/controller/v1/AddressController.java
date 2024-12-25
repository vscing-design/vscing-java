package com.vscing.admin.controller.v1;

import com.vscing.admin.service.AddressService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.vo.ProvinceVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AddressController
 *
 * @author vscing
 * @date 2024/12/23 00:00
 */
@Slf4j
@RestController
@RequestMapping("/v1/address")
@Tag(name = "地址库接口", description = "地址库接口")
public class AddressController {

  @Autowired
  private AddressService addressService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<List<ProvinceVo>> lists() {
    List<ProvinceVo> list = addressService.getList();
    return CommonResult.success(list);
  }

}
