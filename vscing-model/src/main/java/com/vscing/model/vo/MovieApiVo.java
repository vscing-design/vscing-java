package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MovieApiVo
 *
 * @author vscing
 * @date 2025/1/13 10:26
 */
@Data
public class MovieApiVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影片类型")
  private String movieType;

  @Schema(description = "海报图片")
  private String posterUrl;

  @Schema(description = "影片名称")
  private String name;

  @Schema(description = "想看人数")
  private Integer like;

  @Schema(description = "上映日期")
  private LocalDateTime publishDate;

  @Schema(description = "上映类型")
  private String versionType;

  @Schema(description = "影片语言")
  private String language;

  @Schema(description = "影片导演")
  private String director;

  @Schema(description = "影片主演")
  private String cast;

  @Schema(description = "评分")
  private String grade;

  @Schema(description = "上映类型，HOT为热映，WAIT为待上映")
  private String publishStatus;

  @Schema(description = "最大场次价格（元）")
  private BigDecimal maxPrice;

  @Schema(description = "最小场次价格（元）")
  private BigDecimal minShowPrice;

  @Schema(description = "最小影片结算价（元）")
  private BigDecimal minUserPrice;

  @Schema(description = "最小出售价格")
  private BigDecimal minPrice;

}
