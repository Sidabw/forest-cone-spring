/**
 * Copyright (C), 2018-2021, bokecc.com FileName: RedisCacheProperties Author:   shaogz Date:     2021/5/26 3:36 PM
 * Description: Redis Cache库的配置类 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.cache.basic2.config.conn.impl;

import com.sidabw.ann.cache.basic2.config.conn.AbstractRedisConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 〈一句话功能简述〉:
 * 〈Redis Cache库的配置类〉
 *
 * @author shaogz
 * @create 2021/5/26
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
//@ConfigurationProperties(prefix = "spring.redis-cache1")
@Configuration
public class Basic2Cache1Config extends AbstractRedisConfig {

    @Value("0")
    private int database;

    @Value("localhost")
    private String host;

    @Value("6379")
    private int port;

    @Value("")
    private String password;

    /**
     * 连接超时时间
     */
    @Value("2000")
    private int timeout;

    @Value(value = "${spring.redis-cache1.lettuce.pool.max-active:10}")
    private int storePoolMaxActive;

    @Value(value = "${spring.redis-cache1.lettuce.pool.max-idle:2}")
    private int storePoolMaxIdle;

    @Value(value = "${spring.redis-cache1.lettuce.pool.min-idle:0}")
    private int storePoolMinIdle;

    @Value(value = "${spring.redis-cache1.lettuce.pool.max-wait:5000}")
    private int storePoolMaxWait;

    @Value(value = "${spring.redis-cache1.lettuce.pool.time-between-eviction-runs:1800000}")
    private int storePoolEviction;

    @Override
    @Bean("redisCacheTemplate1")
    public RedisTemplate<String, Object> buildRedisTemplate(
        @Qualifier(value = "redisCacheConnectionFactory1") RedisConnectionFactory redisConnectionFactory) {
        return super.buildRedisTemplate(redisConnectionFactory);
    }

    @Override
    @Bean("redisCacheConnectionFactory1")
    @Primary
    public RedisConnectionFactory getRedisConnectionFactory() {
        return super.getRedisConnectionFactory();
    }

}
