package com.sidabw.ann.cache2.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author shaogz
 * @since 2024/2/18 00:02
 */
@Configuration
public class MyConfig {

    public static final String MEM_CACHE_ACCOUNT = "memAccount";
    public static final String CAFFEINE_ACCOUNT = "caffeineCacheManagerAccount";

    @Value("${spring.cache.caffeine.account.spec}")
    private String caffeineSpecAccount;


    @Bean(name = "caffeineConfigAccount")
    public Caffeine<Object, Object> caffeineConfigAccount() {
        Caffeine<Object, Object> from = Caffeine.from(caffeineSpecAccount);
        return from;
    }

    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        CacheLoader<Object, Object> cacheLoader = new CacheLoader<Object, Object>() {
            @Override
            public Object load(Object key) throws Exception {
                //返回null，spring会去调用被代理的真实方法，也就是@CacheAbale标记的方法
                return null;
            }
        };
        return cacheLoader;
    }

    @Bean(name = CAFFEINE_ACCOUNT)
    @Primary
    public CacheManager caffeineCacheManagerAccount(Caffeine<Object, Object> caffeineConfigAccount,
                                                    CacheLoader<Object, Object> cacheLoader) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheNames(Collections.singletonList(MEM_CACHE_ACCOUNT));
        caffeineCacheManager.setCacheLoader(cacheLoader);
        caffeineCacheManager.setCaffeine(caffeineConfigAccount);
        return caffeineCacheManager;
    }

}
