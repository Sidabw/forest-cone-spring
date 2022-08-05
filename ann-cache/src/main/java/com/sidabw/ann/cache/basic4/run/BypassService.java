package com.sidabw.ann.cache.basic4.run;

import com.sidabw.ann.cache.basic3.run.MoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.sidabw.ann.cache.basic4.conf.AllRedisCacheManager4.BYPASS_ON_ERROR_CACHE_NAME;

@Service
@Slf4j
public class BypassService {


    @Cacheable(key = "'live:roomId1_' + #modelId", value = BYPASS_ON_ERROR_CACHE_NAME, cacheManager = "cacheManager4", unless = "#result == null")
    public BypassEntity getEntity(String modelId) {
        try {
            log.info("here");
            Thread.sleep(2 * 10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new BypassEntity("t-4-1", "tt-4-1");
    }
}
