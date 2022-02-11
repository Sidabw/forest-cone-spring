package com.sidabw.webhttpclient.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidabw.webhttpclient.resttemplate.pojo.SidaParams;
import com.sidabw.webhttpclient.resttemplate.pojo.SidaResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Set;

/**
 * 参考1：https://www.baeldung.com/rest-template <br>
 * 参考2：https://www.baeldung.com/httpclient-timeout
 * @author shaogz
 */
@Slf4j
public class Demo1 {

    @Test
    public void testGet() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=222";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        String bodyStr = response.getBody();
        System.out.println(bodyStr);

        ResponseEntity<SidaResult> response2 = restTemplate.getForEntity(fooResourceUrl, SidaResult.class);
        SidaResult body = response2.getBody();
        assert body != null;
        System.out.println(body.getSuccess());
    }


    @Test
    public void testPost(){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SidaParams> request = new HttpEntity<>(new SidaParams("bar", "bar", "bar"));
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4";
        SidaResult foo = restTemplate.postForObject(fooResourceUrl, request, SidaResult.class);
        System.out.println(foo);

        //Create a new resource by POSTing the given object to the URI template, and returns the value of the Location header. This header typically indicates where the new resource is stored.
        //所以感觉这个...  现在是用不到
        //restTemplate.postForLocation()
    }

    @Test
    public void testFormPost(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("id", "1");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4";
        ResponseEntity<SidaResult> response = restTemplate.postForEntity(fooResourceUrl, request , SidaResult.class);
        System.out.println(response.getBody().getSuccess());

    }

    /*

     ***ATTENTION***
     如下put和delete，都是没有返回值的。
     所以在设计RESTFul Web API时，put与delete应当都是void。
     资源修改/删除是否成功，通过http code判断
     ***ATTENTION***

     */

    @Test
    public void testPut() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=put";
        //application-json
        HttpEntity<SidaParams> request = new HttpEntity<>(new SidaParams("bar", "bar", "bar"));
        //void
        restTemplate.put(fooResourceUrl, request);
        //这样也可以
        restTemplate.exchange(fooResourceUrl, HttpMethod.PUT, request, Void.class);
    }

    @Test
    public void testDelete() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=del";
        //void
        restTemplate.delete(fooResourceUrl);
    }

    @Test
    public void testCallback() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://a085-103-90-191-98.ngrok.io/paramsTest4?a=del";

        //用这个来改变请求body 或 请求头
        RequestCallback requestCallback = clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), new SidaParams("bar", "bar", "bar"));
            System.out.println("write req body in req callback");
        };

        //用这个从ClientHttpResponse中提取数据
        //如果要限制返回体大小的话，从这个流里拿数据能行吗
        //还有其他方式能拿到这个inputstream，但是关键在于这种方式能不能行
        //测试在Demo2
        ResponseExtractor<SidaResult> responseExtractor = clientHttpResponse -> {
            HttpStatus statusCode = clientHttpResponse.getStatusCode();
            System.out.println("statusCode: " + statusCode);
            InputStream body = clientHttpResponse.getBody();
            SidaResult sidaResult = new ObjectMapper().readValue(body, SidaResult.class);
            System.out.println("resBody: " + sidaResult);
            return sidaResult;
        };
        SidaResult executeRes = restTemplate.execute(fooResourceUrl, HttpMethod.POST, requestCallback, responseExtractor);
        System.out.println("executeRes: " + executeRes);
    }

    @Test
    public void testExternalConfig() {
        //方式一
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //1. 从连接池中获取链接的超时时间（the time to wait for a connection from the connection manager/pool）
        clientHttpRequestFactory.setConnectionRequestTimeout(timeout);
        //2. 与远程主机建立链接的超时时间（the time to establish the connection with the remote host）
        clientHttpRequestFactory.setConnectTimeout(timeout);
        //3. 建立链接后给到两个响应包的最长间隔时间（ – the time waiting for data – after establishing the connection; maximum time of inactivity between two data packets）
        clientHttpRequestFactory.setReadTimeout(timeout);
        new RestTemplate(clientHttpRequestFactory);


        //方式二
        int timeout2 = 1000;
        RequestConfig config = RequestConfig.custom()
            .setConnectionRequestTimeout(timeout2)
            .setSocketTimeout(timeout2)
            .setConnectTimeout(timeout2)
            .build();
        CloseableHttpClient client = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(config)
            .build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory2 = new HttpComponentsClientHttpRequestFactory(client);
        RestTemplate restTemplate2 = new RestTemplate(clientHttpRequestFactory2);
        /*

         ***ATTENTION***
         1。真正的超时时间是上面设置的三个时间的和
         2。这三个加起来就是最长请求结束的时间吗？不是的，超大文件的下载，响应包一直write，客户端这边要读好久，那...  就是好久..
         3。默认都是配置了连接池的；这个连接池说的是TCP物理链接，即http的keep alive属性；可能里面的maxConnections or maxConnectionsPerRoute不满足

         ***ATTENTION***

        */
        HttpEntity<SidaParams> request = new HttpEntity<>(new SidaParams("bar", "bar", "bar"));
        String fooResourceUrl = "https://a085-103-90-191-98.ngrok.io/paramsTest4";
        SidaResult foo = restTemplate2.postForObject(fooResourceUrl, request, SidaResult.class);
        System.out.println(foo);

    }


    /**
     * head请求，只拿返回头信息。通常用来用于验证uri有效性或拿到资源修改时间。
     * @return: void
     * @Author: shaogz
     * @Date: 2022/2/10 3:12 PM
     **/
    @Test
    public void testHead() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=222";
        HttpHeaders httpHeaders = restTemplate.headForHeaders(fooResourceUrl);
        Set<HttpMethod> allow = httpHeaders.getAllow();
        allow.forEach(e -> System.out.println(e.name()));
        httpHeaders.getAccept().forEach(System.out::println);
        httpHeaders.entrySet().forEach(System.out::println);
    }

    /**
     *  询问服务器该URI的支持的请求方法 Use OPTIONS to Get Allowed Operations
     * @return: void
     * @Author: shaogz
     * @Date: 2022/2/10 3:23 PM
     **/
    @Test
    public void testOptions() {
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest";
        RestTemplate restTemplate = new RestTemplate();
        Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(fooResourceUrl);
        optionsForAllow.forEach(System.out::println);
        //拿不到，don't know why
    }



}
