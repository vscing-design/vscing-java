package com.vscing.admin.controller.v1;

import com.vscing.admin.service.ShowService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.ShowListDto;
import com.vscing.model.vo.ShowListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
  @Operation(summary = "列表")
  public CommonResult<CommonPage<ShowListVo>> lists(ShowListDto queryParam,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<ShowListVo> list = showService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
