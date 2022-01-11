/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: AsyncService
 * Author:   feiyi
 * Date:     2021/3/25 6:08 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.async.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/25
 * @since 1.0.0
 */
@Service
public class AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Async("liveMetaThreadPoolTaskExecutor")
    public void asyncTest(Person2 person2){
        try {
            log.warn("asyncTest1, {}, {}", Thread.currentThread().getName(), person2.getValue());
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("liveMetaThreadPoolTaskExecutor2")
    public void asyncTest2(Person2 person2) {
        try {
            log.warn("asyncTest1, {}, {}", Thread.currentThread().getName(), person2.getValue());
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
