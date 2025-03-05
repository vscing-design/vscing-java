package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * BannerListDto
 *
 * @author vscing
 * @date 2025/3/1 16:58
 */
@Data
public class BannerListDto {

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "banner图状态 0 全部 1 上架 2 下架")
  private Integer status;

}
