# BASIC-3
> 与basic2 同时使用，basic2里有两个CacheManager了，这里再加一个
* 使用CaffeineCacheManager完成三级缓存
* 一级：真实方法调用【往往是查数据库】
* 二级：redis-cache
* 三级：内存缓存，caffeine-cache
* test 目录有caffeine的demo