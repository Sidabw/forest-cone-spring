package com.sidabw.ann.cache.basic4.conf;

public class BypassOnErrRedisCacheWrapper {

    private final BypassOnErrRedisCache redisCache;

    private final Object lookupKey;

    public BypassOnErrRedisCacheWrapper(BypassOnErrRedisCache redisCache, Object lookupKey) {
        this.redisCache = redisCache;
        this.lookupKey = lookupKey;
    }

    public Object doLookUp(){
        return redisCache.realLookup(lookupKey);
    }

}
