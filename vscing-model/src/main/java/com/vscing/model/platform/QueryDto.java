package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QueryDto {

  @NotNull(message = "商户编码未传入")
  @Schema(description = "商户编码")
  private Long userId;

  @NotNull(message = "13位毫秒级时间戳未传入")
  @Schema(description = "13位毫秒级时间戳")
  private Long timestamp;

  @NotNull(message = "签名未传入")
  @Schema(description = "签名")
  private String sign;

}
