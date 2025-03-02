package com.vscing.admin.controller.v1;

import com.vscing.admin.service.UserEarnService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.UserEarnListDto;
import com.vscing.model.vo.UserEarnListVo;
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
 * UserEarnController
 *
 * @author vscing
 * @date 2025/3/2 20:50
 */
@Slf4j
@RestController
@RequestMapping("/v1/userEarn")
@Tag(name = "推广费用明细接口", description = "推广费用明细接口")
public class UserEarnController {

  @Autowired
  UserEarnService userEarnService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<UserEarnListVo>> lists(@ParameterObject UserEarnListDto queryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<UserEarnListVo> list = userEarnService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
