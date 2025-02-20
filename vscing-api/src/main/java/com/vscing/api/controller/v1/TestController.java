package com.vscing.api.controller.v1;

import com.vscing.api.service.TestService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.model.mq.SyncCodeMq;
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

import java.math.BigDecimal;
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
        SyncCodeMq syncCodeMq = new SyncCodeMq();
        syncCodeMq.setOrderId(1889219011411103744L);
        syncCodeMq.setNum(20);
        String msg = JsonUtils.toJsonString(syncCodeMq);
        rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, msg, 3*60 *1000);
//
//        SyncCodeMq result = JsonUtils.parseObject(msg, SyncCodeMq.class);
//        log.info("orderId: {}, num: {}", result.getOrderId(), result.getNum());

//        Long orderId = 1L;
//        rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, orderId.toString(), 10 *1000);

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
//        Long orderId = 1883894846693003264L;
//        rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, orderId.toString(), 10 *1000);
        AppletService appletService = appletServiceFactory.getAppletService("wechat");
        Map<String, Object> paymentData = new HashMap<>(3);
        paymentData.put("outTradeNo", "HY1010101010101011TEST");
        paymentData.put("totalAmount", new BigDecimal(0.01));
        paymentData.put("openid", "o9Yfs6-bjseDWe2DVXcUFSIQkjrk");
        return CommonResult.success(appletService.getPayment(paymentData));
    }

}
