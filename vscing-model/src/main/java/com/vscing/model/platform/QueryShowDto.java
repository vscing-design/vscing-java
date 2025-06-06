package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryShowDto extends QueryDto {

  @Schema(description = "当前页，默认1")
  private Integer pageNum = 1;

  @Max(value = 50, message = "单页数量最大值为50")
  @Schema(description = "单页数量，默认10，最大50")
  private Integer pageSize = 10;

  @NotNull(message = "影院id未传入")
  @Schema(description = "影院id")
  private Long cinemaId;

  @Schema(description = "截止时间")
  private LocalDateTime endTime;

}

