package com.sidabw.ann.cache2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author shaogz
 * @since 2024/2/17 23:54
 */
@SpringBootApplication
@EnableCaching
public class AnnCache2App {

    public static void main(String[] args) {
        SpringApplication.run(AnnCache2App.class, args);
    }

}
