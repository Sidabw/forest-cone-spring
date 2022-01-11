/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: RedisSpringTest
 * Author:   feiyi
 * Date:     2021/3/22 7:58 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/22
 * @since 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisSpringTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void strTest() {
        String key = "str-k";
        String value = "9";
        long expiredTime = 2L;
        //设置值，设置过期时间
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.SECONDS);
        //取值
        Object valueFromR = redisTemplate.opsForValue().get(key);
        assert valueFromR != null;
        System.out.println(valueFromR.getClass());
        System.out.println(valueFromR);
        //incr操作(value 必须是int类型，至于为啥不知道。。。)
        System.out.println(redisTemplate.opsForValue().increment(key, 1));

    }

    @Test
    public void hashTest() {
        String key = "hash-k";
        redisTemplate.opsForHash().putAll(key, Collections.singletonMap("aa","aa-v"));
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        System.out.println(entries.get("aa"));
    }

    @Test
    public void listTest(){
        String key = "list-k";
        redisTemplate.opsForList().rightPushAll(key, 1, 2, 3);
        System.out.println(redisTemplate.opsForList().rightPop(key));
        System.out.println(redisTemplate.opsForList().rightPop(key));
    }

    @Test
    public void otherTest() {
        String key1 = "k1";
        String key11 = "k11";
        String k11Val = "10";

        String k2 = "k2";
        String v2 = "v6";

        // String script = "return redis.call('hmget', KEYS[1], KEYS[2])";
        // String script = "return ARGV[2]";
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/atomicOpsSet.lua")));
        defaultRedisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(defaultRedisScript, Arrays.asList(key1, key11, k2), k11Val, v2);
        System.out.println(result);

        // String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        // String key = "abc";
        // String clientId = "abc-v";
        // DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        // Long result = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), clientId);
        // System.out.println(result);
    }

    @Test
    public void otherTest2() {

        // stringRedisTemplate.opsForValue().set("key1", "v-1");
        // System.out.println(stringRedisTemplate.opsForValue().get("key1"));

        String key1 = "k1{abc}";
        String k11Val = "11";

        String k2 = "k2{abc}";
        String v2 = "v5";

        //过期时间 5秒
        String v3 = "10";

        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/atomicOpsSet2.lua")));
        defaultRedisScript.setResultType(Long.class);
        Long result = stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(key1, k2), k11Val, v2, v3);
        System.out.println(result);
    }

    @Test
    public void otherTest3() {

        String key1 = "meta::living:board:content:liveId_5792644";
        String key2 = "pagePusherSendTime";
        String key3 = "pageContent";

        String arg1 = "12345678";
        String arg2 = "abcdefgd";
        String arg3 = "28800";//过期时间（秒8*60*60）

        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/atomicOpsSet3.lua")));
        defaultRedisScript.setResultType(Long.class);
        Long result = stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(key1, key2, key3), arg1, arg2, arg3);
        System.out.println(result);
    }

    @Test
    public void otherTest4() {
        // String key1 = "list-key-1";
        // String value1 = "list-value-1";
        // ListOperations<String, String> list = stringRedisTemplate.opsForList();
        // Long aLong = list.rightPush(key1, value1);
        // System.out.println(aLong);
        // String s = list.leftPop(key1);
        // System.out.println(s);
        // String s1 = list.leftPop(key1);
        // System.out.println(s1);

        // Long res1 = stringRedisTemplate.opsForValue().increment("add", 0);
        // Long res2 = stringRedisTemplate.opsForValue().increment("add", 0);
        // Long res3 = stringRedisTemplate.opsForValue().increment("add", 0);
        // System.out.println(res1);
        // System.out.println(res2);
        // System.out.println(res3);

    }

}
