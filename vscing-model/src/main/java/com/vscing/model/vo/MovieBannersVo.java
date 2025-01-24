package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MovieBannersVo
 *
 * @author vscing
 * @date 2025/1/14 16:35
 */
@Data
public class MovieBannersVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影片名称")
  private String name;

  @Schema(description = "海报图片")
  private String posterUrl;

}

