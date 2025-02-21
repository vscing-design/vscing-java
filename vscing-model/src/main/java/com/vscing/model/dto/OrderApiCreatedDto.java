package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * OrderApiCreatedDto
 *
 * @author vscing
 * @date 2025/1/19 12:11
 */
@Data
public class OrderApiCreatedDto {

  @NotNull(message = "场次ID不能为空")
  @Schema(description = "场次id")
  private Long showId;

  @NotNull(message = "平台不能为空")
  @Schema(description = "平台 wechat 微信小程序 alipay 支付宝小程序 baidu 百度小程序")
  private String platform;

  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", message = "手机号格式不正确")
  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "无座位时，接受系统调座 1 是 2 否")
  private Integer seatAdjusted;

  @NotNull(message = "选中座位不能为空")
  @Schema(description = "选中座位")
  private List<SeatListDto> seatList;

  @Schema(description = "备注信息")
  private String memo;

}
