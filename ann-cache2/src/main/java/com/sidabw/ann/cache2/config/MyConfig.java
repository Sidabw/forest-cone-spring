package com.sidabw.ann.cache2.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;

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


//    @Bean
//    public CacheLoader<Object, Object> cacheLoader() {
//        CacheLoader<Object, Object> cacheLoader = new CacheLoader<Object, Object>() {
//            @Override
//            public Object load(Object key) throws Exception {
//                return null;
//            }
//            // 重写这个方法将oldValue值返回回去，进而刷新缓存
//            @Override
//            public Object reload(Object key, Object oldValue) throws Exception {
//                return oldValue;
//            }
//        };
//        return cacheLoader;
//    }

    @Bean(name = "caffeineConfigAccount")
    public Caffeine<Object, Object> caffeineConfigAccount() {
        return Caffeine.from(caffeineSpecAccount);
    }

    @Bean(name = CAFFEINE_ACCOUNT)
    @Primary
    public CacheManager caffeineCacheManagerAccount(@Qualifier(value = "caffeineConfigAccount")
                                                        Caffeine<Object, Object> caffeineConfigAccount) {
        //,CacheLoader<Object, Object> cacheLoader

        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheNames(Collections.singletonList(MEM_CACHE_ACCOUNT));
//        caffeineConfigAccount.recordStats();
        caffeineCacheManager.setCaffeine(caffeineConfigAccount);

//        caffeineCacheManager.setCacheLoader(cacheLoader);
        return caffeineCacheManager;
    }

}
