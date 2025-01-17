package com.vscing.admin.component;

import com.vscing.admin.service.TaskService;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-12-23 20:23:24
*/
@Component
public class ScheduledTasks {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ShowMapper showMapper;

    @Autowired
    private ShowAreaMapper showAreaMapper;

    @Scheduled(cron = "0 0 5 * * ?") // 每天早上5点执行同步影片
    public void movieTask() {
        System.out.println("执行定时任务 每天早上5点执行同步影片：" + LocalDateTime.now());
        taskService.syncMovie();
    }

    @Scheduled(cron = "0 0 6 * * ?") // 每天早上6点执行同步场次
    public void movieShowTask() {
        System.out.println("执行定时任务 每天早上6点执行同步场次：" + LocalDateTime.now());
        taskService.syncShow();
    }

    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行场次清空
    public void truncateTableTask() {
        System.out.println("执行定时任务 每天凌晨1点执行场次清空：" + LocalDateTime.now());
        taskService.syncTable();
    }

    @Scheduled(cron = "0 0 23 ? * 5") // 每周5晚上11点同步城市影院
    public void cityCinemaTask() {
        System.out.println("执行定时任务 每周5晚上11点同步城市影院：" + LocalDateTime.now());
        taskService.syncCityCinema();
    }

    @Scheduled(cron = "0 0 21 ? * 5") // 每周5晚上9点同步区县影院
    public void districtCinema() {
        System.out.println("执行定时任务 每周5晚上9点同步区县影院：" + LocalDateTime.now());
        taskService.syncDistrictCinema();
    }

//    @Scheduled(cron = "0 0/5 * * * ?") // 每5分钟同步一下取消待支付订单
//    public void pendingPaymentTask() {
//        System.out.println("执行定时任务 每5分钟同步一下取消待支付订单：" + LocalDateTime.now());
//        taskService.syncPendingPaymentOrder();
//    }

//    @Scheduled(cron = "0 0/3 * * * ?") // 每3分钟同步一下出票信息
//    public void pendingTicketTask() {
//        System.out.println("执行定时任务 每3分钟同步一下出票信息：" + LocalDateTime.now());
//        taskService.syncPendingTicketOrder();
//    }

}
