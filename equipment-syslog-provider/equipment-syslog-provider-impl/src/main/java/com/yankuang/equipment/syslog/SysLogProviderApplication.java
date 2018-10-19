package com.yankuang.equipment.syslog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.syslog",exclude = DataSourceAutoConfiguration.class)
public class SysLogProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SysLogProviderApplication.class);
        application.run(args);
    }
}
