package com.vscing.admin.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RoleController
 *
 * @author vscing
 * @date 2024/12/22 01:26
 */
@RestController
@RequestMapping("/v1/role")
@Tag(name = "系统角色接口", description = "系统角色接口")
public class RoleController {
  private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

}
