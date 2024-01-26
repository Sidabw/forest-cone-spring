package com.sidabw.ann.cache.basic4.conf;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;


@Slf4j
public class BypassOnErrRedisCache extends RedisCache {

    private final CircuitBreaker circuitBreaker;

    /**
     * UnaryOperator<Object> 等同于 Function<Object, Object>
     */
    private final UnaryOperator<Object> func = key -> {
        try {
            return super.lookup(key);
        } catch (Exception e) {
            log.error("get cache from redis fail, do real work then. msg {}", e.getMessage());
        }
        //返回null了，自然就会去调用'被代理对象'
        return null;
    };

    protected BypassOnErrRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig,
                                    CircuitBreaker circuitBreaker) {
        super(name, cacheWriter, cacheConfig);
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    protected Object lookup(@NonNull Object key) {
        //测试结果是 慢调用生效了。
        Supplier<Object> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, () -> func.apply(key));
        //从异常中恢复：'doSomething'抛异常了，此时返回 'Hello from Recovery'，而不是把异常抛出来
        return Try.ofSupplier(decoratedSupplier).recover(throwable -> null).get();
    }

    @Override
    public void put(@NonNull Object key, @Nullable Object value) {
        try {
            super.put(key, value);
        } catch (Exception e) {
            log.error("write cache fail, bypass it. msg {}", e.getMessage());
        }
    }


}
