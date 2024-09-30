package com.vscing.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VscingAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VscingAdminApplication.class, args);
        System.out.println("启动成功");
    }

}
