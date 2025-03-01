package com.vscing.admin.controller.v1;

import com.vscing.admin.service.UploadService;
import com.vscing.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadController
 *
 * @author vscing
 * @date 2025/3/1 17:13
 */
@Slf4j
@RestController
@RequestMapping("/v1/upload")
@Tag(name = "上传文件接口", description = "上传文件接口")
public class UploadController {

  @Autowired
  UploadService uploadService;

  @PostMapping("/file")
  @Operation(summary = "上传文件")
  @Parameter(name = "file", description = "上传文件")
  @Parameter(name = "module", description = "文件目录小写英文")
  public CommonResult<String> file(@RequestParam("file") MultipartFile file,
                                   @RequestParam("module") String module) {
    if (file.isEmpty() || module == null) {
      return CommonResult.validateFailed("参数错误");
    }
    try {
      String url = uploadService.put(file, module);
      return CommonResult.success("上传成功", url);
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

}
