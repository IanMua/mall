package com.ianmu.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearnMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnMallApplication.class, args);
    }

}
