/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: AsyncTestController
 * Author:   feiyi
 * Date:     2021/3/25 6:08 PM
 * Description: AsyncTestController
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.async.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉:
 * 〈AsyncTestController〉
 *
 * @author feiyi
 * @create 2021/3/25
 * @since 1.0.0
 */
@RestController
public class AsyncTestController {

    @Autowired
    private AsyncService asyncService;

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);


    @GetMapping("/asyncTest")
    public String asyncTest(){
        asyncService.asyncTest(new Person2(ATOMIC_INTEGER.incrementAndGet()));
        return "success";
    }

    @GetMapping("/asyncTest2")
    public String asyncTest2(){
        asyncService.asyncTest2(new Person2(ATOMIC_INTEGER.incrementAndGet()));
        return "success2";
    }

}


