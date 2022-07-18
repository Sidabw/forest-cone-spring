package com.sidabw.ann.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * from： https://www.jianshu.com/p/9a80c662dac4
 * @author shaogz
 */
public class CaffeineDemo {

    @Test
    public void test1() throws InterruptedException {
        //填充策略：手动加载
        Cache<String, String> caffeineCache =
            Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(2).build();
        //拿
        String value = caffeineCache.getIfPresent("a");
        System.out.println(value);
        //放
        caffeineCache.put("a", "a-v");
        System.out.println(caffeineCache.getIfPresent("a"));
        caffeineCache.put("b", "b-v");
        caffeineCache.put("c", "c-v");
        //暂时不细究，但是不睡这两秒，maximumSize就不生效
        //猜测与Caffeine的 window TinyLfu回收策略有关。: LFU(Least frequently used) 最不经常使用
        Thread.sleep(2000L);
        System.out.println(caffeineCache.getAllPresent(Arrays.asList("a", "b", "c")));
        //删
        caffeineCache.invalidate("a");
        System.out.println(caffeineCache.getIfPresent("a"));

        //高级一点的拿
        String d = caffeineCache.get("d", k -> {
            // 满足大多数拿不到就...然后再放到缓存中的场景
            // String dV = getFromDb(k);
            return "d-v";
        });
        //get方法是阻塞的，以此保证并发时只有1个线程写入缓存
        System.out.println(d);
    }

    public static void main(String[] args) {
        Date date = new Date(1649318408000L);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date));
    }

    @Test
    public void test2() {
        //填充策略：同步加载
        LoadingCache<Object, String> loadingCache =
            Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(2).build(key -> {
                // String dV = getFromDb(k);
                return "d-v";
            });
        //此时拿不到的化，就会执行注册的函数
        String aV = loadingCache.get("a");
        //d-v
        System.out.println(aV);
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        //填充策略：异步加载
        //默认使用的线程池：ForkJoinPool.commonPool()，可以调用Caffeine.executor(Executor) 来替换
        AsyncLoadingCache<Object, String> asyncLoadingCache =
            Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(2).buildAsync(key -> {
                // String dV = getFromDb(k);
                return "d-v";
            });
        CompletableFuture<String> completableFuture = asyncLoadingCache.get("a");
        System.out.println(completableFuture.get());
    }

    @Test
    public void test4() throws InterruptedException {

        //refreshAfterWrite的测试
        LoadingCache<String, Object> graphs = Caffeine.newBuilder()
            .maximumSize(10_000)
            // 指定在创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
            //这个刷新是惰性的，会在get的时候触发
            //怎么刷新的，自然就是注册的给Caffeine的函数
            .refreshAfterWrite(2, TimeUnit.SECONDS)
            .build(key -> {
                System.out.println(1);
                return "ddd";
            });
        System.out.println(graphs.get("a"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println(graphs.get("a"));
        TimeUnit.SECONDS.sleep(10);

        //1.refreshAfterWrite将在查询数据的时候判断该数据是不是符合查询条件，如果符合条件该缓存就会去执行刷新操作。
        //2.refreshAfterWrite和expireAfterWrite是可以同时使用的。详细参见博客把。
    }

    @Test
    public void test5(){
        //驱逐策略：大小、时间、引用类型。官网有说引用类型这个会降低性能不建议使用
        //其他还有监听器、Writer,未测试
    }
}
