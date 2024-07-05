# ann-cache
> 可参考broadcast项目、static-metadata

* 参考子目录下的README
* 包含redis缓存 和 caffeine缓存

## @Cacheable

对应CacheManager是redis时,对应到redis中key是：value::key，如下图，实际的key是 ams::accountInfo:xxx

```java
@Cacheable(key = "'accountInfo:' + #accountId", value = "ams", cacheManager = CacheManagerConfig.CAFFEINE_ACCOUNT)
```


## StringRedisTemplate 与 RedisTemplate 的区别
* StringRedisTemplate 继承了 RedisTemplate。
* RedisTemplate 是一个泛型类，而 StringRedisTemplate 则不是。
* .StringRedisTemplate 只能对 key=String，value=String 的键值对进行操作，RedisTemplate 可以对任何类型的 key-value 键值对操作。
* 他们各自序列化的方式不同，但最终都是得到了一个字节数组，殊途同归，StringRedisTemplate 使用的是 StringRedisSerializer 类；RedisTemplate 使用的是 JdkSerializationRedisSerializer 类。反序列化，则是一个得到 String，一个得到 Object
* 两者的数据是不共通的，StringRedisTemplate 只能管理 StringRedisTemplate 里面的数据，RedisTemplate 只能管理 RedisTemplate中 的数据。