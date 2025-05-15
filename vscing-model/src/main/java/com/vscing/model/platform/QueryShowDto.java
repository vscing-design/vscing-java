package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryShowDto extends QueryDto {

  @NotNull(message = "影院id未传入")
  @Schema(description = "影院id")
  private Long cinemaId;

  @Schema(description = "截止时间")
  private LocalDateTime endTime;

}

