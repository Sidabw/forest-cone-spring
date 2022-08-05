//package com.sidabw.ann.cache.basic1;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//
//@Configuration
//@EnableCaching
//@Slf4j
//public class Basic1Config {
//
//     @Value("${spring.redis.host:localhost}")
//     private String redisHost;
//
//     @Value("${spring.redis.port:6379}")
//     private Integer redisPort;
//
//     @Value("${spring.redis.database:0}")
//     private Integer redisDatabase;
//
//     @Bean
//     public RedisConnectionFactory redisConnectionFactory() {
//         log.debug("cache--create RedisConnectionFactory");
//         RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//         configuration.setHostName(redisHost);
//         configuration.setPort(redisPort);
//         configuration.setDatabase(redisDatabase);
//         return new LettuceConnectionFactory(configuration);
//     }
//
//
//}
