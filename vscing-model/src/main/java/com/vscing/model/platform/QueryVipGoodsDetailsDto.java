package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryVipGoodsDetailsDto extends QueryDto {

  @NotNull(message = "商品ID未传入")
  @Schema(description = "商品ID")
  private Long goodsId;

}
