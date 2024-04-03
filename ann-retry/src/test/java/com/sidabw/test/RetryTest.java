package com.sidabw.test;

import com.sidabw.retry.AnnRetryApplication;
import com.sidabw.retry.service.RetryTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author shaogz
 * @since 2024/4/3 09:09
 */
@SpringBootTest(classes = AnnRetryApplication.class)
public class RetryTest {

    @Autowired
    private RetryTestService retryTestService;

    @Test
    void test1(){
        System.out.println(retryTestService.getObj(123));
        System.out.println(1);
    }
}
