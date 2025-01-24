package com.vscing.model.vo;

import com.vscing.model.entity.ShowArea;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ShowListVo
 *
 * @author vscing
 * @date 2024/12/29 00:23
 */
@Data
@AutoMappers({
    @AutoMapper(target = MovieTreeVo.class),
    @AutoMapper(target = ShowTreeVo.class)
})
public class ShowListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "供应商名称")
  private String supplierName;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "影院所在省份")
  private String provinceName;

  @Schema(description = "影院所在城市")
  private String cityName;

  @Schema(description = "影院所在区县")
  private String districtName;

  @Schema(description = "影院详细地址")
  private String address;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "电影售卖结束时间")
  private LocalDateTime stopSellTime;

  @Schema(description = "放映时长（分钟）")
  private Integer duration;

  @Schema(description = "场次类型")
  private String showVersionType;

  @Schema(description = "场次价格（元）")
  private BigDecimal showPrice;

  @Schema(description = "影片结算价（元）")
  private BigDecimal userPrice;

  @Schema(description = "分区定价信息")
  private List<ShowArea> showAreaList;

}
