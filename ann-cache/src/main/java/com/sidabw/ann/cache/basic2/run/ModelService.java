package com.sidabw.ann.cache.basic2.run;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.sidabw.ann.cache.basic2.config.AllRedisCacheManager.*;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/22
 * @since 1.0.0
 */
@Service
public class ModelService {


    /**
     * 使用缓存
     *
     *
     *
     *
     * @param modelId  key
     **/

    /**
     *  集合redis 支持消息过期时间的设置
     *  value = "model-meta#6" 结合CustomRedisCacheManager 表示6秒过期
     *
     *  <p>1. key : get model-meta::live:roomId_aa
     *  <p>2. value : \xac\xed\x00\x05sr\x00'com.sidabw.ann.cache.basic1.ModelEntityeB\x0e\x15\xb0\xb5\b\xc3\x02\x00\x02L\x00\bdescribet\x00\x12Ljava/lang/String;L\x00\x04nameq\x00~\x00\x01xpt\x00\x02ttt\x00\x01t
     *  <p>3. 直接缓存的对象，多个项目没法公用这一个缓存。
     *
     * @param modelId  modelId
     **/
    @Cacheable(key = "'live:roomId1_' + #modelId", value = CACHE_NAME_RECORD, cacheManager = "redisCacheManager1", unless = "#result == null")
    public ModelEntity getCache1(String modelId) throws InterruptedException {
        Thread.sleep(2 * 1000);
        return new ModelEntity("t-4-1", "tt-4-1");
    }

    @Cacheable(key = "'live:roomId2_' + #modelId", value = CACHE_NAME_LIVE, cacheManager = "redisCacheManager1",unless = "#result == null")
    public ModelEntity getCache2(String modelId) throws InterruptedException {
        Thread.sleep(2 * 1000);
        return new ModelEntity("t-4-2", "tt-4-2");
    }

    @Cacheable(key = "'live:roomId3_' + #modelId", value = CACHE_NAME_INTERACTION, cacheManager = "redisCacheManager2",unless = "#result == null")
    public ModelEntity getCache3(String modelId) throws InterruptedException {
        Thread.sleep(2 * 1000);
        return new ModelEntity("t-4-3", "tt-4-3");
    }

}
