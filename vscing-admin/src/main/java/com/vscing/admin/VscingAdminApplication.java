package com.vscing.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.vscing.admin", "com.vscing.auth"})
public class VscingAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VscingAdminApplication.class, args);
        System.out.println("启动成功");
    }

}
