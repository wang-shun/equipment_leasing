package com.yankuang.equipment.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@MapperScan("com.yankuang.equipment.authority.mapper")
@SpringBootApplication(scanBasePackages = "com.yankuang.equipment.authority")
public class AuthorityProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AuthorityProviderApplication.class);
        application.run(args);
    }
}
