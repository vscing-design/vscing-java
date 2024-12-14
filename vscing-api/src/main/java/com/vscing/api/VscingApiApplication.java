package com.vscing.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VscingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VscingApiApplication.class, args);
        System.out.println("启动成功");
    }

}
