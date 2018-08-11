package com.yankuang.equipment.syslog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.syslog")
public class SysLogProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SysLogProviderApplication.class);
        application.run(args);
    }
}
