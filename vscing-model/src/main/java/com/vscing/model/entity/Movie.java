package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Movie
 *
 * @author vscing
 * @date 2024/12/26 22:34
 */
@Getter
@Setter
public class Movie extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "三方ID")
  private Long movieId;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "影片名称")
  private String name;

  @Schema(description = "影片时长（分钟）")
  private Integer duration;

  @Schema(description = "上映日期")
  private LocalDateTime publishDate;

  @Schema(description = "影片导演")
  private String director;

  @Schema(description = "影片主演")
  private String cast;



}
