package com.sidabw.ann.cache2;

import com.sidabw.ann.cache2.ser.ICache2Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author shaogz
 * @since 2024/2/17 23:55
 */
@SpringBootTest
public class AnnCache2AppTest {

    @Autowired
    private ICache2Service cache2Service;

    @Test
    void test() throws InterruptedException {
        System.out.println(1);
        System.out.println(cache2Service.getObj("1"));
        System.out.println(cache2Service.getObj("1"));
        TimeUnit.SECONDS.sleep(10);
        System.out.println(cache2Service.getObj("1"));
        TimeUnit.SECONDS.sleep(5);
        System.out.println(cache2Service.getObj("1"));

    }
}
