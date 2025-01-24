package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * UserListVo
 *
 * @author vscing
 * @date 2024/12/29 00:02
 */
@Data
public class UserListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "来源 1 -> 微信小程序 2 -> 支付宝小程序")
  private Integer source;

  @Schema(description = "用户名称")
  private String username;

  @Schema(description = "用户手机号")
  private String phone;

  @Schema(description = "用户订单数")
  private Integer orderNum;

  @Schema(description = "用户星级")
  private Integer star;

  @Schema(description = "用户入驻时间")
  private LocalDateTime createdAt;

}
