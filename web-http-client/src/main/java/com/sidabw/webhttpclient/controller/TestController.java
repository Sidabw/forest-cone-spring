package com.sidabw.webhttpclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author shaogz
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping(value = "/paramsTest4")
    public Map<String, String> paramsTest4(HttpServletRequest request) throws IOException, InterruptedException {
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        System.out.println("requestURI: " + requestURI);
        System.out.println("requestURL: " + requestURL);
        System.out.println("queryString: " + queryString);

        BufferedReader reader = request.getReader();
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println("body: " + line);
        }

        //如果发送的是post请求，那么会默认被解析到ParameterMap中，request.getReader()是拿不到东西的
        request.getParameterMap().forEach((k, v) -> {
            System.out.print("parameter map: ");
            System.out.print(k + " => ");
            System.out.println(Arrays.toString(v));
        });
        // TimeUnit.SECONDS.sleep(3);
        return Collections.singletonMap("success", "true");
    }

    @RequestMapping(value = "/paramsTest5")
    public void paramsTest5(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        //借用下，随便打印点信息
        log.warn("start");
        paramsTest4(request);
        // //测试ServletOutputStream
        // ServletOutputStream outputStream = response.getOutputStream();
        // FileInputStream fileInputStream = new FileInputStream("/Users/feiyi/Downloads/movies-music/浮生梦.mkv");
        // byte[] tmp = new byte[1024];
        // int len = 0;
        // int i = 0;
        // while ((len = fileInputStream.read(tmp)) != 0) {
        //     //经过测试，写到第七次的时候，client端就已经可以拿到status code 并能从对应res body的inputstream中一点点read了
        //     outputStream.write(tmp, 0, len);
        //     TimeUnit.MILLISECONDS.sleep(200);
        //     log.warn("write once {}", i++);
        // }
        // log.warn("finished");


        //测试writer
        //1.构建一个1024字节的String
        StringBuilder strTmpBuilder = new StringBuilder();
        for (int j = 0; j < 1024; j++) {
            strTmpBuilder.append("a");
        }
        String strTmp = strTmpBuilder.toString();
        PrintWriter writer = response.getWriter();
        int k = 0;
        while (k++ < 10_000) {
            //经过测试，写到第十五次的时候，client端就已经可以拿到status code 并能从对应res body的inputstream中一点点read了
            writer.write(strTmp);
            TimeUnit.MILLISECONDS.sleep(200);
            log.warn("write once {}", k);
        }
        log.warn("finished");
    }
}
