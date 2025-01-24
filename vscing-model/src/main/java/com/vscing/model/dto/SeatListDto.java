package com.vscing.model.dto;

import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * seatListDto
 *
 * @author vscing
 * @date 2024/12/31 01:44
 */
@Data
@AutoMappers({
    @AutoMapper(target = ShowInforDto.class)
})
public class SeatListDto {

  @Schema(description = "区域ID")
  private String areaId;

  @NotNull(message = "座位ID不能为空")
  @Schema(description = "座位ID")
  private String seatId;

  @NotNull(message = "座位名称不能为空")
  @Schema(description = "座位名称")
  private String seatName;

}
