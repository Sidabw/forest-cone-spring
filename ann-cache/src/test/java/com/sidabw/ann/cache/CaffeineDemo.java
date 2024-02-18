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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * from： https://www.jianshu.com/p/9a80c662dac4
 * @author shaogz
 */
public class CaffeineDemo {

    /*
    1。 Cache 和 LoadingCache的区别
        都是接口，LoadingCache是Cache的子类，覆盖了Cache的功能如下：
        get的时候，LoadingCache会根据构造传入的CacheLoader，在给入key没有对应value的时候，去执行CacheLoader拿到value再写入再返回

        额外提供的功能如下：
        refresh，异步的更新缓存。如果更新的时候有其他线程拿值，不会阻塞，会拿到旧的。
        换句话说，refreshAfterWrite什么意思？就是到点了其他线程来拿的时候，会立刻返回旧的，同时异步的去刷新。非常神奇...
        expireAfterWrite自然就是你更新了一个缓存，这个缓存过多久失效。
        真说他俩有啥区别，一个是框架的scheduler给你把缓存删了，另外一个需要你get的时候触发，但不是阻塞你的get，会立刻返回你一个旧的。
        没啥好坏，场景不同。
        真心不知道这到底得是多复杂的缓存业务，才会用到这些东西
     */
    @Test
    public void test1() throws InterruptedException {
        //填充策略：手动加载，即缓存的写入需要手动调用put完成。
        Cache<String, String> caffeineCache = Caffeine.newBuilder().maximumSize(2).build();
        //放
        caffeineCache.put("a", "a-v");
        //拿
        String value = caffeineCache.getIfPresent("a");
        System.out.println(value);
        //拿不到时执行写入。多线程下，只有一个线程能够成功写入，另外一个线程会阻塞。
        String d = caffeineCache.get("d", k -> {
            // 满足大多数"拿不到就...然后再放到缓存中"的场景
            // String dV = getFromDb(k);
            return "d-v";
        });
        System.out.println(d);

        //主动驱逐
        caffeineCache.invalidate("a");
        System.out.println(caffeineCache.getIfPresent("a"));

        //触发基于大小的驱逐策略
        caffeineCache.put("b", "b-v");
        caffeineCache.put("c", "c-v");
        //需要睡2s，猜测跟W-TinyLFU有关
        Thread.sleep(2000L);
        System.out.println(caffeineCache.getAllPresent(Arrays.asList("a", "b", "c")));
    }

    @Test
    public void test2() {
        //填充策略：自动加载
        LoadingCache<Object, String> loadingCache = Caffeine.newBuilder().maximumSize(2).build(key -> {return "d-v";});
        //此时拿不到的化，就会执行注册的函数；同样的，多线程下，只有一个线程能够成功写入，另外一个线程会阻塞
        String aV = loadingCache.get("a");
        //d-v
        System.out.println(aV);
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        //忽略
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

        AtomicInteger atomicInteger = new AtomicInteger();
        //refreshAfterWrite的测试
        LoadingCache<String, Object> graphs = Caffeine.newBuilder()
            .maximumSize(10_000)
            //指定在创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
            //这个刷新是惰性的，会在get的时候触发，而且触发时会先返回旧值
            //怎么刷新的，自然就是注册的给Caffeine的函数
            .refreshAfterWrite(3, TimeUnit.SECONDS)
            .build(key -> {
                System.out.println("构造开始");
                String result = "dd " + atomicInteger.getAndIncrement();
                try {
                    TimeUnit.SECONDS.sleep(2);
                }catch (Exception e){}
                System.out.println("构造结束");
                return result;
            });
        //主线程先把值写进去
        System.out.println("第一次取值：");
        System.out.println(graphs.get("a"));
        //到达触发刷新的节点
        TimeUnit.SECONDS.sleep(7);
        System.out.println("到达触发刷新的节点");

        //两个线程同时去拿这个值
        //现象就是，这俩有一个会触发build时传入的CacheLoader，
        //但这俩都会立刻返回拿到旧的值,,,
        //直到最后的 '线程3:' 才会拿到这哥俩其中的一个异步刷新后的值
        //至于'线程3:'刷新后的值，没人拿嘛..旧不说了..【一共执行了三次CacheLoader】
        new Thread(() -> {
            System.out.println("线程1:" + graphs.get("a"));
        }).start();


        new Thread(() -> {
            System.out.println("线程2:" + graphs.get("a"));
        }).start();

        TimeUnit.SECONDS.sleep(10);

        System.out.println("线程3:" + graphs.get("a"));

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
