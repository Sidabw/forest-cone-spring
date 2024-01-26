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

        return new BypassOnErrRedisCacheManager(redisCacheWriter, config, BYPASS_ON_ERROR_CACHE_NAME);
    }
}
