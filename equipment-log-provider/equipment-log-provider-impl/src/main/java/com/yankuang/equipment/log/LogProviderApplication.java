package com.yankuang.equipment.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.log")
public class LogProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LogProviderApplication.class);
        application.run(args);
    }
}
