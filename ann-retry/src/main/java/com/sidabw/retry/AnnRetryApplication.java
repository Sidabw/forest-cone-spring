package com.sidabw.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author shaogz
 * @since 2024/4/3 09:03
 */
@SpringBootApplication
@EnableRetry
public class AnnRetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnRetryApplication.class, args);
    }
}
