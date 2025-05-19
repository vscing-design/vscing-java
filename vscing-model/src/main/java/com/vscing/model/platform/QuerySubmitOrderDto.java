package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySubmitOrderDto extends QueryDto {

  @NotNull(message = "场次id未传入")
  @Schema(description = "场次id")
  private Long showId;

  @NotNull(message = "座位列表未传入")
  @Schema(description = "座位列表")
  private List<SeatList> seatList;

  @NotNull(message = "是否支持分区未传入")
  @Schema(description = "是否支持分区")
  private Integer addFlag;

  @NotNull(message = "异步回调地址未传入")
  @Schema(description = "异步回调地址")
  private String notifyUrl;

  @NotNull(message = "取票手机号未传入")
  @Schema(description = "取票手机号")
  private String takePhoneNumber;

  @NotNull(message = "商户平台订单号未传入")
  @Schema(description = "商户平台订单号")
  private String tradeNo;

  @Schema(description = "是否允许调座")
  private Integer supportChangeSeat;

}
