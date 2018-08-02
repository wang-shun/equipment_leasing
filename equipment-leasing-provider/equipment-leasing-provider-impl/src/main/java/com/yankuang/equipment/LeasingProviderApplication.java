package com.yankuang.equipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动设备租赁计划服务
 */
@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.leasing")
public class LeasingProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LeasingProviderApplication.class);
        application.run(args);
    }
}
