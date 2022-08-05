package com.sidabw.ann.cache.basic2.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public class CustomTtlRedisCacheManager extends RedisCacheManager {

    public CustomTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                   String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
    }

    @Override
    @NonNull
    protected RedisCache createRedisCache(@NonNull String name, @Nullable RedisCacheConfiguration cacheConfig) {
        log.info("createRedisCache, manager name:{}", name);
        if (Objects.isNull(cacheConfig)) {
            cacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        }
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        if (array.length > 1) {
            long ttl = Long.parseLong(array[1]);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
        } else {
            throw new IllegalArgumentException("illegal cache name detected:" + name);
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
