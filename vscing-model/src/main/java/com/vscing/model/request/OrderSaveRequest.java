package com.vscing.model.request;

import com.vscing.model.dto.SeatListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * OrderSaveRequest
 *
 * @author vscing
 * @date 2024/12/31 01:33
 */
@Data
public class OrderSaveRequest {

  @NotNull(message = "用户来源不能为空")
  @Schema(description = "用户来源 1 微信小程序 2 支付宝小程序 3 淘宝 4 咸鱼 5 拼多多 6 微信")
  private Integer userSource;

//  @NotNull(message = "用户信息不能为空")
  @Schema(description = "用户信息")
  private String username;

  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^1([3456789])\\d{9}$", message = "手机号格式不正确")
  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "场次ID")
  private Long showId;

  @NotNull(message = "选中座位不能为空")
  @Schema(description = "选中座位")
  private List<SeatListDto> seatList;

  @NotNull(message = "支付状态不能为空")
  @Schema(description = "支付状态 1 待支付 2 已支付")
  private Integer status;

  @Schema(description = "备注信息")
  private String memo;

}
