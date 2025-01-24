package com.vscing.api.controller.v1;

import com.vscing.api.service.TestService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.mq.config.RabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TestController
 *
 * @author vscing
 * @date 2025/1/12 19:58
 */
@Slf4j
@RestController
@RequestMapping("/v1/test")
@Tag(name = "测试接口", description = "测试接口")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppletServiceFactory appletServiceFactory;

    @GetMapping
    @Operation(summary = "测试mq")
    public CommonResult<Object> test() {
        Long orderId = 1L;
        rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, orderId.toString(), 10 *1000);
        return CommonResult.success("ok");
    }

    @GetMapping("/seat")
    @Operation(summary = "线上测试座位列表和最多选择多少个")
    public CommonResult<SeatMapVo> seat(@ParameterObject ShowSeatRequest data) {
        return CommonResult.success(testService.getSeat(data));
    }

    @GetMapping("/wechat")
    @Operation(summary = "测试微信支付")
    public CommonResult<Object> wechat() {
        AppletService appletService = appletServiceFactory.getAppletService("wechat");
        Map<String, Object> paymentData = new HashMap<>(3);
        paymentData.put("outTradeNo", "HY1010101010101010TEST");
        paymentData.put("totalAmount", 0.01);
        paymentData.put("openid", "047RtYzw5zu6b1kQGd5rT2EpQJEYL0J_AIKbPAaYkwx8sUf");
        return CommonResult.success(appletService.getPayment(paymentData));
    }

}
