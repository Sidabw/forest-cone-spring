/**
 * Copyright (C), 2018-2021, bokecc.com FileName: AbstractRedisConfig Author:   shaogz Date:     2021/5/26 5:43 PM
 * Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.cache.basic2.config.conn;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 〈一句话功能简述〉:
 * 〈构建RedisTemplate的抽象类〉
 *
 * @author shaogz
 * @create 2021/5/26
 * @since 1.0.0
 */
public abstract class AbstractRedisConfig {

    protected abstract int getDatabase();

    protected abstract String getHost();

    protected abstract int getPort();

    protected abstract String getPassword();

    protected abstract int getTimeout();

    protected abstract int getStorePoolMaxActive();

    protected abstract int getStorePoolMaxIdle();

    protected abstract int getStorePoolMinIdle();

    protected abstract int getStorePoolMaxWait();

    protected abstract int getStorePoolEviction();

    /**
     * @Description  构建1个RedisTemplate
     * @return: org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.lang.Object>
     * @Author: shaogz
     * @Date: 2021/5/26 5:44 PM
     **/
    protected RedisTemplate<String, Object> buildRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        // 创建一个模板类
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //将刚才的redis连接工厂设置到模板类中
        template.setConnectionFactory(redisConnectionFactory);
        // 设置key的序列化器
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        // 设置value的序列化器
        StringRedisSerializer valueSerializer = new StringRedisSerializer();
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        return template;
    }

    /**
     * 拿子类的配置构建1个RedisConnectionFactory
     * @return org.springframework.data.redis.connection.RedisConnectionFactory
     * @Author: shaogz
     * @Date: 2021/5/26 6:04 PM
     **/
    protected RedisConnectionFactory getRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(this.getDatabase());
        redisStandaloneConfiguration.setHostName(this.getHost());
        redisStandaloneConfiguration.setPort(this.getPort());
        redisStandaloneConfiguration.setPassword(this.getPassword());

        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        //空闲对象的最大数量
        genericObjectPoolConfig.setMaxIdle(this.getStorePoolMaxIdle());
        //空闲对象的最小数量
        genericObjectPoolConfig.setMinIdle(this.getStorePoolMinIdle());
        //池化对象的最大数量
        genericObjectPoolConfig.setMaxTotal(this.getStorePoolMaxActive());
        //借用对象时可以等待的最长时间。
        genericObjectPoolConfig.setMaxWaitMillis(this.getStorePoolMaxWait());
        //空闲检测的周期
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(this.getStorePoolEviction());

        /*
        * Lettuce， redis java客户端之一，
        * */
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(this.getTimeout()))
                .poolConfig(genericObjectPoolConfig)
                .build();

        LettuceConnectionFactory lettuceConnectionFactory =
            new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        //不加这个redisTemplate.opsForValue().get()会NPE
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

}
