/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: MySchedulingTask
 * Author:   feiyi
 * Date:     2021/3/26 11:51 AM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.scheduled.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/26
 * @since 1.0.0
 */
@Component
@Slf4j
public class MySchedulingTask {

    private int num = 0;

    /**
     * 不配置线程池，任务之间是串行执行。
     * 但，mySchedulingTask是1个任务，mySchedulingTask2才是另外一个任务
     * fixedRate是1个任务多次执行，不是多个任务！
     *
     * 如果只有1个mySchedulingTask1，当前情况下依旧是每5秒执行一次。因为fixedRete就是这个意思啊
     * newScheduledThreadPool.scheduleAtFixedRate 啥意思，就是这个意思
     *
     * 测试：把MySchedulingConfigurer注释掉，就会发现每5秒（因为是睡5秒），只有一个任务在执行；
     * 打开，则是2个都在执行，这是我们想要的，多任务并行执行！
     *
     */
    // @Scheduled(fixedRate = 1000L)
    // public void mySchedulingTask1(){
    //     try {
    //         log.debug("task1 {} come in!", num++);
    //         Thread.sleep(5000L);
    //     } catch (Exception e) {
    //         log.error("", e);
    //     }
    // }
    //
    // @Scheduled(fixedRate = 1000L)
    // public void mySchedulingTask2(){
    //     try {
    //         log.debug("task2 {} come in!", num++);
    //         Thread.sleep(5000L);
    //     } catch (Exception e) {
    //         log.error("", e);
    //     }
    // }

}
