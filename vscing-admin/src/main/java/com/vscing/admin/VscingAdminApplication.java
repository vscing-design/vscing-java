package com.vscing.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * VscingAdminApplication
 *
 * @author vscing
 * @date 2024/12/14 23:33
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.vscing"})
public class VscingAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VscingAdminApplication.class, args);
        System.out.println("启动成功");
    }

}
