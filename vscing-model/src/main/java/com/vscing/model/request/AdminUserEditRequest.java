package com.vscing.model.request;

import com.vscing.model.dto.AdminUserSaveDto;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * AdminUserEditRequest
 *
 * @author vscing
 * @date 2024/12/23 21:25
 */
@Data
@AutoMapper(target = AdminUserSaveDto.class)
public class AdminUserEditRequest {

  @NotNull(message = "用户ID不能为空")
  @Schema(description = "用户ID")
  private Long id;

  @NotNull(message = "用户名不能为空")
  @Size(min = 3, max = 50, message = "用户名长度必须在3到50个字符之间")
  @Schema(description = "用户名")
  private String username;

  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", message = "手机号格式不正确")
  @Schema(description = "手机号")
  private String phone;

  @NotNull(message = "机构不能为空")
  @Size(min = 1, message = "机构至少选择一个")
  @Schema(description = "机构")
  private List<Long> organizationIds;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer status;

}
