package com.vscing.admin.controller.v1;

import com.vscing.admin.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AddressController
 *
 * @author vscing
 * @date 2024/12/23 00:00
 */
@Slf4j
@RestController
@RequestMapping("/v1/address")
@Tag(name = "系统管理员登陆接口", description = "系统管理员登陆接口")
public class AddressController {

  @Autowired
  private AddressService addressService;

}
