package com.vscing.api.controller.v1;

import com.vscing.api.service.TestService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.model.entity.Order;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.mapper.OrderMapper;
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

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    @Operation(summary = "测试mq")
    public CommonResult<Object> test() {

        rabbitMQService.sendDelayedMessage(RabbitMQConfig.REFUND_ROUTING_KEY, "1893507872288112640", 1000);
        // 查询订单信息
        Order order = orderMapper.selectByOrderSn("HY202502231147112967");
        if(order == null) {
            throw new ServiceException("订单数据不存在");
        }
        // 获取支付句柄
        String appletType = AppletTypeEnum.findByCode(order.getPlatform());
        AppletService appletService = appletServiceFactory.getAppletService(appletType);
        // 组装参数
        BigDecimal totalAmount = order.getTotalPrice();
        totalAmount = totalAmount.multiply(BigDecimal.valueOf(100));
        Map<String, String> queryData = new HashMap<>(5);
        queryData.put("outTradeNo", order.getTradeNo());
        queryData.put("tradeNo", order.getOrderSn());
        queryData.put("refundNo", order.getRefundNo());
        queryData.put("totalAmount", totalAmount.toString());
        queryData.put("baiduUserId", order.getBaiduUserId().toString());
        // 发送退款请求
        boolean res = appletService.queryRefund(queryData);
        log.info("百度退款订单查询结果: {}", res);
        return CommonResult.success("ok");
//        SyncCodeMq syncCodeMq = new SyncCodeMq();
//        syncCodeMq.setOrderId(1889219011411103744L);
//        syncCodeMq.setNum(20);
//        String msg = JsonUtils.toJsonString(syncCodeMq);
//        rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, msg, 3*60 *1000);
//
//        SyncCodeMq result = JsonUtils.parseObject(msg, SyncCodeMq.class);
//        log.info("orderId: {}, num: {}", result.getOrderId(), result.getNum());

//        Long orderId = 1L;
//        rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, orderId.toString(), 10 *1000);

//        return CommonResult.success("ok");
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

    @GetMapping("/baidu")
    @Operation(summary = "测试百度")
    public CommonResult<Object> baidu() {
        rabbitMQService.sendDelayedMessage(RabbitMQConfig.REFUND_ROUTING_KEY, "1893920153807257600", 10 * 1000);
        AppletService appletService = appletServiceFactory.getAppletService("baidu");
        return CommonResult.success(true);
    }

}
