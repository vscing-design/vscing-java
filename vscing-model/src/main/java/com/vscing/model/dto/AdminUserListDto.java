package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * AdminUserListDto
 *
 * @author vscing
 * @date 2024/12/15 23:12
 */
@Data
public class AdminUserListDto {

  @Schema(description = "机构ID")
  private Long organizationId;

  @Schema(description = "账户")
  private String username;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "开始创建日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;

  @Schema(description = "结束创建日期")
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") post用
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // get用
  private LocalDateTime endDate;
}
