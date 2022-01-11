/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: ModelService
 * Author:   feiyi
 * Date:     2021/3/22 4:19 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.cache.test;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
     * @Description 使用缓存
     * @param modelId  key
     **/
    @Cacheable(key = "'live:roomId_' + #modelId", value = "${spring.cache.cache-names}", unless = "#result == null")
    public ModelEntity getById(String modelId) throws InterruptedException {
        Thread.sleep(2 * 1000);
        return new ModelEntity("t", "tt");
    }

    /**
     * @Description 缓存更新
     * @param modelId  key
     **/
    @CachePut(key = "'live:roomId_' + #modelId", value = "model-meta", unless = "#result == null")
    public ModelEntity updateById(String modelId) {
        return new ModelEntity("t-2", "tt-2");
    }

    /**
     * @Description 删除缓存
     * @param modelId  modelId
     **/
    @CacheEvict(key = "'live:roomId_' + #modelId", value = "model-meta")
    public ModelEntity delById(String modelId) {
        return new ModelEntity("t-3", "tt-3");
    }

    /**
     * @Description 集合redis 支持消息过期时间的设置
     *  value = "model-meta#6" 结合CustomRedisCacheManager 表示6秒过期
     * @param modelId  modelId
     **/
    @Cacheable(key = "'live:roomId_' + #modelId", value = "model-meta#4", unless = "#result == null")
    public ModelEntity springCacheWithRedisTTLSupport(String modelId) throws InterruptedException {
        Thread.sleep(2 * 1000);
        return new ModelEntity("t-4", "tt-4");
    }

}
