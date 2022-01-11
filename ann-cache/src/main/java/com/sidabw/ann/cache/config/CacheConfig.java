/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: CacheConfig
 * Author:   feiyi
 * Date:     2021/3/22 4:51 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉:
 * 〈自定义CacheManager替代默认的CacheManager〉
 *
 * @author feiyi
 * @create 2021/3/22
 * @since 1.0.0
 */
@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CacheManager cacheManager() {
        LOGGER.debug("cache--create CacheManager!");
        //1. 第一版使用redis完成TTL的指定
        // RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        //         .entryTtl(Duration.ofDays(1))
        //         .computePrefixWith(cacheName -> "caching:" + cacheName);
        // return new CustomRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory()), defaultCacheConfig);

        //2. 第二版使用CaffeineCacheManager完成TTL的指定
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheNames(Collections.singletonList("def"));
        caffeineCacheManager.setCaffeine(
            Caffeine.newBuilder()
                //每次写入3秒后过期
                .expireAfterWrite(3, TimeUnit.SECONDS)
                //缓存最大条数
                .maximumSize(10));
        return caffeineCacheManager;
    }


    // @Value("${spring.redis.host}")
    // private String redisHost;
    //
    // @Value("${spring.redis.port}")
    // private Integer redisPort;
    //
    // @Value("${spring.redis.database}")
    // private Integer redisDatabase;
    //
    // @Bean
    // public RedisConnectionFactory redisConnectionFactory() {
    //     //还需要一个redisTemplate, 这个在bokeccprework.redis.RedisConfig下注册了
    //     LOGGER.debug("cache--create RedisConnectionFactory");
    //     RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    //     configuration.setHostName(redisHost);
    //     configuration.setPort(redisPort);
    //     configuration.setDatabase(redisDatabase);
    //     return new LettuceConnectionFactory(configuration);
    // }

}

// class CustomRedisCacheManager extends RedisCacheManager {
//
//     private static final Logger LOGGER = LoggerFactory.getLogger(CustomRedisCacheManager.class);
//
//     public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
//         super(cacheWriter, defaultCacheConfiguration);
//     }
//
//     @Override
//     protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
//         LOGGER.debug("cache--CustomRedisCacheManager createRedisCache");
//         String[] array = StringUtils.delimitedListToStringArray(name, "#");
//         name = array[0];
//         if (array.length > 1) {
//             long ttl = Long.parseLong(array[1]);
//             cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
//         }
//         return super.createRedisCache(name, cacheConfig);
//     }
// }