package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * MovieProducer
 *
 * @author vscing
 * @date 2024/12/27 20:42
 */
@Getter
@Setter
public class MovieProducer extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "类型 1-导演 2-演员")
  private Integer type;

  @Schema(description = "导演/演员图片")
  private String avatar;

  @Schema(description = "导演/演员英文名称")
  private String enName;

  @Schema(description = "导演/演员中文名称")
  private String scName;

  @Schema(description = "演员角色名称")
  private String actName;

}
