package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * UserConfig
 *
 * @author vscing
 * @date 2025/3/2 22:02
 */
@Getter
@Setter
public class UserConfig extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "名称")
  private String title;

  @Schema(description = "键")
  private String key;

  @Schema(description = "值")
  private String value;

}
