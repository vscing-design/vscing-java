package com.vscing.api.controller.v1;

import com.vscing.api.service.TestService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.InviteCodeService;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.UserAuth;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.UserAuthMapper;
import com.vscing.model.mq.SyncCodeMq;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.mq.config.DelayRabbitMQConfig;
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
    private InviteCodeService inviteCodeService;

    @Autowired
    private AppletServiceFactory appletServiceFactory;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @GetMapping
    @Operation(summary = "测试mq")
    public CommonResult<Object> test() {


        // 支付成功通知
//        String msg = String.valueOf(1883894846693003264L);
//        rabbitMQService.sendFanoutMessage(FanoutRabbitMQConfig.SYNC_ORDER_ROUTING_KEY, msg);

        try {
            // 查询订单信息
            Order order = orderMapper.selectById(1883894846693003264L);
            if(order == null) {
                throw new ServiceException("订单数据不存在");
            }
            if(order.getPlatform() != AppletTypeEnum.ALIPAY.getCode()) {
                throw new ServiceException("订单平台错误");
            }
            // 查询用户openid
            UserAuth userAuth = userAuthMapper.findOpenid(order.getUserId(), order.getPlatform());
            // 调用支付宝同步订单
            AppletService appletService = appletServiceFactory.getAppletService(AppletTypeEnum.ALIPAY.getApplet());
            Map<String, Object> syncOrderData = new HashMap<>(1);
            syncOrderData.put("outBizNo", order.getOrderSn());
            syncOrderData.put("orderModifiedTime", order.getUpdatedAt());
            syncOrderData.put("orderCreateTime", order.getCreatedAt());
            syncOrderData.put("buyerOpenId", userAuth.getOpenid());
            syncOrderData.put("amount", order.getTotalPrice());
            syncOrderData.put("tradeNo", order.getTradeNo());
            syncOrderData.put("purchaseQuantity", order.getPurchaseQuantity());
            syncOrderData.put("stopShowTime", order.getStopShowTime());
            appletService.syncOrder(syncOrderData);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        // 发起提现
//        TransferMq transferMq = new TransferMq();
//        transferMq.setUserId(1881281583710961664L);
//        transferMq.setPlatform(1);
//        transferMq.setAmount(new BigDecimal("0.01"));
//        transferMq.setWithdrawSn("HY-TX202503132312300921");
//        String msg = JsonUtils.toJsonString(transferMq);
//        rabbitMQService.sendFanoutMessage(FanoutRabbitMQConfig.TRANSFER_ROUTING_KEY, msg);

//        try {
//            TransferMq transferMq = new TransferMq();
//            transferMq.setUserId(1881281583710961664L);
//            transferMq.setPlatform(2);
//            transferMq.setAmount(new BigDecimal("1.00"));
//            transferMq.setWithdrawSn("HYT202503132312300921");
//            UserAuth userAuth = userAuthMapper.findOpenid(transferMq.getUserId(), transferMq.getPlatform());
//            if(userAuth == null) {
//                throw new ServiceException("转账用户不存在");
//            }
//            int platform = transferMq.getPlatform();
//            AppletService appletService = appletServiceFactory.getAppletService(AppletTypeEnum.findByCode(platform));
//            Map<String, Object> transferData = new HashMap<>(3);
//            transferData.put("openid", userAuth.getOpenid());
//            transferData.put("amount", transferMq.getAmount());
//            transferData.put("withdrawBatchSn", "HYBT202503152312300923");
//            transferData.put("withdrawSn", "HYT202503132312300921");
//            Object result = appletService.transferOrder(transferData);
//            log.info("transferResult: {}", result);
//        } catch (Exception e) {
//            log.error("转账请求异常：", e);
//            throw new ServiceException(e.getMessage());
//        }

//        String inviteCode = inviteCodeService.idToInviteCode(1894419529721880576L);
//        log.info("inviteCode: {}", inviteCode);
//        Long id = inviteCodeService.inviteCodeToId(inviteCode);
//        log.info("id: {}", id);

//        Order order = orderMapper.selectById(1894419529721880576L);
//        // 下发支付参数
//        OrderApiPaymentVo orderApiPaymentVo = new OrderApiPaymentVo();
//        // 处理支付宝参数
//        if(order.getPlatform() == AppletTypeEnum.ALIPAY.getCode()) {
//            orderApiPaymentVo.setTradeNo(order.getTradeNo());
//            // 处理微信参数
//        } else if (order.getPlatform() == AppletTypeEnum.WECHAT.getCode()) {
//            // 查询用户的openid
//            UserAuth userAuth = userAuthMapper.findOpenid(1883117511135916032L, 1);
//            // 再次支付
//            AppletService appletService = appletServiceFactory.getAppletService(AppletTypeEnum.WECHAT.getApplet());
//            Map<String, Object> paymentData = new HashMap<>(3);
//            paymentData.put("outTradeNo", order.getOrderSn());
//            paymentData.put("totalAmount", order.getTotalPrice());
//            paymentData.put("openid", userAuth.getOpenid());
//            Map<String, String> paymentRes = appletService.getPayment(paymentData);
//            orderApiPaymentVo.setTimeStamp(paymentRes.getOrDefault("timeStamp", ""));
//            orderApiPaymentVo.setNonceStr(paymentRes.getOrDefault("nonceStr", ""));
//            orderApiPaymentVo.setPackageStr(paymentRes.getOrDefault("packageStr", ""));
//            orderApiPaymentVo.setSignType(paymentRes.getOrDefault("signType", ""));
//            orderApiPaymentVo.setPaySign(paymentRes.getOrDefault("paySign", ""));
//            // 处理百度参数
//        } else if (order.getPlatform() == AppletTypeEnum.BAIDU.getCode()) {
//            // 再次支付
//            AppletService appletService = appletServiceFactory.getAppletService(AppletTypeEnum.BAIDU.getApplet());
//            Map<String, Object> paymentData = new HashMap<>(2);
//            paymentData.put("outTradeNo", order.getOrderSn());
//            paymentData.put("totalAmount", order.getTotalPrice());
//            Map<String, String> paymentRes = appletService.getPayment(paymentData);
//            // 百度支付参数
//            BaiduOrderInfoVo baiduOrderInfoVo = new BaiduOrderInfoVo();
//            baiduOrderInfoVo.setAppKey(paymentRes.getOrDefault("appKey", ""));
//            baiduOrderInfoVo.setDealId(paymentRes.getOrDefault("dealId", ""));
//            baiduOrderInfoVo.setRsaSign(paymentRes.getOrDefault("rsaSign", ""));
//            baiduOrderInfoVo.setTotalAmount(String.valueOf(order.getTotalPrice().multiply(BigDecimal.valueOf(100))));
//            baiduOrderInfoVo.setDealTitle("嗨呀电影票订单" + order.getOrderSn());
//            baiduOrderInfoVo.setTpOrderId(order.getOrderSn());
//            baiduOrderInfoVo.setSignFieldsRange("1");
//            orderApiPaymentVo.setOrderInfo(baiduOrderInfoVo);
//        }
//        log.info("orderApiPaymentVo: {}", orderApiPaymentVo);

//        rabbitMQService.sendDelayedMessage(RabbitMQConfig.REFUND_ROUTING_KEY, "1893507872288112640", 1000);
        // 查询订单信息
//        Order order = orderMapper.selectByOrderSn("HY202502231147112967");
//        if(order == null) {
//            throw new ServiceException("订单数据不存在");
//        }
//        // 获取支付句柄
//        String appletType = AppletTypeEnum.findByCode(order.getPlatform());
//        AppletService appletService = appletServiceFactory.getAppletService(appletType);
//        // 组装参数
//        BigDecimal totalAmount = order.getTotalPrice();
//        totalAmount = totalAmount.multiply(BigDecimal.valueOf(100));
//        Map<String, String> queryData = new HashMap<>(5);
//        queryData.put("outTradeNo", order.getTradeNo());
//        queryData.put("tradeNo", order.getOrderSn());
//        queryData.put("refundNo", order.getRefundNo());
//        queryData.put("totalAmount", totalAmount.toString());
//        queryData.put("baiduUserId", order.getBaiduUserId().toString());
//        // 发送退款请求
//        boolean res = appletService.queryRefund(queryData);
//        log.info("百度退款订单查询结果: {}", res);
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
        // 发送mq异步处理 同步出票信息
        SyncCodeMq syncCodeMq = new SyncCodeMq();
        syncCodeMq.setOrderId(1911006715162316800L);
        syncCodeMq.setNum(1);
        String msg = JsonUtils.toJsonString(syncCodeMq);
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_CODE_ROUTING_KEY, msg, 3*60*1000);
//        rabbitMQService.sendDelayedMessage(RabbitMQConfig.REFUND_ROUTING_KEY, "1893920153807257600", 10 * 1000);
        AppletService appletService = appletServiceFactory.getAppletService("baidu");
        return CommonResult.success(true);
    }

}
