package com.vscing.admin.controller.v1;

import com.vscing.admin.service.TaskService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.service.payment.PaymentService;
import com.vscing.common.service.payment.PaymentServiceFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TaskController
 *
 * @date 2024/12/11 00:51
 * @auth vscing(vscing @ foxmail.com)
 */
@Slf4j
@RestController
@RequestMapping("/v1/task")
@Tag(name = "任务接口", description = "任务接口")
public class TaskController {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PaymentServiceFactory paymentServiceFactory;

    @GetMapping
    public CommonResult<String> test() {
        PaymentService paymentService = paymentServiceFactory.getPaymentService("alipay");
        String result = paymentService.pay(10);
        return CommonResult.success(result);
    }


    // https://docs.qq.com/doc/DWXVlWmtHbnpLUXdT
    @GetMapping("/address")
    public CommonResult<Object> listAll() {
        taskService.syncAddress();
        return CommonResult.success();
    }
}
