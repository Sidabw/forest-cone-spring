package com.sidabw.webhttpclient.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidabw.webhttpclient.resttemplate.pojo.SidaParams;
import com.sidabw.webhttpclient.resttemplate.pojo.SidaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author shaogz
 */
@Slf4j
public class Demo2 {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    /**
     * 先说结论：是可以通过ResponseExtractor完成对ResBody大小的限制工作的，对应当前项目的paramsTest5接口.
     * 项目应该是在：回调任务执行系统：swordman
     */
    public static void main(String[] args) throws InterruptedException {
        log.warn("aaa");
        TimeUnit.SECONDS.sleep(5);
        log.warn("wake up...");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://localhost:8889/paramsTest5?a=del";

        //用这个来改变请求body 或 请求头
        RequestCallback requestCallback = clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), new SidaParams("bar", "bar", "bar"));
            log.warn("write req body in req callback");
        };

        //用这个从ClientHttpResponse中提取数据
        ResponseExtractor<SidaResult> responseExtractor = clientHttpResponse -> {
            HttpStatus statusCode = clientHttpResponse.getStatusCode();
            log.warn("statusCode: " + statusCode);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int readThreshold = 1024*24;
            int readAlready = 0;

            InputStream body = clientHttpResponse.getBody();
            byte[] tmp = new byte[1024];
            int len = 0;
            int i = 0;
            while ((len = body.read(tmp)) != 0) {
                readAlready += len;
                log.warn("read once {}, {}, {}", len, i++, readAlready);
                if (readAlready > readThreshold) {
                    log.warn("full {}, {}, {}", len, i++, readAlready);
                    clientHttpResponse.close();
                    throw new RuntimeException("be full...");
                    //return null;
                }
            }
            return null;
        };
        SidaResult executeRes = restTemplate.execute(fooResourceUrl, HttpMethod.POST, requestCallback, responseExtractor);
        log.warn("executeRes: " + executeRes);
    }

}
