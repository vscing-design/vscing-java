package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Banner
 *
 * @author vscing
 * @date 2025/1/23 23:33
 */
@Getter
@Setter
public class Banner extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "banner图url")
  private String img;

  @Schema(description = "banner图状态 1正常 2禁用")
  private Integer status;

}
