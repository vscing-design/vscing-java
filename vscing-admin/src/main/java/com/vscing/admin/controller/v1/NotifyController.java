package com.vscing.admin.controller.v1;

import com.vscing.model.http.HttpOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NotifyController
 *
 * @author vscing
 * @date 2025/1/2 18:28
 */
@Slf4j
@RestController
@RequestMapping("/v1/notify")
@Tag(name = "回调接口", description = "回调接口")
public class NotifyController {

  @PostMapping("/order")
  @Operation(summary = "订单回调", description = "回调保证不了正常业务处理，只做主动查询订单业务处理")
  public String order(HttpOrder data) {
    log.info("data: {}", data);
    return "OK";
  }

}
