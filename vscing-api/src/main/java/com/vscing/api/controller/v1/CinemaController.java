package com.vscing.api.controller.v1;

import com.vscing.api.service.CinemaService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.CinemaApiDetailsDto;
import com.vscing.model.dto.CinemaApiDistrictDto;
import com.vscing.model.dto.CinemaApiListDto;
import com.vscing.model.vo.CinemaApiDetailsVo;
import com.vscing.model.vo.CinemaApiDistrictVo;
import com.vscing.model.vo.CinemaApiVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CinemaController
 *
 * @author vscing
 * @date 2025/1/12 19:58
 */
@Slf4j
@RestController
@RequestMapping("/v1/cinema")
@Tag(name = "影院列表接口", description = "影院列表接口")
public class CinemaController {

  @Autowired
  private CinemaService cinemaService;

  @PostMapping("/district")
  @Operation(summary = "影院筛选区县")
  public CommonResult<List<CinemaApiDistrictVo>> district(@Validated @RequestBody CinemaApiDistrictDto data,
                                                          BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    List<CinemaApiDistrictVo> list = cinemaService.getDistrictList(data);
    return CommonResult.success(list);
  }

  @GetMapping
  @Operation(summary = "影院列表")
  public CommonResult<CommonPage<CinemaApiVo>> lists(@ParameterObject CinemaApiListDto queryParam,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<CinemaApiVo> list = cinemaService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PostMapping("/details")
  @Operation(summary = "影院详情")
  public CommonResult<CinemaApiDetailsVo> details(@Validated @RequestBody CinemaApiDetailsDto data,
                                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    CinemaApiDetailsVo details = cinemaService.getDetails(data);
    if (details == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(details);
  }

}
