package com.vscing.admin.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    @Scheduled(cron = "0/10 * * * * ?") // 每10秒执行一次
    public void executeTask() {
        System.out.println("执行定时任务：" + LocalDateTime.now());
    }

}
