package com.sidabw.forest.cone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sidabw.forest.cone.mybatisplus.mapper")
public class ForestConeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForestConeApplication.class, args);
    }

}
