package com.yankuang.equipment.equipment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.equipment")
@MapperScan("com.yankuang.equipment.equipment.mapper")
public class EquipmentProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EquipmentProviderApplication.class);
        application.run(args);
    }
}
