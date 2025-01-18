package com.vscing.api.controller.v1;

import com.vscing.api.service.OrderService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.SeatMapVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OrderController
 *
 * @author vscing
 * @date 2025/1/12 20:01
 */
@Slf4j
@RestController
@RequestMapping("/v1/cinema")
@Tag(name = "订单接口", description = "订单接口")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/selectSeat")
  @Operation(summary = "系统选中座位列表（不包含三方选中数据）")
  public CommonResult<List<SeatListDto>> seatList(@Validated @RequestBody ShowSeatRequest data,
                                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    return CommonResult.success(orderService.seatList(data));
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
    return CommonResult.success(orderService.getSeat(data));
  }

}
