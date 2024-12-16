package com.vscing.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * VscingAdminApplication
 *
 * @author vscing
 * @date 2024/12/14 23:33
 */

@SpringBootApplication(scanBasePackages = {"com.vscing"})
public class VscingAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VscingAdminApplication.class, args);
        System.out.println("启动成功");
    }

}
