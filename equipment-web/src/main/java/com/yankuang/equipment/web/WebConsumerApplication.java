package com.yankuang.equipment.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.web")
public class WebConsumerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebConsumerApplication.class);
        application.run(args);
    }
}
