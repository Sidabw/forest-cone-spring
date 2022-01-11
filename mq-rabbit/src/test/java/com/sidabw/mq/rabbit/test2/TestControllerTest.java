package com.sidabw.mq.rabbit.test2;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Copyright (C), 2018-2021, bokecc.com FileName: TestControllerTest Author:   shaogz Date:     2021/5/8 6:00 PM
 * Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class TestControllerTest {

    @Test
    public void testt() {
        // String url = "http://ipv4.fiddler:8021/test2";
        String url = "http://ipv4.fiddler:9012/api/v1/document/create";
        String res = HttpUtil.createPost(url).form(Collections.singletonMap("name", "阿斯顿发d"))
            .contentType("application/x-www-form-urlencoded").execute().body();
        // String res = HttpUtil.post(url, Collections.singletonMap("name", "阿斯顿发d"));
        System.out.println(res);
    }

    @Test
    public void t2() {
        String aa = "事实上";
        byte[] bytes = aa.getBytes(StandardCharsets.UTF_8);
    }

}