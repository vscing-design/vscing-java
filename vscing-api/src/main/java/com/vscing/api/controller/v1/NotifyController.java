package com.vscing.api.controller.v1;

import com.vscing.api.service.NotifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    Map<String, String> params = new HashMap<String,String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i]
            : valueStr + values[i] + ",";
      }
      //乱码解决，这段代码在出现乱码时使用。
      //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
      params.put(name, valueStr);
    }
    log.error("支付宝异步通知: {}", params);
    boolean res = notifyService.queryAlipayOrder(params);
    if (res) {
      // 查询订单接口，获取订单状态
      return "success";
    }
    return "fail";
  }

  @PostMapping("/wechatCreate")
  @Operation(summary = "微信下单异步通知")
  public String wechatCreate(HttpServletRequest request) {
    Map<String, String> params = new HashMap<String,String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i]
                : valueStr + values[i] + ",";
      }
      //乱码解决，这段代码在出现乱码时使用。
      //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
      params.put(name, valueStr);
    }
    log.error("微信下单异步通知: {}", params);
    return "success";
//    boolean res = notifyService.queryAlipayOrder(params);
//    if (res) {
//      // 查询订单接口，获取订单状态
//      return "success";
//    }
//    return "fail";
  }

}
