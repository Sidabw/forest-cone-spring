package com.sidabw.ann.cache.basic3.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class AllRedisCacheManager3 {

    public static final String CAFFEINE_CACHE_NAME = "def";

    @Bean(name = "caffeineCacheManager3")
    public CacheManager redisCacheManager3() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                //每次写入3秒后过期
                .expireAfterWrite(3, TimeUnit.SECONDS)
                //缓存最大条数
                .maximumSize(10);

        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheNames(Collections.singletonList(CAFFEINE_CACHE_NAME));
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }


}
