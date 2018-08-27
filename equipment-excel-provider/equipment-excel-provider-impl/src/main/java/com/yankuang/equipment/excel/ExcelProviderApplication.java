package com.yankuang.equipment.excel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xhh
 * @date 2018/8/15 13:55
 */

@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.excel")
public class ExcelProviderApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ExcelProviderApplication.class);
        application.run(args);
    }
}
