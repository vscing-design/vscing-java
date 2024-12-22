package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Supplier
 *
 * @author vscing
 * @date 2024/12/20 00:37
 */
@Getter
@Setter
public class Supplier extends BaseEntity {

  @Schema(description = "用户id")
  private Long id;

  @NotNull(message = "供应商名称不能为空")
  @Schema(description = "供应商名称")
  private String name;

  @NotNull(message = "供应商状态不能为空")
  @Schema(description = "供应商状态 1 洽谈中 2 合作中 3 已暂停")
  private Integer status;

  @Schema(description = "接入日期")
  private LocalDateTime accessDate;

  @NotNull(message = "供应商accont不能为空")
  @Schema(description = "供应商accont")
  private String configAccont;

  @NotNull(message = "供应商key不能为空")
  @Schema(description = "供应商key")
  private String configKey;

  @NotNull(message = "供应商id不能为空")
  @Schema(description = "供应商id")
  private String configId;

  @Schema(description = "联系人")
  private String contacts;

  @Schema(description = "电话")
  private String phone;

  @Schema(description = "预存金额")
  private BigDecimal prepaidAmount;

  @Schema(description = "合作描述")
  private String coopDesc;

}
