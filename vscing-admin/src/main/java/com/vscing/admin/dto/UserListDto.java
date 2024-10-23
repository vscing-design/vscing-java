package com.vscing.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserListDto {

    private String username;

    private String phone;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
