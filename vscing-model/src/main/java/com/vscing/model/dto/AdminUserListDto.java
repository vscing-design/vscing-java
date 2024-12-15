package com.vscing.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * AdminUserListDto
 *
 * @author vscing
 * @date 2024/12/15 23:12
 */
@Data
public class AdminUserListDto {

  private String username;

  private String phone;

  private LocalDateTime startDate;

  private LocalDateTime endDate;
}
