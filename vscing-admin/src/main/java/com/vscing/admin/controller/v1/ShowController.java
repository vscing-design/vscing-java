package com.vscing.admin.controller.v1;

import com.vscing.admin.service.ShowService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.ShowAllDto;
import com.vscing.model.dto.ShowListDto;
import com.vscing.model.request.SeatPriceRequest;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.MovieTreeVo;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.model.vo.SeatPriceMapVo;
import com.vscing.model.vo.ShowListVo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ShowController
 *
 * @author vscing
 * @date 2024/12/29 00:21
 */
@Slf4j
@RestController
@RequestMapping("/v1/show")
@Tag(name = "场次列表接口", description = "场次列表接口")
public class ShowController {

  @Autowired
  ShowService showService;

  @GetMapping
  @Operation(summary = "分页列表")
  public CommonResult<CommonPage<ShowListVo>> lists(ShowListDto queryParam,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<ShowListVo> list = showService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/all")
  @Operation(summary = "手动下单筛选项")
  public CommonResult<List<MovieTreeVo>> all(ShowAllDto queryParam) {
    return CommonResult.success(showService.getAll(queryParam));
  }

  @PostMapping("/seat")
  @Operation(summary = "座位列表和最多选择多少个")
  public CommonResult<SeatMapVo> seat(@Validated @RequestBody ShowSeatRequest data,
                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    return CommonResult.success(showService.getSeat(data));
  }

  @PostMapping("/price")
  @Operation(summary = "选中座位价格统计")
  public CommonResult<SeatPriceMapVo> price(@Validated @RequestBody SeatPriceRequest data,
                                            BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if(data.getSeatNum() != data.getAreaIds().size()) {
      return CommonResult.validateFailed("选中座位数量与选中区域数量不一致");
    }
    return CommonResult.success(showService.getSeatPrice(data));
  }



}
