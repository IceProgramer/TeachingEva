package com.itmo.teachingeva;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.itmo.teachingeva.mapper")
@EnableScheduling
public class TeachingEvaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachingEvaApplication.class, args);
    }

}
