package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * BannerListVo
 *
 * @author vscing
 * @date 2025/3/1 16:38
 */
@Data
public class BannerListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "banner图url")
  private String img;

  @Schema(description = "banner图状态 1 上架 2 下架")
  private Integer status;

  @Schema(description = "banner排序")
  private Integer sort;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

}
