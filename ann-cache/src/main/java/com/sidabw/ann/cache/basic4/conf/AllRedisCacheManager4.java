package com.sidabw.ann.cache.basic4.conf;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
@EnableCaching
@Slf4j
public class AllRedisCacheManager4 {

    public static final String BYPASS_ON_ERROR_CACHE_NAME = "bpoe";

    @Bean(name = "cacheManager4")
    public CacheManager redisCacheManager4(@Qualifier(value = "redisCacheTemplate2") RedisTemplate<String, Object> redisCacheTemplate) {
        RedisConnectionFactory connectionFactory = redisCacheTemplate.getConnectionFactory();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        assert connectionFactory != null;
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        CircuitBreaker circuitBreaker = createCircuitBreaker();
        return new BypassOnErrRedisCacheManager(redisCacheWriter, config, circuitBreaker, BYPASS_ON_ERROR_CACHE_NAME);
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
