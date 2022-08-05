package com.sidabw.ann.cache.basic3.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.sidabw.ann.cache.basic2.config.AllRedisCacheManager.CACHE_NAME_RECORD;

@Service
@Slf4j
public class MoService2 {

    @Cacheable(key = "'live:roomId1_' + #modelId", value = CACHE_NAME_RECORD, cacheManager = "redisCacheManager1", unless = "#result == null")
    public MoEntity getEntity(String modelId) {
        try {
            log.info("here");
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new MoEntity("t-4-1", "tt-4-1");
    }

}
