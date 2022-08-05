package com.sidabw.ann.cache.basic4.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Slf4j
public class BypassOnErrRedisCacheManager extends RedisCacheManager {

    private final RedisCacheWriter cacheWriter;

    public BypassOnErrRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                        String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);

        this.cacheWriter = cacheWriter;
    }

    @Override
    @NonNull
    protected RedisCache createRedisCache(@NonNull String name, @Nullable RedisCacheConfiguration cacheConfig) {
        log.info("createRedisCache, manager name:{}", name);
        return new BypassOnErrRedisCache(name, cacheWriter, cacheConfig);
    }

}
