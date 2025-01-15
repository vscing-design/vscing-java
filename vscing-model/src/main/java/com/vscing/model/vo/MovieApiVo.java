package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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

  @Schema(description = "场次ID")
  private Long showId;

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

}
