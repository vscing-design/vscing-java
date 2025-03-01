package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.MovieService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.MovieListDto;
import com.vscing.model.entity.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MovieController
 *
 * @author vscing
 * @date 2024/12/26 22:22
 */
@Slf4j
@RestController
@RequestMapping("/v1/movie")
@Tag(name = "影片接口", description = "影片接口")
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<Movie>> lists(@ParameterObject MovieListDto queryParam,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<Movie> list = movieService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PutMapping("/top")
  @Operation(summary = "置顶")
  public CommonResult<Object> updatedTop(@Validated @RequestBody Movie movie,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if (movie.getId() == null || movie.getTop() == null) {
      return CommonResult.validateFailed("参数错误");
    }
    if(userInfo != null) {
      // 操作人ID
      movie.setUpdatedBy(userInfo.getUserId());
    }
    try {
      boolean result = movieService.updatedTop(movie);
      if (result) {
        return CommonResult.success("编辑成功");
      } else {
        return CommonResult.failed("编辑失败");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }


}
