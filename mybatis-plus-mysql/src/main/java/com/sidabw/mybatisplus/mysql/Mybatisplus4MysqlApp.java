package com.sidabw.mybatisplus.mysql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shaogz
 */
@SpringBootApplication
@MapperScan("com.sidabw.mybatisplus.mysql.generator.mapper")
public class Mybatisplus4MysqlApp {

    public static void main(String[] args) {
        SpringApplication.run(Mybatisplus4MysqlApp.class, args);
    }
}
