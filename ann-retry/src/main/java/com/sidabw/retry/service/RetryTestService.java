package com.sidabw.retry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author shaogz
 * @since 2024/4/3 09:08
 */
@Service
@Slf4j
public class RetryTestService {


    @Recover
    public Object getObjRecover(RuntimeException throwable, int p1) {
        log.info("recover coming in. original ex: {}. original params:{}", throwable.getMessage(), p1);
        return Collections.singletonMap("a", "d");
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000L, maxDelay = 80000L, multiplier = 2))
    public Object getObj(int p1){
        //最多尝试执行3次。如果有失败，那么重试间隔按照@Backoff配置的*2来，也就是第一次5s间隔，第二次10s间隔，第三次20s，第四次40s...
        //recover将在最终失败后执行，坑：Retryable.recover写上上面的方法名就不行，不写就ok...
        log.info("coming in p1: " + p1);
        throw new RuntimeException("xxx");
        //, listeners = "taskListener"

    }
}
