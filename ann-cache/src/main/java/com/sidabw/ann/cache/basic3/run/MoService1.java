package com.sidabw.ann.cache.basic3.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.sidabw.ann.cache.basic3.config.AllRedisCacheManager3.CAFFEINE_CACHE_NAME;

@Service
@Slf4j
public class MoService1 {

    private MoService2 moService2;

    @Cacheable(key = "'live:roomId1_' + #modelId", value = CAFFEINE_CACHE_NAME, cacheManager = "caffeineCacheManager3", unless = "#result == null")
    public MoEntity getEntity(String modelId) {
        log.info("here");
        return moService2.getEntity(modelId);
    }

    @Autowired
    public void setMoService2(MoService2 moService2) {
        this.moService2 = moService2;
    }
}
