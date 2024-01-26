# BASIC-2
* 使用CacheManager配置Multi-Redis-Cache
* 默认@Cacheable是不能指定redis的ttl的
  * 能指定一个全局的，但是不支持在@Cacheable直接指定 
  * 通过CustomTtlRedisCacheManager｜RedisCacheConfiguration自定义每个cache的ttl，而不是项目里所有ttl都是一样的
* 使用apache.commons-pool2对象池
