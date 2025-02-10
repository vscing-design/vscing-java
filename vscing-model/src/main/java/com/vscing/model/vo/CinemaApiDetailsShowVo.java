package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CinemaApiDetailsShowVo
 *
 * @author vscing
 * @date 2025/1/16 23:20
 */
@Data
public class CinemaApiDetailsShowVo {

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "影片ID")
    private Long movieId;

    @Schema(description = "场次ID")
    private Long showId;

    @Schema(description = "影片名称")
    private String movieName;

    @Schema(description = "上映日期")
    private LocalDateTime publishDate;

    @Schema(description = "影片导演")
    private String director;

    @Schema(description = "影片主演")
    private String cast;

    @Schema(description = "影片简介")
    private String intro;

    @Schema(description = "上映类型")
    private String versionType;

    @Schema(description = "影片语言")
    private String language;

    @Schema(description = "影片类型")
    private String movieType;

    @Schema(description = "海报图片")
    private String posterUrl;

    @Schema(description = "剧情照，多个用英文逗号隔开")
    private String plotUrl;

    @Schema(description = "评分")
    private String grade;

    @Schema(description = "想看人数")
    private Integer like;

    @Schema(description = "上映类型，HOT为热映，WAIT为待上映")
    private String publishStatus;

    @Schema(description = "影厅名称")
    private String hallName;

    @Schema(description = "放映时长（分钟）")
    private Integer duration;

    @Schema(description = "放映开始时间")
    private LocalDateTime showTime;

    @Schema(description = "电影售卖结束时间")
    private LocalDateTime stopSellTime;

    @Schema(description = "场次类型")
    private String showVersionType;

    @Schema(description = "官方金额")
    private BigDecimal maxPrice;

    @Schema(description = "销售金额")
    private BigDecimal minPrice;

    @Schema(description = "优惠金额")
    private BigDecimal discount;

    @Schema(description = "临时值")
    private BigDecimal showPrice;

    @Schema(description = "临时值")
    private BigDecimal userPrice;

}
