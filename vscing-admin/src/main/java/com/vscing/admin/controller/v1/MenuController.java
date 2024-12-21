package com.vscing.admin.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MenuController
 *
 * @author vscing
 * @date 2024/12/22 01:24
 */
@RestController
@RequestMapping("/v1/menu")
@Tag(name = "系统菜单接口", description = "系统菜单接口")
public class MenuController {

  private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

}
