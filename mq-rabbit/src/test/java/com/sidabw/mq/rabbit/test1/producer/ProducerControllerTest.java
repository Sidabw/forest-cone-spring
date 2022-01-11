package com.sidabw.mq.rabbit.test1.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (C), 2018-2021, bokecc.com FileName: ProducerControllerTest Author:   shaogz Date:     2021/5/6 9:02 PM
 * Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerControllerTest {

    // @Autowired
    // ProducerController producerController;

    @Test
    public void testSendMessage2() throws JsonProcessingException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        int i = 1000;
        while (i >0) {
            executorService.execute(() ->{
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();
                    // producerController.sendMessage2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            i--;
        }
    }
}