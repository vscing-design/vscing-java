package com.vscing.api.controller.v1;

import com.vscing.api.service.MovieService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.vo.MovieApiVo;
import com.vscing.model.vo.MovieBannersVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MovieController
 *
 * @author vscing
 * @date 2025/1/12 19:58
 */
@Slf4j
@RestController
@RequestMapping("/v1/movie")
@Tag(name = "影片列表接口", description = "影片列表接口")
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping("/banners")
  @Operation(summary = "获取影片轮播图")
  public CommonResult<List<MovieBannersVo>> banners() {
    List<MovieBannersVo> list = movieService.getBanners();
    return CommonResult.success(list);
  }

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<MovieApiVo>> lists(@ParameterObject MovieApiListDto queryParam) {
    List<MovieApiVo> list = movieService.getList(queryParam);
    return CommonResult.success(CommonPage.restPage(list));
  }



}
