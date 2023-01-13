package com.itmo.teachingeva;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itmo.teachingeva.mapper")
public class TeachingEvaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachingEvaApplication.class, args);
    }

}
