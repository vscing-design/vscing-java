package com.vscing.api.controller.v1;

import com.vscing.api.service.TestService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.mq.config.RabbitMQConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.vscing.mq.config.RabbitMQConfig.DELAYED_EXCHANGE_NAME;

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
    private RabbitTemplate rabbitTemplate;

    public void sendDelayedMessage(String routingKey, Object message, int delayMilliseconds) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, routingKey, message, msg -> {
            msg.getMessageProperties().setHeader("x-delay", delayMilliseconds);
            return msg;
        });
        System.out.println(" [x] Sent delayed message to " + routingKey);
    }

    @GetMapping
    @Operation(summary = "测试mq")
    public CommonResult<Object> test() {
        Map<String, Object> message = new HashMap<>(2);
        // 2分钟
        message.put("id", 1);
        message.put("key", "123");
        sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, message, 2 * 60 * 1000);
        // 10分钟
        message.put("id", 2);
        message.put("key", "456");
        sendDelayedMessage(RabbitMQConfig.CANCEL_ORDER_ROUTING_KEY, message, 10 * 60 * 1000);
        return CommonResult.success("ok");
    }

    @GetMapping("/seat")
    @Operation(summary = "线上测试座位列表和最多选择多少个")
    public CommonResult<SeatMapVo> seat(@ParameterObject ShowSeatRequest data) {
        return CommonResult.success(testService.getSeat(data));
    }

}
