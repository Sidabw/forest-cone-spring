package com.sidabw.ann.cache.basic4.conf;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Duration;

@Slf4j
public class BypassOnErrRedisCacheManager extends RedisCacheManager {

    private final RedisCacheWriter cacheWriter;

    private final CircuitBreaker circuitBreaker;

    public BypassOnErrRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                        String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);

        this.cacheWriter = cacheWriter;
        this.circuitBreaker = createCircuitBreaker();
    }

    @Override
    @NonNull
    protected RedisCache createRedisCache(@NonNull String name, @Nullable RedisCacheConfiguration cacheConfig) {
        log.info("createRedisCache, manager name:{}", name);
        return new BypassOnErrRedisCache(name, cacheWriter, cacheConfig, circuitBreaker);
    }

    private CircuitBreaker createCircuitBreaker() {

        // 为断路器创建自定义的配置
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                //窗口大小为2，阈值50%，即两个请求里有1个失败，就打开断路器
                .failureRateThreshold(50)
                .slidingWindowSize(2)
                //断路器从开启过渡到半开应等待的时间。
                .waitDurationInOpenState(Duration.ofMillis(10000))
                //半开情况下允许通过的请求数。这些请求完事后，超过阈值则回到全开，低于阈值则降低到关闭。
                .permittedNumberOfCallsInHalfOpenState(2)
                //慢调用阈值，50%
                .slowCallRateThreshold(50)
                //方法调用时间超过500ms就算'慢调用'
                .slowCallDurationThreshold(Duration.ofMillis(500))
                .recordExceptions(Throwable.class)
                //要记录哪些异常，不过话说，这些CheckedException都不可能抛出来啊...
//                .recordExceptions(IOException.class, TimeoutException.class)
                //排除哪些异常
//                .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
                .build();

        // 使用自定义的全局配置创建CircuitBreakerRegistry
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        //
        return circuitBreakerRegistry.circuitBreaker("name");

    }

}
