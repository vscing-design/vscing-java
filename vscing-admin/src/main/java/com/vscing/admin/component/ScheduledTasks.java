package com.vscing.admin.component;

import com.vscing.admin.service.TaskService;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TaskService taskService;

    @Autowired
    private ShowMapper showMapper;

    @Autowired
    private ShowAreaMapper showAreaMapper;

    @Scheduled(cron = "0 0 8,9 * * ?") // 每天早上8点和9点执行同步影片和同步场次
    public void movieShowTask() {
        System.out.println("执行定时任务 每天早上8点和9点执行同步影片和同步场次：" + LocalDateTime.now());
        taskService.syncMovie();
        taskService.syncShow();
    }

    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点执行场次清空
    public void truncateTableTask() {
        System.out.println("执行定时任务 每天凌晨3点执行场次清空：" + LocalDateTime.now());
        showMapper.truncateTable();
        showAreaMapper.truncateTable();
    }

    @Scheduled(cron = "0 0 22 ? * 5") // 每周5晚上10点同步影院
    public void cinemaTask() {
        System.out.println("执行定时任务 每周5晚上10点同步影院：" + LocalDateTime.now());
        taskService.syncCinema();
    }

}
