package com.vscing.admin.controller.v1;

import com.vscing.admin.service.NotifyService;
import com.vscing.model.http.HttpOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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

  @Autowired
  private NotifyService notifyService;

  @PostMapping("/order")
  @Operation(summary = "订单回调", description = "回调保证不了正常业务处理，只做主动查询订单业务处理")
  public String order(HttpOrder data) {
    log.info("data: {}", data);
    return "OK";
  }

  @PostMapping("/alipayTransfer")
  @Operation(summary = "支付宝转账异步通知")
  public String alipayCreate(HttpServletRequest request) {
    boolean res = notifyService.alipayTransfer(request);
    if (res) {
      // 查询订单接口，获取订单状态
      return "success";
    }
    return "fail";
  }

  @PostMapping("/wechatTransfer")
  @Operation(summary = "微信转账异步通知")
  public ResponseEntity<Object> wechatCreate(HttpServletRequest request) {
    // 查询订单接口，获取订单状态
    boolean res = notifyService.wechatTransfer(request);
    if (!res) {
      HashMap<String, String> errorResponse = new HashMap<>(2);
      errorResponse.put("code", "FAIL");
      errorResponse.put("message", "失败");
      return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }

}
