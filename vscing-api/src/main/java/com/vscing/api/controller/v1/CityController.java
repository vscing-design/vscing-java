package com.vscing.api.controller.v1;

import com.vscing.api.service.CityService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.CurrentCityApiDto;
import com.vscing.model.vo.AppletCityVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CityController
 *
 * @author vscing
 * @date 2025/1/11 16:40
 */
@Slf4j
@RestController
@RequestMapping("/v1/city")
@Tag(name = "城市列表接口", description = "城市列表接口")
public class CityController {

  @Autowired
  private CityService cityService;

  @GetMapping
  @Operation(summary = "城市列表")
  public CommonResult<List<AppletCityVo>> lists() {
    List<AppletCityVo> list = cityService.getAllList();
    return CommonResult.success(list);
  }

  @PostMapping
  @Operation(summary = "当前城市信息")
  public CommonResult<AppletCityVo> currentCity(@Validated @RequestBody CurrentCityApiDto requestData,
                                    BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    AppletCityVo currentCity = cityService.currentCity(requestData);
    return CommonResult.success("获取最近城市", currentCity);
  }

}
