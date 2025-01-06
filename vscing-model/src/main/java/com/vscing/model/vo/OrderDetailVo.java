package com.vscing.model.vo;

import com.vscing.model.dto.SeatListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * OrderDetailVo
 *
 * @author vscing
 * @date 2025/1/6 18:53
 */
@Data
public class OrderDetailVo {

  @Schema(description = "用户来源 1 微信小程序 2 支付宝小程序 3 淘宝 4 咸鱼 5 拼多多 6 微信")
  private Integer userSource;

  @Schema(description = "用户信息")
  private String username;

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

  @Schema(description = "省份ID")
  private Long provinceId;

  @Schema(description = "省份名称")
  private String provinceName;

  @Schema(description = "城市ID")
  private Long cityId;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "区县ID")
  private Long districtId;

  @Schema(description = "区县名称")
  private String districtName;

  @Schema(description = "选中座位")
  private List<SeatListDto> seatList;

  @Schema(description = "支付状态 1 待支付 2 已支付")
  private Integer status;

  @Schema(description = "备注信息")
  private String memo;

}
