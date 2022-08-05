package com.sidabw.ann.cache.basic4.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;


@Slf4j
public class BypassOnErrRedisCache extends RedisCache {

    protected BypassOnErrRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
    }

    @Override
    protected Object lookup(Object key) {
        try {
            return super.lookup(key);
        } catch (Exception e) {
            log.error("get cache from redis fail, do real work then. msg {}", e.getMessage());
        }
        //返回null了，自然就会去调用'被代理对象'
        return null;
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        try {
            super.put(key, value);
        } catch (Exception e) {
            log.error("write cache fail, bypass it. msg {}", e.getMessage());
        }
    }


}
