/**
 * Copyright (C), 2018-2021, bokecc.com FileName: MqBrokerTest Author:   shaogz Date:     2021/5/7 10:29 AM Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mq.rabbit.test2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sidabw.mq.rabbit.test.producer.ProducerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author shaogz
 * @create 2021/5/7
 * @since 1.0.0
 */
@SpringBootTest
public class ProducerControllerTest {

    @Autowired
    ProducerController producerController;

    @Test
    public void testSendMessage2() throws JsonProcessingException, InterruptedException {
        System.out.println(1);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        // CountDownLatch countDownLatch = new CountDownLatch(1000);
        int i = 1;
        while (i >0) {
            executorService.execute(() ->{
                try {
                    // countDownLatch.countDown();
                    // countDownLatch.await();
                    producerController.sendMessage2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            i--;
        }
        Thread.sleep(1000L);
        // Thread.sleep(100000L);
    }
}
