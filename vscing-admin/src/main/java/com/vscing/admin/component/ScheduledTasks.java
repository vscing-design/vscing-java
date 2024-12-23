package com.vscing.admin.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-12-23 20:23:24
*/
@Component
public class ScheduledTasks {

    @Scheduled(cron = "0 0 22,0 * * ?") // 每天晚上10点和午夜12点执行
    public void executeTask() {
        System.out.println("执行定时任务：" + LocalDateTime.now());
    }

}
