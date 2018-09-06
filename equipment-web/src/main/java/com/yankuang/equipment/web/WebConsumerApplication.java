package com.yankuang.equipment.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.web",exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class WebConsumerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebConsumerApplication.class);
        application.run(args);
    }
}
