///**
// * Copyright (C), 2018-2021, zenki.ai
// * FileName: ModelService
// * Author:   feiyi
// * Date:     2021/3/22 4:19 PM
// * Description:
// * History:
// * <author>          <time>          <version>          <desc>
// * 作者姓名           修改时间           版本号              描述
// */
//package com.sidabw.ann.cache.basic1;
//
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
///**
// * 〈一句话功能简述〉:
// * 〈〉
// *
// * @author feiyi
// * @create 2021/3/22
// * @since 1.0.0
// */
//@Service
//public class ModelService {
//
//
//    /**
//     * 使用缓存
//     * <p>1. key : get model-meta::live:roomId_aa
//     * <p>2. value : \xac\xed\x00\x05sr\x00'com.sidabw.ann.cache.basic1.ModelEntityeB\x0e\x15\xb0\xb5\b\xc3\x02\x00\x02L\x00\bdescribet\x00\x12Ljava/lang/String;L\x00\x04nameq\x00~\x00\x01xpt\x00\x02ttt\x00\x01t
//     * <p>3. 直接缓存的对象，多个项目没法公用这一个缓存。
//     *
//     * @param modelId  key
//     **/
//    @Cacheable(key = "'live:roomId_' + #modelId", value = "model-meta", unless = "#result == null")
//    public ModelEntity getById(String modelId) throws InterruptedException {
//        Thread.sleep(2 * 1000);
//        return new ModelEntity("t", "tt");
//    }
//
//    /**
//     *  缓存更新
//     * @param modelId  key
//     **/
//    @CachePut(key = "'live:roomId_' + #modelId", value = "model-meta", unless = "#result == null")
//    public ModelEntity updateById(String modelId) {
//        return new ModelEntity("t-2", "tt-2");
//    }
//
//    /**
//     *  删除缓存
//     * @param modelId  modelId
//     **/
//    @CacheEvict(key = "'live:roomId_' + #modelId", value = "model-meta")
//    public ModelEntity delById(String modelId) {
//        return new ModelEntity("t-3", "tt-3");
//    }
//
////    /**
////     *  集合redis 支持消息过期时间的设置
////     *  value = "model-meta#6" 结合CustomRedisCacheManager 表示6秒过期
////     * @param modelId  modelId
////     **/
////    @Cacheable(key = "'live:roomId_' + #modelId", value = "model-meta#4", unless = "#result == null")
////    public ModelEntity springCacheWithRedisTTLSupport(String modelId) throws InterruptedException {
////        Thread.sleep(2 * 1000);
////        return new ModelEntity("t-4", "tt-4");
////    }
//
//}
