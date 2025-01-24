package com.vscing.model.vo;

import com.vscing.model.entity.MovieProducer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MovieApiDetailsVo
 *
 * @author vscing
 * @date 2025/1/18 01:50
 */
@Data
public class MovieApiDetailsVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影片名称")
  private String name;

  @Schema(description = "影片时长（分钟）")
  private Integer duration;

  @Schema(description = "上映日期")
  private LocalDateTime publishDate;

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

  @Schema(description = "影片导演")
  private String director;

  @Schema(description = "影片主演")
  private String cast;

  @Schema(description = "演员、导演列表")
  private List<MovieProducer> movieProducerList;

}
