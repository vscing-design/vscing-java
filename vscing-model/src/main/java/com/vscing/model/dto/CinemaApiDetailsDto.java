package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * CinemaApiDetailsDto
 *
 * @author vscing
 * @date 2025/1/18 02:32
 */
@Data
public class CinemaApiDetailsDto {

  @NotNull(message = "影院Id不能为空")
  @Schema(description = "影院Id")
  private Long id;

  @NotNull(message = "纬度不能为空")
  @Schema(description = "纬度")
  private Double lat;

  @NotNull(message = "经度不能为空")
  @Schema(description = "经度")
  private Double lng;

}
