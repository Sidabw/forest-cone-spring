package com.sidabw.ann.cache2.ser.impl;

import com.sidabw.ann.cache2.ser.ICache2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.sidabw.ann.cache2.config.MyConfig.CAFFEINE_ACCOUNT;
import static com.sidabw.ann.cache2.config.MyConfig.MEM_CACHE_ACCOUNT;


/**
 * @author shaogz
 * @since 2024/2/17 23:57
 */
@Service
@Slf4j
public class Cache2ServiceImpl implements ICache2Service {


    @Override
    @Cacheable(key = "'accountInfo:' + #id", value = MEM_CACHE_ACCOUNT, cacheManager = CAFFEINE_ACCOUNT)
    public Object getObj(String id) {
        log.info("come in");
        return Collections.singletonMap("a", "a-value22");
    }
}
