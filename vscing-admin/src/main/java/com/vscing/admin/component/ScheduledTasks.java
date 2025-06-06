package com.vscing.admin.component;

import com.vscing.admin.service.TaskService;
import com.vscing.admin.service.VipSyncService;
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
    private VipSyncService vipSyncService;

    @Autowired
    private ShowMapper showMapper;

    @Autowired
    private ShowAreaMapper showAreaMapper;

    @Scheduled(cron = "0 0 5 * * ?") // 每天早上5点执行同步影片
    public void movieTask() {
        System.out.println("执行定时任务 每天早上5点执行同步影片：" + LocalDateTime.now());
        taskService.syncMovie();
    }

    @Scheduled(cron = "0 0 5 */3 * ?") // 每三天同步一次，每次同步都是早上5点
    public void addressTask() {
        System.out.println("执行定时任务 每三天早上5点执行同步地址：" + LocalDateTime.now());
        taskService.syncAddress();
    }

    @Scheduled(cron = "0 0 6 */3 * ?") // 每三天同步一次，每次早上6点同步城市影院
    public void cityCinemaTask() {
        System.out.println("执行定时任务 每三天早上6点同步城市影院：" + LocalDateTime.now());
        taskService.syncCityCinema();
    }

    @Scheduled(cron = "0 0 6 */3 * ?") // 每三天同步一次，每次早上6点同步区县影院
    public void districtCinema() {
        System.out.println("执行定时任务 每三天早上6点同步区县影院：" + LocalDateTime.now());
        taskService.syncDistrictCinema();
    }

    @Scheduled(cron = "0 0 8-23/2 * * ?") // 每天8点开始，每2小时执行一次
    public void performTaskFirstPart() {
        System.out.println("执行定时任务 每天8点开始，每2小时执行同步场次：" + LocalDateTime.now());
        taskService.syncShow();
    }

    @Scheduled(cron = "0 0 0-4/2 * * ?") // 每天0点开始，每2小时执行一次
    public void performTaskSecondPart() {
        System.out.println("执行定时任务 每天0点开始，每2小时执行同步场次：" + LocalDateTime.now());
        taskService.syncShow();
    }

    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行场次清空
    public void truncateTableTask() {
        System.out.println("执行定时任务 每天凌晨1点执行场次清空：" + LocalDateTime.now());
        taskService.syncTable();
    }

//    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行场次清空
//    public void truncateTableTask() {
//        System.out.println("执行定时任务 每天凌晨1点执行场次清空：" + LocalDateTime.now());
//        taskService.syncTable();
//    }

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

    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行会员商品分类同步
    public void vipGroupTask() {
        System.out.println("执行定时任务 每天凌晨1点执行场次清空：" + LocalDateTime.now());
        vipSyncService.queryGroup();
    }

    @Scheduled(cron = "0 0 8-23/2 * * ?") // 每天8点开始，每2小时执行一次
    public void vipGoodsFirstTask() {
        System.out.println("执行定时任务 每天8点开始，每2小时执行商品同步：" + LocalDateTime.now());
        vipSyncService.queryGoods(1);
    }

    @Scheduled(cron = "0 30 8-23/2 * * ?")
    public void vipGoodsFirstTask1() {
        System.out.println("执行定时任务 每天8点开始，每2个半小时执行商品同步：" + LocalDateTime.now());
        vipSyncService.allGoods();
    }

    @Scheduled(cron = "0 0 0-4/2 * * ?") // 每天0点开始，每2小时执行一次
    public void vipGoodsSecondTask() {
        System.out.println("执行定时任务 每天0点开始，每2小时执行商品同步：" + LocalDateTime.now());
        vipSyncService.queryGoods(1);
    }

    @Scheduled(cron = "0 30 0-4/2 * * ?") // 每天0点开始，每2小时执行一次
    public void vipGoodsSecondTask1() {
        System.out.println("执行定时任务 每天0点开始，每2个半小时执行商品同步：" + LocalDateTime.now());
        vipSyncService.queryGoods(1);
    }

}
