package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * MovieTopDto
 *
 * @author vscing
 * @date 2025/3/1 15:50
 */
@Data
public class MovieTopDto {
  @NotNull(message = "影片ID不能为空")
  @Schema(description = "影片ID")
  private Long id;

  @NotNull(message = "置顶不能为空")
  @Schema(description = "置顶 1-5")
  private Integer top;

  @NotNull(message = "上映类型不能为空")
  @Schema(description = "上映类型，HOT为热映，WAIT为待上映")
  private String publishStatus;

  @Schema(description = "更新者ID")
  private Long updatedBy;

}
