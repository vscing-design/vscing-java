package com.vscing.api.controller.v1;

import com.vscing.api.service.MovieService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.MovieApiCinemaDto;
import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.entity.Banner;
import com.vscing.model.entity.MovieProducer;
import com.vscing.model.vo.MovieApiCinemaVo;
import com.vscing.model.vo.MovieApiDetailsVo;
import com.vscing.model.vo.MovieApiVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public CommonResult<List<Banner>> banners() {
    List<Banner> list = movieService.getBanners();
    return CommonResult.success(list);
  }

  @GetMapping
  @Operation(summary = "首页影片列表")
  public CommonResult<CommonPage<MovieApiVo>> lists(@ParameterObject MovieApiListDto queryParam,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<MovieApiVo> list = movieService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/{id}")
  @Operation(summary = "影片详情")
  public CommonResult<MovieApiDetailsVo> details(@PathVariable("id") Long id) {
    if (id == null) {
      return CommonResult.validateFailed("参数错误");
    }
    MovieApiDetailsVo details = movieService.getDetails(id);
    if (details == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(details);
  }

  @GetMapping("/producer/{id}")
  @Operation(summary = "影片演员导演列表")
  public CommonResult<List<MovieProducer>> producer(@PathVariable("id") Long id) {
    if (id == null) {
      return CommonResult.validateFailed("参数错误");
    }
    List<MovieProducer> movieProducerList = movieService.getMovieProducerList(id);
    if (movieProducerList == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(movieProducerList);
  }

  @PostMapping("/cinema")
  @Operation(summary = "影片详情以及影院列表")
  public CommonResult<MovieApiCinemaVo> cinema(@Validated @RequestBody MovieApiCinemaDto data,
                                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    MovieApiCinemaVo details = movieService.getMovieCinemaList(data);
    if (details == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(details);
  }

}
