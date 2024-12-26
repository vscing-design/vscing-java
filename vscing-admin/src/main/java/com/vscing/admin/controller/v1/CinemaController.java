package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.CinemaService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.CinemaListDto;
import com.vscing.model.entity.Cinema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CinemaController
 *
 * @author vscing
 * @date 2024/12/26 22:22
 */
@Slf4j
@RestController
@RequestMapping("/v1/cinema")
@Tag(name = "影院接口", description = "影院接口")
public class CinemaController {

  @Autowired
  private CinemaService cinemaService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<Cinema>> lists(CinemaListDto queryParam,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<Cinema> list = cinemaService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/{id}")
  @Operation(summary = "详情")
  public CommonResult<Cinema> details(@PathVariable("id") Long id) {
    Cinema cinema = cinemaService.getDetails(id);
    if (cinema == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(cinema);
  }

  @PostMapping
  @Operation(summary = "新增")
  public CommonResult<Object> add(@Validated @RequestBody Cinema cinema,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    if(userInfo != null) {
      // 操作人ID
      cinema.setCreatedBy(userInfo.getUserId());
    }
    try {
      long id = cinemaService.created(cinema);
      if (id == 0) {
        return CommonResult.failed("新增失败");
      } else {
        return CommonResult.success("新增成功");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PutMapping
  @Operation(summary = "编辑")
  public CommonResult<Object> save(@Validated @RequestBody Cinema cinema,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (cinema.getId() == null) {
      return CommonResult.validateFailed("ID不能为空");
    }
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if(userInfo != null) {
      // 操作人ID
      cinema.setUpdatedBy(userInfo.getUserId());
    }
    try {
      long id = cinemaService.updated(cinema);
      if (id == 0) {
        return CommonResult.failed("编辑失败");
      } else {
        return CommonResult.success("编辑成功");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除")
  public CommonResult<Object> delete(@PathVariable Long id, @AuthenticationPrincipal AdminUserDetails userInfo) {
    if(id == null) {
      return CommonResult.validateFailed("参数错误");
    }
    long result = cinemaService.deleted(id, userInfo.getUserId());
    if (result != 0) {
      return CommonResult.success("删除成功");
    } else {
      return CommonResult.failed("删除失败");
    }
  }


}
