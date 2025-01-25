package com.vscing.api.controller.v1;

import com.vscing.api.service.NotifyService;
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
 * @date 2025/1/19 15:12
 */
@Slf4j
@RestController
@RequestMapping("/v1/notify")
@Tag(name = "异步通知接口", description = "异步通知接口")
public class NotifyController {

  @Autowired
  private NotifyService notifyService;

  @PostMapping("/alipayCreate")
  @Operation(summary = "支付宝下单异步通知")
  public String alipayCreate(HttpServletRequest request) {
    boolean res = notifyService.queryAlipayOrder(request);
    if (res) {
      // 查询订单接口，获取订单状态
      return "success";
    }
    return "fail";
  }

  @PostMapping("/wechatCreate")
  @Operation(summary = "微信下单异步通知")
  public ResponseEntity<Object> wechatCreate(HttpServletRequest request) {
    boolean res = notifyService.queryWechatOrder(request);
    if (res) {
      // 查询订单接口，获取订单状态
      return new ResponseEntity<>(HttpStatus.OK);
    }
    HashMap<String, String> errorResponse = new HashMap<>(2);
    errorResponse.put("code","FAIL");
    errorResponse.put("message","失败");
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
