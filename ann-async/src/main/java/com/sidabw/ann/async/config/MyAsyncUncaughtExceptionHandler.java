package com.sidabw.ann.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shaogz
 */
public class MyAsyncUncaughtExceptionHandler implements RejectedExecutionHandler {

    private static final Logger log = LoggerFactory.getLogger(MyAsyncUncaughtExceptionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        //原有的DiscardOldestPolicy，会抛弃队头的任务
        //新增该类的原因只是为了额外输出一行日志，没错.
        if (!e.isShutdown()) {
            log.warn("oldest task discarded! {}", e.getQueue().poll());
            e.execute(r);
        }
    }
}