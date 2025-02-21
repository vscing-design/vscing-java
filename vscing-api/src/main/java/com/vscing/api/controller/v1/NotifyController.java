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
import org.springframework.web.bind.annotation.RequestParam;
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
    // 查询订单接口，获取订单状态
    boolean res = notifyService.queryWechatOrder(request);
    if (!res) {
      HashMap<String, String> errorResponse = new HashMap<>(2);
      errorResponse.put("code", "FAIL");
      errorResponse.put("message", "失败");
      return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }

  @PostMapping("/baiduCreate")
  @Operation(summary = "百度下单异步通知")
  public ResponseEntity<Object> baiduCreate(HttpServletRequest request) {
    boolean res = notifyService.queryBaiduOrder(request);
    if (res) {
      // 查询订单接口，获取订单状态
      return new ResponseEntity<>(HttpStatus.OK);
    }
    HashMap<String, String> errorResponse = new HashMap<>(2);
    errorResponse.put("code","FAIL");
    errorResponse.put("message","失败");
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping("/baiduRefundReview")
  @Operation(summary = "百度下单退款审核")
  public ResponseEntity<Object> baiduRefundReview(@RequestParam(value="refundPayMoney") int refundPayMoney) {
    HashMap<String, Object> response = new HashMap<>(2);
    response.put("errno", 0);
    response.put("msg", "success");
    // 金额数据
    HashMap<String, Object> calculateRes = new HashMap<>(1);
    calculateRes.put("refundPayMoney", refundPayMoney);
    // data数据
    HashMap<String, Object> data = new HashMap<>(2);
    data.put("auditStatus", 1);
    data.put("calculateRes", calculateRes);
    response.put("data", data);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/baiduRefund")
  @Operation(summary = "百度下单退款通知")
  public ResponseEntity<Object> baiduRefund(HttpServletRequest request) {
    return null;
  }

}
