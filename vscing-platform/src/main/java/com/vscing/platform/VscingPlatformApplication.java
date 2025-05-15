package com.vscing.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.vscing"})
public class VscingPlatformApplication {

  public static void main(String[] args) {
    SpringApplication.run(VscingPlatformApplication.class, args);
    System.out.println("启动成功");
  }

}
