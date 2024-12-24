package com.vscing.model.vo;

import com.vscing.model.dto.AdminUserSaveDto;
import com.vscing.model.entity.Menu;
import com.vscing.model.entity.Organization;
import com.vscing.model.entity.Permission;
import com.vscing.model.entity.Role;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AdminUserVo
 *
 * @author vscing
 * @date 2024/12/19 22:14
 */
@Getter
@Setter
@AutoMapper(target = AdminUserSaveDto.class)
public class AdminUserDetailVo {

  @Schema(description = "用户id")
  private Long id;

  @Schema(description = "账户")
  private String username;

  @Schema(description = "密码")
  private String password;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "邮箱")
  private String email;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer status;

  @Schema(description = "头像")
  private String avatar;

  @Schema(description = "备注")
  private String notes;

  @Schema(description = "最后登陆IP")
  private String lastIp;

  @Schema(description = "最后登陆时间")
  private LocalDateTime loginAt;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "创建者ID")
  private Long createdBy;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

  @Schema(description = "更新者ID")
  private Long updatedBy;

  @Schema(description = "关联机构")
  private List<Organization> relatedOrgList;

  @Schema(description = "关联角色")
  private List<Role> relatedRoleList;

  @Schema(description = "关联菜单")
  private List<Menu> relatedMenuList;

  @Schema(description = "按钮权限")
  private List<Permission> relatedPermissionList;

}
