package com.sidabw.ann.async;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Arrays;

@SpringBootTest
class BokeccPrework2ApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void test() throws InterruptedException {
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

}
