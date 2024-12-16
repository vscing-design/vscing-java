package com.vscing.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * VscingApiApplication
 *
 * @author vscing
 * @date 2024/12/14 23:33
 */
@SpringBootApplication
public class VscingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VscingApiApplication.class, args);
        System.out.println("启动成功");
    }

}
