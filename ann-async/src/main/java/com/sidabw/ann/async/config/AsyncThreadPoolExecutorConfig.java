package com.sidabw.ann.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author shaogz
 */
@Configuration
public class AsyncThreadPoolExecutorConfig {

    private static final Logger log = LoggerFactory.getLogger(AsyncThreadPoolExecutorConfig.class);

    @Bean({"liveMetaThreadPoolTaskExecutor"})
    public Executor getAsyncExecutor() {
        //为了测试DiscardOldestPolicy，所以设的比较小1，2，1
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(2);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("cc-async1-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        executor.setRejectedExecutionHandler(new MyAsyncUncaughtExceptionHandler());
        executor.initialize();
        log.info("MyAsyncConfigurer2 ThreadPoolTaskExecutor init success!");
        return executor;
    }

    @Bean({"liveMetaThreadPoolTaskExecutor2"})
    public Executor getAsyncExecutor2() {
        //为了测试DiscardOldestPolicy，所以设的比较小1，2，1
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(2);
        executor.setThreadNamePrefix("cc-async2-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        executor.setRejectedExecutionHandler(new MyAsyncUncaughtExceptionHandler());
        executor.initialize();
        log.info("MyAsyncConfigurer2 ThreadPoolTaskExecutor init success!");
        return executor;
    }
}
