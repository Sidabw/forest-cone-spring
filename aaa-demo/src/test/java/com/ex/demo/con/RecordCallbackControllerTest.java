package com.ex.demo.con;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author shaogz
 * @since 2024/1/24 19:09
 */
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RecordCallbackControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordCallbackControllerTest.class);

    @Autowired
    private RecordCallbackController recordCallbackController;

    @Test
    public void test() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i<15; i++) {
            new Thread(() -> {
                Object forObject = restTemplate.getForObject("http://localhost:8889/recordCallback4", Object.class);
                LOGGER.info("got res:{}", forObject);
            }).start();
            TimeUnit.SECONDS.sleep(1);
        }
        TimeUnit.SECONDS.sleep(100);

        System.out.println(recordCallbackController);
    }

}