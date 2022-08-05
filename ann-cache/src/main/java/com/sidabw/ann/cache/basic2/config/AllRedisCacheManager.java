package com.sidabw.ann.cache.basic2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@Slf4j
public class AllRedisCacheManager {

    /**
     * 查询live.record缓存ttl 1小时
     */
    public static final String CACHE_NAME_RECORD = "static:metadata:record#20";

    /**
     * 查询live.live缓存ttl 1小时
     */
    public static final String CACHE_NAME_LIVE = "static:metadata:live#10";

    /**
     * 查询live.interaction 缓存ttl：
     */
    public static final String CACHE_NAME_INTERACTION = "static:metadata:interaction#15";

    @Bean(name = "redisCacheManager1")
    @Primary
    public CacheManager redisCacheManager1(@Qualifier(value = "redisCacheTemplate1") RedisTemplate<String, Object> redisCacheTemplate) {
        RedisConnectionFactory connectionFactory = redisCacheTemplate.getConnectionFactory();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        //config.entryTtl() 可以在这里类上设置ttl，当然现在我们是不需要的
        //config.disableCachingNullValues() 这个挺有用，不用总是去写 unless = "#result == null"
        //config.serializeKeysWith()
        //config.serializeValuesWith()
        assert connectionFactory != null;
        //就只有两个，一个阻塞，一个非阻塞，
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        /*
         * 1. 一个cache name 对应一个  RedisCacheConfiguration
         * 2. ttl得在RedisCacheConfiguration配置
         * 3. cache name就是 @Cacheable的value，可以认为就是一个cache
         * 4. 当前"redisCacheManager1"是：一个CacheManager对应着多个cache
         * 5. 一个CacheManager只能对应一个RedisConnectionFactory
         */
        String[] cacheNames = new String[]{CACHE_NAME_RECORD, CACHE_NAME_LIVE};
        return new CustomTtlRedisCacheManager(redisCacheWriter, config, cacheNames);
    }

    @Bean(name = "redisCacheManager2")
    public CacheManager redisCacheManager2(@Qualifier(value = "redisCacheTemplate2") RedisTemplate<String, Object> redisCacheTemplate) {
        RedisConnectionFactory connectionFactory = redisCacheTemplate.getConnectionFactory();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        assert connectionFactory != null;
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        String[] cacheNames = new String[]{CACHE_NAME_INTERACTION};
        return new CustomTtlRedisCacheManager(redisCacheWriter, config, cacheNames);
    }


}
