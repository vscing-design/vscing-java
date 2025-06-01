package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * VipGoodsAttach
 *
 * @author vscing
 * @date 2025/6/1 19:07
 */
@Getter
@Setter
public class VipGoodsAttach extends BaseEntity {

  @Schema(description = "主键 ID")
  private Long id;

  @Schema(description = "VIP 商品 ID")
  private Long vipGoodsId;

  @Schema(description = "充值字段标题")
  private String title;

  @Schema(description = "充值字段提示")
  private String tip;

}
